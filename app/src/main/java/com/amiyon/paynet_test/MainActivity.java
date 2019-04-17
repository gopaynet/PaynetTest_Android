package com.amiyon.paynet_test;

//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amiyon.paynet.ErrorResult;
import com.amiyon.paynet.PaymentResponse;
import com.amiyon.paynet.PaymentResult;
import com.amiyon.paynet.Paynet;
import com.amiyon.paynet.library.CustomerInfo;

public class MainActivity extends AppCompatActivity {

    private EditText paynet_secret_key;
    private EditText paynet_mid;

    private EditText invoice_number;
    private EditText total_amount;

    private EditText customer_name;
    private EditText customer_email;
    private EditText customer_phone;
    private TextView paynet_text_response;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        paynet_secret_key = findViewById(R.id.paynet_secret_key);
        paynet_mid = findViewById(R.id.paynet_mid);

        SharedPreferences sharedPreferences = getSharedPreferences("GOPAYNET", MODE_PRIVATE);

        String secret_key = sharedPreferences.getString("gopaynet_secretkey", null);
        if (secret_key != null)
            paynet_secret_key.setText(secret_key);

        String mid = sharedPreferences.getString("gopaynet_mid", null);
        if (mid != null)
            paynet_mid.setText(mid);

        invoice_number=findViewById(R.id.invoice_number);
        invoice_number.setText("");
        total_amount=findViewById(R.id.invoice_amount);
        total_amount.setText("");

        customer_name=findViewById(R.id.customer_name);
        customer_name.setText("test user");
        customer_email=findViewById(R.id.customer_email);
        customer_phone=findViewById(R.id.customer_phone);

