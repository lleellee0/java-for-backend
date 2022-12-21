package kr.co.ordermanagement.application;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import kr.co.ordermanagement.domain.order.OrderedProduct;
import kr.co.ordermanagement.domain.order.State;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.domain.product.ProductRepository;
import kr.co.ordermanagement.presentation.dto.ChangeStateRequestDto;
import kr.co.ordermanagement.presentation.dto.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleOrderService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public SimpleOrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public OrderResponseDto createOrder(List<OrderProductRequestDto> orderProductRequestDtos) {
        List<OrderedProduct> orderedProducts = makeOrderedProducts(orderProductRequestDtos);
        decreaseProductsAmount(orderedProducts);

        Order order = new Order(orderedProducts);
        orderRepository.add(order);

        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(order);
        return orderResponseDto;
    }

    public OrderResponseDto findById(Long orderId) {
        Order order = orderRepository.findById(orderId);

        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(order);
        return orderResponseDto;
    }

    public OrderResponseDto changeState(Long orderId, ChangeStateRequestDto changeStateRequestDto) {
        Order order = orderRepository.findById(orderId);
        State state = changeStateRequestDto.getState();

        order.changeStateForce(state);

        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(order);
        return orderResponseDto;
    }

    public List<OrderResponseDto> findByState(State state) {
        List<Order> orders = orderRepository.findByState(state);

        List<OrderResponseDto> orderResponseDtos = orders
                .stream()
                .map(order -> OrderResponseDto.toDto(order))
                .toList();

        return orderResponseDtos;
    }

    public OrderResponseDto cancelOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId);

        order.cancel();

        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(order);
        return orderResponseDto;
    }

    private List<OrderedProduct> makeOrderedProducts(List<OrderProductRequestDto> orderProductRequestDtos) {
        return orderProductRequestDtos
                .stream()
                .map(orderProductRequestDto -> {
                    Long productId = orderProductRequestDto.getId();
                    Product product = productRepository.findById(productId);

                    Integer orderedAmount = orderProductRequestDto.getAmount();
                    product.checkEnoughAmount(orderedAmount);

                    return new OrderedProduct(
                            productId,
                            product.getName(),
                            product.getPrice(),
                            orderProductRequestDto.getAmount()
                    );
                }).toList();
    }

    private void decreaseProductsAmount(List<OrderedProduct> orderedProducts) {
        orderedProducts
                .stream()
                .forEach(orderedProduct -> {
                    Long productId = orderedProduct.getId();
                    Product product = productRepository.findById(productId);

                    Integer orderedAmount = orderedProduct.getAmount();
                    product.decreaseAmount(orderedAmount);
                });
    }
}
