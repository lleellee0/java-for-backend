package kr.co.ordermanagement.domain.order;

public class OrderedProduct {
    private Long id;
    private String name;
    private Integer price;
    private Integer amount;

    public OrderedProduct(Long id, String name, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }
}
