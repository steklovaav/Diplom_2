package pojo;

public class OrderListResponse {
    private Order[] orders;
    private int total;
    private int totalToday;

    public OrderListResponse() {
    }

    public OrderListResponse(Order[] orders, int total, int totalToday) {
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }
}
