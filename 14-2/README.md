# order-management-application
주문 관리 API

## 요구사항
1. 상품을 주문할 수 있는 주문 관리 API 서버를 만들어야 합니다. 일부 코드는 이미 구현되어 있으며, 해당 코드에 주문 관리 기능을 추가해야 합니다. 이 GitHub 레포지토리를 다운로드하여 프로젝트를 진행해 주세요.
2. 상품은 상품번호, 상품이름, 가격, 재고수량이라는 정보를 가집니다. 상품을 나타내는 Product 클래스와 각각의 정보를 나타내는 필드는 이미 코드로 추가되어 있습니다.
3. 상품은 미리 추가되어 있으며 해당 상품들을 주문할 수 있어야 합니다. 다음은 애플리케이션 시작 시 상품을 추가하는 코드입니다.
  ```java
    @PostConstruct
    void initProducts() {
        Product product1 = new Product(1L, "상품1", 10000, 100);
        Product product2 = new Product(2L, "상품2", 25000, 300);
        Product product3 = new Product(3L, "상품3", 30000, 500);

        products.add(product1);
        products.add(product2);
        products.add(product3);
    }
  ```

4. 상품 주문과 관련된 요구사항은 아래 API 명세를 참고하세요.
5. 미리 구현된 코드에 추가할 코드가 있다면 추가해도 됩니다.



## API 명세
### 1. 상품 주문 API
* 클라이언트에게 주문하려는 상품번호와 주문수량을 받아서 주문정보를 생성합니다. 생성된 주문정보는 응답 바디에 담겨 클라이언트에게 제공됩니다.
* 주문수량만큼 상품의 재고수량이 줄어들어야 합니다.
* 주문된 상품의 재고수량이 부족하면 주문에 실패해야 합니다(응답은 아래 명세 참고). **이때 주문 자체가 생성되지 않아야 하므로 재고가 있는 상품의 재고수량이 감소되지 않아야 합니다.**
* 클라이언트가 잘못된 상품번호로 요청하는 경우에도 주문에 실패해야 합니다(응답은 아래 명세 참고).
* 주문에 성공하는 경우 아래와 같은 정보가 응답 바디에 담겨야 합니다.
  * id : 주문번호
  * orderedProducts : 주문된 상품 목록
    * id : 주문된 상품번호
    * name : 주문된 상품이름 (주문 시점의 이름)
    * price : 주문된 상품가격 (주문 시점의 가격)
    * amount : 주문수량
  * totalPrice : 전체 주문가격
  * state : 주문상태
* 주문상태는 생성(CREATED), 배송중(SHIPPING), 완료(COMPLETED), 취소(CANCELED)가 있고, 각 주문상태에서 다른 주문상태로 변경은 2번 ‘주문상태 변경 API’와 5번 ‘주문 취소 API’를 참고하세요.
* 주문 생성 시 최초 주문상태는 생성(CREATED) 상태여야 합니다.

요청 메서드 : POST   
요청 경로 : /orders   

#### 주문 성공(/orders)   
요청 바디   
```json
[
    {
        "id": 1,
        "amount": 1
    },
    {
        "id": 3,
        "amount": 1
    }
]
```

응답 바디   
```json
{
    "id": 1,
    "orderedProducts": [
        {
            "id": 1,
            "name": "상품1",
            "price": 10000,
            "amount": 1
        },
        {
            "id": 3,
            "name": "상품3",
            "price": 30000,
            "amount": 1
        }
    ],
    "totalPrice": 40000,
    "state": "CREATED"
}
```

#### 주문 실패(수량 부족)   
요청 바디   
```json
[
    {
        "id": 1,
        "amount": 99999
    },
    {
        "id": 3,
        "amount": 1
    }
]
```

응답 바디(상태코드 500)   
```json
{
    "message": "1번 상품의 수량이 부족합니다."
}
```

#### 주문 실패(id에 해당하는 상품이 없음)   
요청 바디   
```json
[
    {
        "id": 99999,
        "amount": 1
    },
    {
        "id": 3,
        "amount": 1
    }
]
```

응답 바디(상태코드 404)   
```json
{
    "message": "Product를 찾지 못했습니다."
}
```

