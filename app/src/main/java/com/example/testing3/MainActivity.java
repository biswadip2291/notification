package com.example.testing3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpsTrustManager.allowAllSSL();




        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                EditText mail = findViewById(R.id.Email);

                String Email = mail.getText().toString();

                login(Email);

            }
        });
        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                EditText mail = findViewById(R.id.Email);

                String Email = mail.getText().toString();

                register(Email);
            }
        });


    }


    //to register data

    private void register(String Email) {
        String UUID = OneSignal.getDeviceState().getUserId();
//

//
                        StringRequest stringrequest = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/register.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> getparams = new HashMap<String, String>();
                                getparams.put("tokens", UUID);
                                getparams.put("email", Email);
                                return getparams;
                            }
                        };
                        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
                        rq.add(stringrequest);
        Toast.makeText(this, "register sucess", Toast.LENGTH_SHORT).show();
                    }





void login(String Email){
    String UUID = OneSignal.getDeviceState().getUserId();
    if (UUID != null) {
        Log.e("o", UUID);
    }

    // to retrive data

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






                        if(email.equals(Email))
                        {
                            check=true;
                            Toast.makeText(MainActivity.this, "login sucess", Toast.LENGTH_SHORT).show();
                            Log.e("l",Email);

                            //to update data
                            StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/update.php", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> getparams = new HashMap<String, String>();
                                    getparams.put("email",Email);
                                    getparams.put("player", UUID);

                                    return getparams;
                                }
                            };
                            RequestQueue r = Volley.newRequestQueue(MainActivity.this);
                            r.add(request);
                          Intent intent=new Intent(MainActivity.this,sendnotification.class);
                          intent.putExtra("key",Email);
                          startActivity(intent);

                        }


                    }
                    if(check==false)
                    {
                        Toast.makeText(MainActivity.this, "register first", Toast.LENGTH_SHORT).show();
                    }



                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
        rq.add(request);




}


void logout(){
    String UUID = OneSignal.getDeviceState().getUserId();
    StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/update2.php", new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> getparams = new HashMap<String, String>();

            getparams.put("token", UUID);

            return getparams;
        }
    };
    RequestQueue r = Volley.newRequestQueue(MainActivity.this);
    r.add(request);


}



}




