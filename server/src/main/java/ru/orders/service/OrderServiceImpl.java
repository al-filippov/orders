package ru.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orders.model.Order;
import ru.orders.model.OrderLine;
import ru.orders.repository.OrderRepository;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order findOrder(Long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public Order updateOrder(Order order) {
        for (OrderLine orderLine: order.getOrderLines()) {
            if (orderLine.getOrder() != null) {
                continue;
            }
            orderLine.setOrder(order);
        }
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public void removeOrder(Long id) {
        orderRepository.delete(id);
    }

    @Override
    public void removeOrder(Order order) {
        orderRepository.delete(order);
    }
}
