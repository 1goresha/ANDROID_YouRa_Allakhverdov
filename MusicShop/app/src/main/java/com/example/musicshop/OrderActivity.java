package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.net.URI;

public class OrderActivity extends AppCompatActivity {

    TextView orderTextView;
    String[] emailAddresses;
    String subject;
    String textMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderTextView = findViewById(R.id.order);
        emailAddresses = new String[]{"i.deryabin2017@mail.ru"};
        subject = "test send email from app";

        Intent receivedOrderIntent = getIntent();
        String customerName = receivedOrderIntent.getStringExtra("customerName");
        String goodsName = receivedOrderIntent.getStringExtra("goodsName");
        Integer quantity = receivedOrderIntent.getIntExtra("quantity", 0);
        Double price = receivedOrderIntent.getDoubleExtra("price", 0);
        Double orderPrice = receivedOrderIntent.getDoubleExtra("orderPrice", 0);

        textMessage = "customer: " + customerName + "\n"
                + "goodsName: " + goodsName + "\n"
                + "quantity: " + quantity + "\n"
                + "price: " + price + "\n"
                + "price for all: " + orderPrice;

        orderTextView.setText(textMessage);
    }

    public void sendToEmail(View view) {

        Intent orderIntent = new Intent(Intent.ACTION_SENDTO);
        orderIntent.setData(Uri.parse("mailto:"));
        orderIntent.putExtra(Intent.EXTRA_EMAIL, emailAddresses);
        orderIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        orderIntent.putExtra(Intent.EXTRA_TEXT, textMessage);

        if (orderIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(orderIntent);
        }
    }
}
