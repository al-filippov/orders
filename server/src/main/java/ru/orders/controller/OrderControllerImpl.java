package ru.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.orders.model.Order;
import ru.orders.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderControllerImpl implements OrderController {

    @Autowired
    private OrderService orderService;

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order findOrder(@PathVariable(value = "id") Long id) {
        return orderService.findOrder(id);
    }

    @Override
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Order updateOrder(@PathVariable(value = "id") Long id, @RequestBody Order order) {
        order.setId(id);
        return orderService.updateOrder(order);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeOrder(@PathVariable(value = "id") Long id) {
        orderService.removeOrder(id);
    }
}
