package ru.orders.controller;

import ru.orders.model.Order;

import java.util.List;

public interface OrderController {

    Order findOrder(Long id);

    List<Order> findAll();

    Order createOrder(Order order);

    Order updateOrder(Long id, Order order);

    void removeOrder(Long id);
}
