package kr.co.ordermanagement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrdermanagementApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("1-1. 주문 생성 - 성공")
	void test1_1() throws Exception {
		byte[] body = """
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
				""".getBytes();

		mockMvc.perform(
				MockMvcRequestBuilders.post("/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body)
				)
				.andExpect(status().isOk())
				.andExpect(content().json("""
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
						"""));
	}

	@Test
	@DisplayName("1-2. 주문 생성 - 실패 (수량 부족)")
	void test1_2() throws Exception {
		byte[] body = """
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
				""".getBytes();

		mockMvc.perform(
						MockMvcRequestBuilders.post("/orders")
								.contentType(MediaType.APPLICATION_JSON)
								.content(body)
				)
				.andExpect(status().isInternalServerError())
				.andExpect(content().json("""
						{
						    "message": "1번 상품의 수량이 부족합니다."
						}
						"""));
	}

	@Test
	@DisplayName("1-3. 주문 생성 - 실패 (Product 찾지 못함)")
	void test1_3() throws Exception {
		byte[] body = """
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
				""".getBytes();

		mockMvc.perform(
						MockMvcRequestBuilders.post("/orders")
								.contentType(MediaType.APPLICATION_JSON)
								.content(body)
				)
				.andExpect(status().isNotFound())
				.andExpect(content().json("""
						{
							"message": "Product를 찾지 못했습니다."
						}
						"""));
	}

	@Test
	@DisplayName("2-1. 주문상태 강제 변경 - 성공")
	void test2_1() throws Exception {
		byte[] body = """
				{
				    "state": "SHIPPING"
				}
				""".getBytes();

		mockMvc.perform(
						MockMvcRequestBuilders.patch("/orders/1")
								.contentType(MediaType.APPLICATION_JSON)
								.content(body)
				)
				.andExpect(status().isOk())
				.andExpect(content().json("""
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
						"""));

		test2_1_init();
	}

	void test2_1_init() throws Exception {
		byte[] body = """
				{
				    "state": "CREATED"
				}
				""".getBytes();

		mockMvc.perform(
						MockMvcRequestBuilders.patch("/orders/1")
								.contentType(MediaType.APPLICATION_JSON)
								.content(body)
				);
	}

	@Test
	@DisplayName("2-2. 주문상태 강제 변경 - 실패 (Order 찾지 못함)")
	void test2_2() throws Exception {
		byte[] body = """
				{
				    "state": "SHIPPING"
				}
				""".getBytes();

		mockMvc.perform(
						MockMvcRequestBuilders.patch("/orders/99999")
								.contentType(MediaType.APPLICATION_JSON)
								.content(body)
				)
				.andExpect(status().isNotFound())
				.andExpect(content().json("""
						{
						    "message": "Order를 찾지 못했습니다."
						}
						"""));
	}

	@Test
	@DisplayName("3-1. 주문번호로 조회 - 성공")
	void test3_1() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.get("/orders/1")
				)
				.andExpect(status().isOk())
				.andExpect(content().json("""
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
						"""));
	}

	@Test
	@DisplayName("3-2. 주문번호로 조회 - 실패 (Order 찾지 못함)")
	void test3_2() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.get("/orders/99999")
				)
				.andExpect(status().isNotFound())
				.andExpect(content().json("""
						{
						    "message": "Order를 찾지 못했습니다."
						}
						"""));
	}

	@Test
	@DisplayName("4-1. 주문상태로 조회 - 성공 (검색 결과 있음)")
	void test4_1() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.get("/orders?state=CREATED")
				)
				.andExpect(status().isOk())
				.andExpect(content().json("""
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
						"""));
	}

	@Test
	@DisplayName("4-2. 주문상태로 조회 - 실패 (검색 결과 없음, 빈 배열)")
	void test4_2() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.get("/orders?state=COMPLETED")
				)
				.andExpect(status().isOk())
				.andExpect(content().json("""
						[]
						"""));
	}

	@Test
	@DisplayName("5-1. 주문 취소 - 성공")
	void test5_1() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.patch("/orders/1/cancel")
				)
				.andExpect(status().isOk())
				.andExpect(content().json("""
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
						"""));
	}

	@Test
	@DisplayName("5-2. 주문 취소 - 실패 (이미 취소되었거나 취소할 수 없는 주문상태)")
	void test5_2() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.patch("/orders/1/cancel")
				)
				.andExpect(status().isInternalServerError())
				.andExpect(content().json("""
						{
						    "message": "이미 취소되었거나 취소할 수 없는 주문상태입니다."
						}
						"""));
	}

}
