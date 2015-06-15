package com.example.fssahomepage.app;

import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by masonherhusky on 6/14/15.
 */
public  class CreateCaseActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_case);
        addListenerOnButton();
    }

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText other;
    private Button submit_button;

    public void addListenerOnButton() {

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        other = (EditText) findViewById(R.id.other);
        submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                submit(v);

            }

        });
    }

    public void submit(View view) {
        String nameString = name.getText().toString();
        String emailString = email.getText().toString();
        String phoneString = phone.getText().toString();
        String otherString = other.getText().toString();
        RequestParams params = new RequestParams();
        /*  We don't need to check if parameters are correct, they are checked on the server side.  */
        params.put("name", nameString);
        params.put("email", emailString);
        params.put("phone", phoneString);
        params.put("description", otherString);
        invokeWS(params);
    }

    public void invokeWS(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("https://www.salesforce.com/servlet/servlet.WebToCase?encoding=UTF-8", params, new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("SUCCESS");
                displayResponse(responseBody.toString()); //will toString work for byte[]?
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Code: " + statusCode + "; " + responseBody.toString());
                if (responseBody == null)
                    Toast.makeText(getApplicationContext(), "Something has gone wrong, please check your internet connection.", Toast.LENGTH_LONG).show();
                else
                    displayResponse(responseBody.toString());
            }
        });
    }

    public void displayResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
