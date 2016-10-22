package ru.orders.clientmobile;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;
import ru.orders.clientmobile.models.Order;

import java.util.ArrayList;
import java.util.List;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainPresenter> {

    private ArrayList<Order> orders = new ArrayList<>();
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        orderAdapter = new OrderAdapter(this, orders);
        listView.setAdapter(orderAdapter);
    }

    public void onItemsNext(List<Order> items) {
        orders.clear();
        orders.addAll(items);
    }

    public void onItemsError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
}
