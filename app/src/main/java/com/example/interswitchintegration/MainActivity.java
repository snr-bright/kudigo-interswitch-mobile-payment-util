package com.example.interswitchintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.interswitchpaymentlibrary.CustomerInfo;
import com.example.interswitchpaymentlibrary.MakePayment;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    MakePayment makePayment = new MakePayment();
    CustomerInfo info = new CustomerInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pay = findViewById(R.id.payBtn);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setCustomerId("1");
                info.setCustomerName("Jay");
                info.setCustomerEmail("j.g.com");
                info.setPhoneNumber("123456");
                info.setReference("012");

                Intent intent = new Intent(MainActivity.this,MakePayment.class);
                intent.putExtra("customer_info",info);
                startActivity(intent);
            }
        });



    }
}