package ru.orders.clientmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ru.orders.clientmobile.models.OrderLine;

import java.util.List;

public class OrderLineAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater lInflater;
    private List<OrderLine> objects;

    public OrderLineAdapter(Context ctx, List<OrderLine> objects) {
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
            currentView = lInflater.inflate(R.layout.line, viewGroup, false);
        }

        OrderLine orderLine = (OrderLine) getItem(i);

        ((TextView) currentView.findViewById(R.id.lineName)).setText(String.format("Name: %s", orderLine.getItem()));
        ((TextView) currentView.findViewById(R.id.linePrice)).setText(String.format("Price: %s", orderLine.getPrice()));
        ((TextView) currentView.findViewById(R.id.lineQuantity)).setText(String.format("Quantity: %s", orderLine.getQuantity()));

        return currentView;
    }
}
