package ru.orders.clientmobile;

import android.app.Application;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.orders.clientmobile.api.EndPoints;
import ru.orders.clientmobile.api.OrderApi;

public class App extends Application {

    private static App instance;

    private static OrderApi orderApi;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        orderApi = new Retrofit.Builder()
                .baseUrl(EndPoints.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(OrderApi.class);
    }


    public static OrderApi getOrderApi() {
        return orderApi;
    }
}
