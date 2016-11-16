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
import ru.orders.clientmobile.models.OrderLine;

import java.util.ArrayList;

@RequiresPresenter(OrderPresenter.class)
public class OrderActivity extends NucleusAppCompatActivity<OrderPresenter> {

    private final int ORDER_LINE_EDIT = 1;
    private final int ORDER_LINE_DELETE = 2;

    private final int ORDER_LINE_REQUEST = 1;

    private ArrayList<OrderLine> orderLines = new ArrayList<>();
    private OrderLineAdapter orderLineAdapter;

    private Order curOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        curOrder = (Order) getIntent().getExtras().get("order");

        if (curOrder == null) {
            finish();
        }

        orderLines.clear();
        orderLines.addAll(curOrder.getOrderLines());

        ListView listView = (ListView) findViewById(R.id.orderLinesListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openOrderLineActivity(orderLines.get(i));
            }
        });
        orderLineAdapter = new OrderLineAdapter(this, orderLines);
        listView.setAdapter(orderLineAdapter);

        registerForContextMenu(listView);

        Button addLine = (Button) findViewById(R.id.addOrderLine);
        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrderLineActivity(null);
            }
        });

        Button cancel = (Button) findViewById(R.id.orderCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button save = (Button) findViewById(R.id.orderSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curOrder.getOrderLines().clear();
                curOrder.getOrderLines().addAll(orderLines);
                getPresenter().updateItem(curOrder);
            }
        });
    }

    public void onItemsError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void openOrderLineActivity(OrderLine orderLine) {
        Intent orderLineIntent = new Intent(getApplicationContext(), OrderLineActivity.class);
        if (orderLine != null) {
            orderLineIntent.putExtra("lineForEdit", orderLine);
            orderLineIntent.putExtra("lineForEditIndex", orderLines.indexOf(orderLine));
        }
        startActivityForResult(orderLineIntent, ORDER_LINE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode != ORDER_LINE_REQUEST) {
            return;
        }

        OrderLine orderLine = (OrderLine) data.getExtras().get("line");
        int index = data.getIntExtra("lineIndex", -1);

        if (orderLine == null) {
            return;
        }

        if (index != -1) {
            orderLines.set(index, orderLine);
        } else {
            orderLines.add(orderLine);
        }

        orderLineAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId()) {
            case R.id.orderLinesListView:
                menu.add(0, ORDER_LINE_EDIT, 0, "Edit");
                menu.add(0, ORDER_LINE_DELETE, 0, "Delete");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final int curIndex = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;

        switch (item.getItemId()) {
            case ORDER_LINE_EDIT:
                openOrderLineActivity(orderLines.get(curIndex));
                break;
            case ORDER_LINE_DELETE:
                orderLines.remove(curIndex);
                orderLineAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