### 2. 주문상태 강제 변경 API
* 주문상태 강제 변경 API는 {orderId}에 해당하는 주문의 상태를 강제로 변경합니다. 여기서 변경되는 주문상태는 어떤 상태라도 요청하는 상태로 변경됩니다. 주문상태 강제 변경 API에는 주문상태 변경과 관련된 제약사항이 존재하지 않습니다.
* 주문상태를 변경할 수 있는 API는 이외에도 5번의 ‘주문 취소 API’가 있습니다. 주문상태 변경과 관련해서는 제약사항이 있으므로 해당 API를 참고하세요.

요청 메서드 : PATCH   
요청 경로 : /orders/{orderId}   

#### 변경 성공(/orders/2)   
요청 바디   
```json
{
    "state": "SHIPPING"
}
```

응답 바디   
```json
{
    "id": 1,
    "orderedProducts": [
        {
            "id": 1,
            "name": "상품1",
            "price": 10000,
            "amount": 1
        },
        {
            "id": 3,
            "name": "상품3",
            "price": 30000,
            "amount": 1
        }
    ],
    "totalPrice": 40000,
    "state": "SHIPPING"
}
```

#### 변경 실패(id에 해당하는 주문이 없음)   
응답 바디(상태코드 404)   
```json
{
    "message": "Order를 찾지 못했습니다."
}
```

### 3. 주문번호로 조회 API
* 주문번호로 주문을 조회할 수 있는 API입니다.

요청 메서드 : GET   
요청 경로 : /orders/{orderId}   

#### 조회 성공(/orders/1)   
요청 바디 없음   

응답 바디   
```json
{
    "id": 1,
    "orderedProducts": [
        {
            "id": 1,
            "name": "상품1",
            "price": 10000,
            "amount": 1
        },
        {
            "id": 3,
            "name": "상품3",
            "price": 30000,
            "amount": 1
        }
    ],
    "totalPrice": 40000,
    "state": "CREATED"
}
```

#### 조회 실패(id에 해당하는 주문이 없음)   
응답 바디(상태코드 404)   
```json
{
    "message": "Order를 찾지 못했습니다."
}
```

### 4. 주문상태로 조회 API
* 특정 주문상태를 가지는 주문을 전부 조회할 수 있는 API입니다.
* 해당 주문상태를 가지는 주문이 아무것도 없다면 빈 배열이 반환되어야 합니다.

요청 메서드 : GET   
요청 경로 : /orders?state={state}   

#### 조회 성공 (/orders?state=CREATED)   
요청 바디 없음   

응답 바디
```json
[
    {
        "id": 1,
        "orderedProducts": [
            {
                "id": 1,
                "name": "상품1",
                "price": 10000,
                "amount": 1
            },
            {
                "id": 3,
                "name": "상품3",
                "price": 30000,
                "amount": 1
            }
        ],
        "totalPrice": 40000,
        "state": "CREATED"
    }
]
```

#### 조건에 맞는 주문이 없는 경우 (/orders?state=COMPLETED)   
응답 바디   
```json
[]
```

### 5. 주문 취소 API
* 주문상태를 변경할 수 있는 API입니다.
* 주문 취소는 오직 CREATED 상태에서만 가능하고, 나머지 상태(SHIPPING, COMPLETED, CANCELED) 상태에서는 불가능합니다. 취소가 불가능한 상태에서 취소하려고 하면 에러 메시지가 반환되어야 합니다.
* 주문이 취소되는 것이지, 삭제되는 것은 아닙니다. 취소된 상태로 여전히 주문을 조회할 수 있어야 합니다.

요청 메서드 : PATCH   
요청 경로 : /orders/{orderId}/cancel   

#### 취소 성공(/orders/1/cancel)   
요청 바디 없음   

응답 바디
```json
{
    "id": 1,
    "orderedProducts": [
        {
            "id": 1,
            "name": "상품1",
            "price": 10000,
            "amount": 1
        },
        {
            "id": 3,
            "name": "상품3",
            "price": 30000,
            "amount": 1
        }
    ],
    "totalPrice": 40000,
    "state": "CANCELED"
}
```

#### 취소 실패(취소할 수 없는 상태)   
응답 바디 (상태코드 500)   
```json
{
    "message": "이미 취소되었거나 취소할 수 없는 주문상태입니다."
}
```
