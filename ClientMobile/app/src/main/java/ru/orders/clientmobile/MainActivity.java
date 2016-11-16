package ru.orders.clientmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;
import ru.orders.clientmobile.models.Order;

import java.util.ArrayList;
import java.util.List;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainPresenter> {

    private final int ORDER_EDIT = 1;
    private final int ORDER_DELETE = 2;

    private ArrayList<Order> orders = new ArrayList<>();
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.ordersListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getPresenter().getItem(orders.get(i).getId());
            }
        });
        orderAdapter = new OrderAdapter(this, orders);
        listView.setAdapter(orderAdapter);

        registerForContextMenu(listView);

        Button createOrder = (Button) findViewById(R.id.createOrder);
        createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().createItem(new Order());
            }
        });
    }

    public void openOrderActivity(final Order order) {
        Intent orderIntent = new Intent(getApplicationContext(), OrderActivity.class);
        orderIntent.putExtra("order", order);
        startActivity(orderIntent);
    }

    public void onItemsNext(List<Order> items) {
        orders.clear();
        orders.addAll(items);
        orderAdapter.notifyDataSetChanged();
    }

    public void refreshOrderList() {
        getPresenter().requestItems();
    }

    public void onItemsError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId()) {
            case R.id.ordersListView:
                menu.add(0, ORDER_EDIT, 0, "Edit");
                menu.add(0, ORDER_DELETE, 0, "Delete");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final int curIndex = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;

        switch (item.getItemId()) {
            case ORDER_EDIT:
                getPresenter().getItem(orders.get(curIndex).getId());
                break;
            case ORDER_DELETE:
                final Order curItem = orders.get(curIndex);
                getPresenter().deleteItem(curItem);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
