package ru.orders.clientmobile;

import android.os.Bundle;
import nucleus.presenter.RxPresenter;
import ru.orders.clientmobile.models.Order;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import java.util.List;

public class MainPresenter extends RxPresenter<MainActivity> {

    private static final int GET_ALL_ITEMS = 1;
    private static final int REMOVE_ITEM = 2;
    private static final int CREATE_ITEM = 3;
    private static final int GET_ITEM = 4;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        requestItems();
    }

    public void requestItems() {
        restartableLatestCache(GET_ALL_ITEMS,
                new Func0<Observable<List<Order>>>() {
                    @Override
                    public Observable<List<Order>> call() {
                        return App.getOrderApi()
                                .findAll()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                },
                new Action2<MainActivity, List<Order>>() {
                    @Override
                    public void call(MainActivity mainActivity, List<Order> orders) {
                        mainActivity.onItemsNext(orders);
                        stop(GET_ALL_ITEMS);
                    }
                },
                new Action2<MainActivity, Throwable>() {
                    @Override
                    public void call(MainActivity mainActivity, Throwable throwable) {
                        mainActivity.onItemsError(throwable);
                    }
                });


        start(GET_ALL_ITEMS);
    }

    public void deleteItem(final Order order) {
        restartableLatestCache(REMOVE_ITEM,
                new Func0<Observable<Void>>() {
                    @Override
                    public Observable<Void> call() {
                        return App.getOrderApi()
                                .removeOrder(order.getId())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                },
                new Action2<MainActivity, Void>() {
                    @Override
                    public void call(MainActivity mainActivity, Void avoid) {
                        mainActivity.refreshOrderList();
                        stop(REMOVE_ITEM);
                    }
                },
                new Action2<MainActivity, Throwable>() {
                    @Override
                    public void call(MainActivity mainActivity, Throwable throwable) {
                        mainActivity.onItemsError(throwable);
                    }
                });

        start(REMOVE_ITEM);
    }

    public void createItem(final Order order) {
        restartableLatestCache(CREATE_ITEM,
                new Func0<Observable<Order>>() {
                    @Override
                    public Observable<Order> call() {
                        return App.getOrderApi()
                                .createOrder(order)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                },
                new Action2<MainActivity, Order>() {
                    @Override
                    public void call(MainActivity mainActivity, Order order1) {
                        mainActivity.refreshOrderList();
                        stop(CREATE_ITEM);
                    }
                },
                new Action2<MainActivity, Throwable>() {
                    @Override
                    public void call(MainActivity mainActivity, Throwable throwable) {
                        mainActivity.onItemsError(throwable);
                    }
                });

        start(CREATE_ITEM);
    }

    public void getItem(final Long id) {
        restartableLatestCache(GET_ITEM,
                new Func0<Observable<Order>>() {
                    @Override
                    public Observable<Order> call() {
                        return App.getOrderApi()
                                .findOrder(id)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                },
                new Action2<MainActivity, Order>() {
                    @Override
                    public void call(MainActivity mainActivity, Order order1) {
                        mainActivity.openOrderActivity(order1);
                        stop(GET_ITEM);
                    }
                },
                new Action2<MainActivity, Throwable>() {
                    @Override
                    public void call(MainActivity mainActivity, Throwable throwable) {
                        mainActivity.onItemsError(throwable);
                    }
                });

        start(GET_ITEM);
    }
}
