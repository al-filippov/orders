package ru.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.orders.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
