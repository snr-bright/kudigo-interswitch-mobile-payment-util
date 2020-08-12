package com.example.interswitchpaymentlibrary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.interswitchng.iswmobilesdk.IswMobileSdk;
import com.interswitchng.iswmobilesdk.shared.models.core.IswPaymentInfo;
import com.interswitchng.iswmobilesdk.shared.models.core.IswPaymentResult;

import java.util.Date;


public class MakePayment extends AppCompatActivity implements IswMobileSdk.IswPaymentCallback {
    private CustomerInfo customerInfo;
    private LinearLayout resultContainer;
    private EditText etAmount;
    private TextView resultTitle,
            responseCode,
            responseDescription,
            paymentAmount,
            channel,
            isSuccessful;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

       etAmount = findViewById(R.id.amount);

        Intent i = getIntent();
       customerInfo = (CustomerInfo)i.getSerializableExtra("customer_info");


        resultContainer = findViewById(R.id.resultContainer);

        resultTitle = findViewById(R.id.resultTitle);
        responseCode = findViewById(R.id.responseCode);
        responseDescription = findViewById(R.id.responseDescription);
        paymentAmount = findViewById(R.id.paymentAmount);
        channel = findViewById(R.id.channel);
        isSuccessful = findViewById(R.id.isSuccessful);

        final EditText etAmount = findViewById(R.id.amount);

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resultContainer.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        Button btn = findViewById(R.id.btnPay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitiatePayment(customerInfo);

            }
        });
    }



    public void InitiatePayment(CustomerInfo customerInfo) {
        String customerId = customerInfo.getCustomerId(),
                customerName = customerInfo.getCustomerName(),
                customerEmail = customerInfo.getCustomerEmail(),
                customerMobile = customerInfo.getPhoneNumber(),
                reference = customerInfo.getReference() + new Date().getTime();

        String amtString = etAmount.getText().toString();

        long amount;
        // initialize amount
        if (amtString.isEmpty()) {
            toast("Please enter amount");
            return;
        } else {
            // amount = Integer.parseInt(amtString) * 100;
            amount = Long.parseLong(amtString);
        }

        // create payment info
        IswPaymentInfo iswPaymentInfo = new IswPaymentInfo(
               customerId,customerName,customerEmail,customerMobile,reference, amount);

        // trigger payment
        IswMobileSdk.getInstance().pay(iswPaymentInfo, MakePayment.this);
    }


    @Override
    public void onUserCancel() {
        String title = "You cancelled payment";
        showResult(title, null);

        toast("You cancelled payment, please try again.");
    }


    @Override
    public void onPaymentCompleted(@NonNull IswPaymentResult result) {
        String title = "Payment Result";
        showResult(title, result);


        if (result.isSuccessful())
            toast("Your payment was successful, using: " + result.getChannel().name());
        else toast("Unable to complete payment at the moment, try again.");

    }


    private void showResult(String title, IswPaymentResult result) {
        resultContainer.setVisibility(View.VISIBLE);

        // show result
        resultTitle.setText(title);
        boolean hasValue = result != null;
        int primaryColor = ContextCompat.getColor(this, R.color.colorPrimary);
        resultTitle.setTextColor(!hasValue ? Color.RED : primaryColor);

        int visibility = hasValue ? View.VISIBLE : View.INVISIBLE;
        paymentAmount.setVisibility(visibility);
        responseCode.setVisibility(visibility);
        responseDescription.setVisibility(visibility);
        isSuccessful.setVisibility(visibility);
        channel.setVisibility(visibility);

        if (!hasValue) return;

        paymentAmount.setText("" + (result.getAmount() / 100));
        responseCode.setText(result.getResponseCode());
        responseDescription.setText(result.getResponseDescription());
        isSuccessful.setText("" + result.isSuccessful());
        channel.setText(result.getChannel().name());
    }


    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



}
