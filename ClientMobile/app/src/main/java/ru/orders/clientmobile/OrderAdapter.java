package ru.orders.clientmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ru.orders.clientmobile.models.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater lInflater;
    private List<Order> objects;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);

    public OrderAdapter(Context ctx, List<Order> objects) {
        this.ctx = ctx;
        this.objects = objects;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View currentView = view;
        if (currentView == null) {
            currentView = lInflater.inflate(R.layout.item, viewGroup, false);
        }

        Order order = (Order) getItem(i);

        ((TextView) currentView.findViewById(R.id.orderId)).setText(String.format("Order # %s", order.getId().toString()));
        ((TextView) currentView.findViewById(R.id.orderDate)).setText(dateFormat.format(order.getCreationDate()));

        return currentView;
    }
}
