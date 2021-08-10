package com.example.testing3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class sendnotification extends AppCompatActivity {
String TOKEN="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendnotification);


        String email=getIntent().getStringExtra("key");
        retrive(email);

        Button button =findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title=findViewById(R.id.titlee);
                String Title=title.getText().toString();

                EditText message=findViewById(R.id.messagee);
                String Message=message.getText().toString();



                send1(TOKEN,Title,Message);

            }});

            Button button2 =findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                EditText title=findViewById(R.id.titlee);
                String Title=title.getText().toString();

                EditText message=findViewById(R.id.messagee);
                String Message=message.getText().toString();

                    send2(TOKEN,Title,Message);

                }
        }
        );


    }
    private void retrive(String Email) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/retrive.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array= new JSONArray(response);
                    Boolean check= false;
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        String email=object.getString("email");
                        String token=object.getString("tokens");




                        if(email.equals(Email))
                        {

                         TOKEN=token;
                            Toast.makeText(sendnotification.this, TOKEN, Toast.LENGTH_SHORT).show();

                        }


                    }



                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(sendnotification.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue rq = Volley.newRequestQueue(sendnotification.this);
        rq.add(request);


    }

    void send1(String token,String title,String message){
        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/send2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(sendnotification.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> getparams = new HashMap<String, String>();
                getparams.put("tok", token);
                getparams.put("title", title);
                getparams.put("message", message);

                return getparams;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Toast.makeText(sendnotification.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue r = Volley.newRequestQueue(sendnotification.this);
        r.add(request);
    }


     void send2(String token,String title,String message){
         StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/send3.php", new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(sendnotification.this, error.toString(), Toast.LENGTH_SHORT).show();

             }
         }) {
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> getparams = new HashMap<String, String>();
                 getparams.put("tok", token);
                 getparams.put("title", title);
                 getparams.put("message", message);


                 return getparams;
             }
         };
         request.setRetryPolicy(new RetryPolicy() {
             @Override
             public int getCurrentTimeout() {
                 return 50000;
             }

             @Override
             public int getCurrentRetryCount() {
                 return 50000;
             }

             @Override
             public void retry(VolleyError error) throws VolleyError {
                 Toast.makeText(sendnotification.this, error.toString(), Toast.LENGTH_SHORT).show();

             }
         });
         RequestQueue r = Volley.newRequestQueue(sendnotification.this);
         r.add(request);


    }
    }
