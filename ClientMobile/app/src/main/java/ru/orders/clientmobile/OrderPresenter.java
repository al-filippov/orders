package ru.orders.clientmobile;

import nucleus.presenter.RxPresenter;
import ru.orders.clientmobile.models.Order;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class OrderPresenter extends RxPresenter<OrderActivity> {

    private static final int UPDATE_ITEM = 5;

    public void updateItem(final Order order) {
        restartableLatestCache(UPDATE_ITEM,
                new Func0<Observable<Order>>() {
                    @Override
                    public Observable<Order> call() {
                        return App.getOrderApi()
                                .updateOrder(order.getId(), order)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                },
                new Action2<OrderActivity, Order>() {
                    @Override
                    public void call(OrderActivity orderActivity, Order order1) {
                        orderActivity.finish();
                        stop(UPDATE_ITEM);
                    }
                },
                new Action2<OrderActivity, Throwable>() {
                    @Override
                    public void call(OrderActivity orderActivity, Throwable throwable) {
                        orderActivity.onItemsError(throwable);
                    }
                });

        start(UPDATE_ITEM);
    }
}
