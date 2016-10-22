package ru.orders.clientmobile.models;

public class OrderLine {

    private Long id;

    private String item;

    private Double price;

    private Integer quantity;

    private Order order;

    public OrderLine() {
        this.price = 0.0;
        this.quantity = 0;
    }

    public OrderLine(String item, Double price, Integer quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (!order.getOrderLines().contains(this)) {
            order.getOrderLines().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLine orderLine = (OrderLine) o;

        if (this.id == null || orderLine.id == null) {
            return this == orderLine;
        }

        return id.equals(orderLine.id);

    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
