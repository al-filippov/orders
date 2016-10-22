package ru.orders.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date creationDate;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<OrderLine> orderLines = new LinkedHashSet<>();

    public Order() {
        this.creationDate = new Date();
    }

    public Order(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Set<OrderLine> getOrderLines() {
        return this.orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        if (orderLines.contains(orderLine)) {
            return;
        }

        if (this.orderLines.add(orderLine)) {
            if (orderLine.getOrder() != null && orderLine.getOrder() != this) {
                orderLine.getOrder().removeOrderLine(orderLine);
            }
            orderLine.setOrder(this);
        }
    }

    public void removeOrderLine(OrderLine orderLine) {
        if (orderLines.remove(orderLine)) {
            orderLine.setOrder(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (this.id == null || order.id == null) {
            return this == order;
        }

        return id.equals(order.id);

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
        return "Order{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                '}';
    }
}