        paynet_text_response = findViewById(R.id.text_response);



    }




    public void submit(View v){


        closeKeyboard();

        String secretKey = paynet_secret_key.getText().toString();
        String paynetMID = paynet_mid.getText().toString();

        String customerName=customer_name.getText().toString();
        String cusomerEmail = customer_email.getText().toString();
        String customerPhone = customer_phone.getText().toString();

        String invoiceNumber=invoice_number.getText().toString();
        String totalAmount=total_amount.getText().toString();

        if(secretKey.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Enter a valid secret key", Toast.LENGTH_SHORT).show();
        }
        else if(paynetMID.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Enter a valid paynet MID", Toast.LENGTH_SHORT).show();
        }
        else if(customerName.equalsIgnoreCase("")){
            Toast.makeText(this, "Please Enter customer name", Toast.LENGTH_SHORT).show();
        }else if(invoiceNumber.equalsIgnoreCase("")){
            Toast.makeText(this, "Please Enter invoice number", Toast.LENGTH_SHORT).show();
        }else if(totalAmount.equalsIgnoreCase("")){
            Toast.makeText(this, "Please Enter total number", Toast.LENGTH_SHORT).show();
        }else {


            Paynet.secretKey = secretKey;
            Paynet.mid = paynetMID;
            // Add Address details to addressInfo JSONObject

            SharedPreferences sharedPreferences = getSharedPreferences("GOPAYNET", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("gopaynet_secretkey", secretKey);
            editor.putString("gopaynet_mid", paynetMID);
            editor.commit();


            paynet_text_response.setText("");

//            //You can declare PaymentResponse as below also
//            //Call the activity to load the payment page
//            PaymentResponse paymentResponse = new PaymentResponse() {
//
//                // This will be fired if user competed payment process
//                @Override
//                public void onSuccess(PaymentResult result) {
//
//                    Toast.makeText(MainActivity.this, "Payment succeeded", Toast.LENGTH_SHORT).show();
//                    String strResponse = "Payment Succeeded \ninvoiceId: " + result.invoiceId + "\nAuthorizationCode: " + result.authorizationCode + "\nDate: " +result.date;
//                    paynet_text_response.setText(strResponse);
//                }
//
//                //This will be fired if user failed payment
//                @Override
//                public void onFailed(ErrorResult result) {
//
//                    Toast.makeText(MainActivity.this, "Payment failed with status " + result.paymentStatus, Toast.LENGTH_SHORT).show();
//
//                    String strResponse = "Payment Succeeded \ninvoiceId: " + result.invoiceId + "\nTransactionId: " + result.transactionId;
//                    paynet_text_response.setText(strResponse);
//                }
//
//                //This ill be fired if user closed payment window. Ther is a boolean parameter 'cancelled' will be received based on user action.
//                //There will be 2 cases you need manage in your app
//                //1. user user closed window in the middle of the payment, then cancelld parameter will be true.
//                //2. if user closed window afre a success or a fialed payment it will be fallse.
//                @Override
//                public void onWindowClosed(Boolean cancelled) {
//
//                    String strResponse = cancelled == true ? "User cancelled payment" : "Payment window closed";
//                    paynet_text_response.setText(strResponse);
//                    Toast.makeText(MainActivity.this, strResponse, Toast.LENGTH_SHORT).show();
//                }
//
//                //This will be fired if any general error occured in the middle of a payment.
//                @Override
//                public void onError(Exception error) {
//
//                    String strResponse = "Payment error occured  " + error.getLocalizedMessage();
//                    paynet_text_response.setText(strResponse);
//
//                    Toast.makeText(MainActivity.this, strResponse, Toast.LENGTH_SHORT).show();
//                }
//            };


            // declare Paynet library instance
            Paynet paynet = Paynet.getInstance();

            // Add customer details to customerInfo
            CustomerInfo customerInfo = new CustomerInfo(customerName, cusomerEmail,customerPhone);


            //User defined parameters
            String usedDefined1 = "1";
            String usedDefined2 = "2";
            String usedDefined3 = "3";
            String usedDefined4 = "4";

            paynet.makePayment(MainActivity.this, customerInfo,invoiceNumber,totalAmount,usedDefined1,usedDefined2,usedDefined3,
                    usedDefined4,new PaymentResponse() {

                        // This will be fired if user competed payment process
                        @Override
                        public void onSuccess(PaymentResult result) {

                            Toast.makeText(MainActivity.this, "Payment succeeded", Toast.LENGTH_SHORT).show();
                            String strResponse = "Payment Succeeded \ninvoiceId: " + result.invoiceId + "\nAuthorizationCode: " + result.authorizationCode + "\nDate: " +result.date;
                            paynet_text_response.setText(strResponse);
                        }

                        //This will be fired if user failed payment
                        @Override
                        public void onFailed(ErrorResult result) {

                            Toast.makeText(MainActivity.this, "Payment failed with status " + result.paymentStatus, Toast.LENGTH_SHORT).show();

                            String strResponse = "Payment Succeeded \ninvoiceId: " + result.invoiceId + "\nTransactionId: " + result.transactionId;
                            paynet_text_response.setText(strResponse);
                        }

                        //This ill be fired if user closed payment window. Ther is a boolean parameter 'cancelled' will be received based on user action.
                        //There will be 2 cases you need manage in your app
                        //1. user user closed window in the middle of the payment, then cancelld parameter will be true.
                        //2. if user closed window afre a success or a fialed payment it will be fallse.
                        @Override
                        public void onWindowClosed(Boolean cancelled) {

                            String strResponse = cancelled == true ? "User cancelled payment" : "Payment window closed";
                            paynet_text_response.setText(strResponse);
                            Toast.makeText(MainActivity.this, strResponse, Toast.LENGTH_SHORT).show();
                        }

                        //This will be fired if any general error occured in the middle of a payment.
                        @Override
                        public void onError(Exception error) {

                            String strResponse = "Payment error occured  " + error.getLocalizedMessage();

                            paynet_text_response.setText(paynet_text_response.getText() + "\n" + strResponse);

                            Toast.makeText(MainActivity.this, strResponse, Toast.LENGTH_SHORT).show();

                        }
                    });
        }


    }

    private void closeKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        catch (Exception e) {

        }

    }

}
