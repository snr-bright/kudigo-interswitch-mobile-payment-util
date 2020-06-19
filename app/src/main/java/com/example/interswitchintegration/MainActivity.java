package com.example.interswitchintegration;

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


public class MainActivity extends AppCompatActivity implements IswMobileSdk.IswPaymentCallback {
    private LinearLayout resultContainer;
    private TextView resultTitle,
            responseCode,
            responseDescription,
            paymentAmount,
            channel,
            isSuccessful;

    private String  customerId, customerName, customerEmail, customerMobile, reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        resultContainer = findViewById(R.id.resultContainer);

        resultTitle = findViewById(R.id.resultTitle);
        //responseCode = findViewById(R.id.responseCode);
       // responseDescription = findViewById(R.id.responseDescription);
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

       final CustomerInfo info = new CustomerInfo();
        /*info.setCustomerId("1");
        info.setCustomerName("Joan");
        info.setCustomerEmail("j@g.com");
        info.setPhoneNumber("+233 445566");
        info.setReference("1234");*/


        Button btn = findViewById(R.id.btnPay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitiatePayment(info);

            }
        });
    }

    public void InitiatePayment(CustomerInfo customerInfo){
        // get customer details


        EditText etAmount = findViewById(R.id.amount);
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
                customerId = customerInfo.getCustomerId(),
                customerName = customerInfo.getCustomerName(),
                customerEmail = customerInfo.getCustomerEmail(),
                customerMobile = customerInfo.getPhoneNumber(),
                reference = customerInfo.getReference() + new Date().getTime(),
                amount);


        // trigger payment
        IswMobileSdk.getInstance().pay(iswPaymentInfo, MainActivity.this);
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
            toast("your payment was successful, using: " + result.getChannel().name());
        else toast("unable to complete payment at the moment, try again.");

    }



    private void showResult(String title, IswPaymentResult result) {
        resultContainer.setVisibility(View.VISIBLE);

        // show result
        resultTitle.setText(title);
        boolean hasValue = result != null;
        int primaryColor = ContextCompat.getColor(this, R.color.colorPrimary);
        resultTitle.setTextColor(!hasValue ? Color.RED: primaryColor);

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


    public class CustomerInfo{
        String customerId;
        String customerName;
        String customerEmail;
        String phoneNumber;
        String reference;
        String amount;

        public void customerInfo(String customerId, String customerName, String customerEmail, String phoneNumber, String amount, String reference) {
            this.customerId = customerId;
            this.customerName = customerName;
            this.customerEmail = customerEmail;
            this.phoneNumber = phoneNumber;
            this.amount = amount;
            this.reference = reference;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

    }


}
