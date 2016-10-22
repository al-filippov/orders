package ru.orders.clientmobile.api;

import retrofit2.http.*;
import ru.orders.clientmobile.models.Order;
import rx.Observable;

import java.util.List;

public interface OrderApi {

    @GET(value = "orders/{id}")
    Observable<Order> findOrder(@Path(value = "id") Long id);

    @GET(value = "orders")
    Observable<List<Order>> findAll();

    @POST(value = "orders")
    Observable<Order> createOrder(@Body Order order);

    @POST(value = "orders/{id}")
    Observable<Order> updateOrder(@Path(value = "id") Long id, @Body Order order);

    @DELETE(value = "orders/{id}")
    Observable<Void> removeOrder(@Path(value = "id") Long id);
}
