package ru.orders.service;

import ru.orders.model.Order;

import java.util.List;

public interface OrderService {

    Order findOrder(Long id);

    List<Order> findAll();

    Order createOrder(Order order);

    Order updateOrder(Order order);

    void removeOrder(Long id);

    void removeOrder(Order order);
}
