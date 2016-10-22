package ru.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.orders.model.Order;
import ru.orders.model.OrderLine;
import ru.orders.service.OrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class DBInit {

    @Autowired
    private OrderService orderService;

    public void populate() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

            Order order1 = new Order();
            order1.addOrderLine(new OrderLine("Item1", 10.0, 1));
            order1.addOrderLine(new OrderLine("Item2", 20.0, 2));
            order1.addOrderLine(new OrderLine("Item3", 30.0, 3));

            Order order2 = new Order(simpleDateFormat.parse("05.05.2016 02:00"));
            order2.addOrderLine(new OrderLine("Item4", 40.0, 4));
            order2.addOrderLine(new OrderLine("Item5", 50.0, 5));
            order2.addOrderLine(new OrderLine("Item6", 60.0, 6));
            order2.addOrderLine(new OrderLine("Item7", 70.0, 7));

            Order order3 = new Order(simpleDateFormat.parse("11.04.2016 15:00"));
            order3.addOrderLine(new OrderLine("Item1", 10.0, 1));
            order3.addOrderLine(new OrderLine("Item8", 80.0, 8));

            orderService.createOrder(order1);
            orderService.createOrder(order2);
            orderService.createOrder(order3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
