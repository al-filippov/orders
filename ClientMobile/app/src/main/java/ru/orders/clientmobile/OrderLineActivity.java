package ru.orders.clientmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ru.orders.clientmobile.models.OrderLine;

public class OrderLineActivity extends AppCompatActivity {

    private OrderLine curOrderLine;
    private int curOrderLineIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_line);

        final EditText name = (EditText) findViewById(R.id.lineNameEditor);
        final EditText price = (EditText) findViewById(R.id.linePriceEditor);
        final EditText quantity = (EditText) findViewById(R.id.lineQuantityEditor);

        curOrderLine = new OrderLine();

        if (getIntent().hasExtra("lineForEdit")) {

            curOrderLine = (OrderLine) getIntent().getExtras().get("lineForEdit");
            curOrderLineIndex = getIntent().getIntExtra("lineForEditIndex", -1);

            if (curOrderLine != null) {
                name.setText(curOrderLine.getItem());
                price.setText(String.format("%s", curOrderLine.getPrice()));
                quantity.setText(String.format("%s", curOrderLine.getQuantity()));
            }
        }

        Button cancel = (Button) findViewById(R.id.lineCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button ok = (Button) findViewById(R.id.lineOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()) {
                    return;
                }

                if (price.getText().toString().isEmpty()) {
                    return;
                }

                if (quantity.getText().toString().isEmpty()) {
                    return;
                }

                curOrderLine.setItem(name.getText().toString());
                curOrderLine.setPrice(Double.parseDouble(price.getText().toString()));
                curOrderLine.setQuantity(Integer.parseInt(quantity.getText().toString()));

                Intent intent = new Intent();
                intent.putExtra("line", curOrderLine);
                intent.putExtra("lineIndex", curOrderLineIndex);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
