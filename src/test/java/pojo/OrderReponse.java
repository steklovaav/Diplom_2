package pojo;

public class OrderReponse {
    private OrderNumber order;
    private String name;

    public OrderReponse() {
    }

    public OrderReponse(OrderNumber orderNumber, String name) {
        this.order = orderNumber;
        this.name = name;
    }

    public OrderNumber getOrder() {
        return order;
    }

    public void setOrder(OrderNumber orderNumber) {
        this.order = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
