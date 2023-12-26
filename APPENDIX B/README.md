# product-management-application

### JDK
자바 버전은 JDK17을 사용했습니다.

구체적으론 OpenJDK 중 [Temurin JDK](https://adoptium.net/temurin/releases/)를 사용했습니다. 

### Profile
Profile은 test Profile로 바로 리스트를 사용하는 상품 관리 애플리케이션을 실행시킬 수 있도록 구성했고, 만약 데이터베이스를 사용하는 Profile로 실행시키려면 application.properties에서 ‘spring.profiles.active’ 값을 prod로 변경해주세요.

Profile을 prod로 변경하는 경우 데이터베이스를 사용하게 되고, 데이터베이스는 아래와 같이 쉽게 실행시켜 보실 수 있습니다.

#### 도커로 MySQL 데이터베이스 실행
```
docker run --name some-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=hanbit -d mysql:8.0.29 --character-set-server=utf8mb4  --collation-server=utf8mb4_general_ci
```

#### 데이터베이스 접속
```
mysql -u root -p
```

#### 스키마 생성
```
CREATE SCHEMA product_management;
```

#### 스키마 사용
```
USE product_management;
```

#### products 테이블 생성
```SQL
CREATE TABLE products (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    amount INT NOT NULL
);
```