package com.example.fssahomepage.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    Button logInButton;
    Button nearestCenterButton;
    Button createCaseButton;
    Button scanQRButton;
    Button phoneNumberButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInButton = (Button) findViewById(R.id.loginButton);
        logInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                logInButtonClick();
            }
        });

        nearestCenterButton = (Button) findViewById(R.id.nearestCenterButton);
        nearestCenterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                nearestCenterButtonClick();
            }
        });

        createCaseButton = (Button) findViewById(R.id.createCaseButton);
        createCaseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createCaseButtonClick();
            }
        });

        scanQRButton = (Button) findViewById(R.id.scanQRButton);
        scanQRButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                scanQRButtonClick();
            }
        });

        phoneNumberButton = (Button) findViewById(R.id.phone);
        phoneNumberButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                phoneNumberButtonClick();
            }
        });
    }

    public void logInButtonClick(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fssa.my.salesforce.com"));
        startActivity(browserIntent);
    }

    public void nearestCenterButtonClick(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://in.gov/fssa/dfr/3147.htm"));
        startActivity(browserIntent);
    }

    public void createCaseButtonClick(){
        //Intent intent = new Intent(this, CreateCaseActivity.class);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://civichack.globalresourcesindy.com"));
        startActivity(browserIntent);
        //open new page with input fields that capture key data, and a send field that sends to salesforce
    }

    public void scanQRButtonClick(){
        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);

        }
    }

    public void phoneNumberButtonClick(){
        String phoneNumber = phoneNumberButton.getText().toString();
        String uri = "tel:" + phoneNumber.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contents));
                startActivity(browserIntent);
            }
            if (resultCode == RESULT_CANCELED) {
                //handle cancel
            }
        }
    }


}