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

    private static final int REQUEST_ITEMS = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(REQUEST_ITEMS,
                new Func0<Observable<List<Order>>>() {
                    @Override
                    public Observable<List<Order>> call() {
                        return App.getOrderApi()
                                .findAll()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                },
                new Action2<MainActivity, List<Order>>() {
                    @Override
                    public void call(MainActivity mainActivity, List<Order> orders) {
                        mainActivity.onItemsNext(orders);
                    }
                },
                new Action2<MainActivity, Throwable>() {
                    @Override
                    public void call(MainActivity mainActivity, Throwable throwable) {
                        mainActivity.onItemsError(throwable);
                    }
                });

        if (savedState == null)
            start(REQUEST_ITEMS);
    }
}
