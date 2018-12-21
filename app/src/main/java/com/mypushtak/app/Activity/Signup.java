package com.mypushtak.app.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mypushtak.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Signup extends AppCompatActivity {

    EditText email;
    EditText password;
    ImageView showVisible;
    ImageView showVisibleoff;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        showVisible=findViewById(R.id.showvisible);
        showVisibleoff=findViewById(R.id.showvisibleoff);
        signup=findViewById(R.id.signup);

        String emailid=getIntent().getStringExtra("signup");
        email.setText(emailid);


        showVisibleoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showVisible.setVisibility(View.VISIBLE);
                showVisibleoff.setVisibility(View.GONE);
            }
        });

        showVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showVisibleoff.setVisibility(View.VISIBLE);
                showVisible.setVisibility(View.GONE);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                createNewUser();
            }
        });
    }

    public void createNewUser(){
        RequestQueue queue = Volley.newRequestQueue(Signup.this);
        String donor_url = "http://192.168.43.243:8080/userpost/" + email.getText().toString();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("first_name", "name");
            jsonBody.put("last_name", ".com");
            jsonBody.put("alternative_email", "smallboy7311@gmail.com");
            jsonBody.put("password", password.getText().toString());
            jsonBody.put("user_role_id", "1");
            jsonBody.put("avatar", "12345678.jpg");
            jsonBody.put("birth_date", "04/13/2012");
            jsonBody.put("communication_address", "test");
            jsonBody.put("mobile", "12345678");
            jsonBody.put("contact_no", "null");
            jsonBody.put("profession", "null");
            jsonBody.put("contribution", "null");
            jsonBody.put("profile_percentage", "100");
            jsonBody.put("is_volunteer", "N");
            jsonBody.put("is_donor", "N");
            jsonBody.put("is_customer", "N");
            jsonBody.put("is_verified", "Y");
            jsonBody.put("is_deleted", "N");
            jsonBody.put("is_active", "Y");
            jsonBody.put("i_date", "12345678");
            jsonBody.put("i_by", "1");
            jsonBody.put("u_date", "1459164134");
            jsonBody.put("u_by", "1");
            jsonBody.put("registered_date", "2015-10-09");
            jsonBody.put("wallet_amount", "0");

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, donor_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                        Log.d("onResponseString", "response: " + responseString);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            queue.add(stringRequest);
            getUserId();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getUserId() {
        final int[] id = new int[1];
        RequestQueue getUserqueue = Volley.newRequestQueue(Signup.this);
        String getUser_url = "http://192.168.43.243:8080/getUsers/" + email.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.GET, getUser_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonBody = new JSONObject(response);
                    id[0] = jsonBody.getInt("id");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("onResponse", "response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getUserqueue.add(postRequest);
        donationpost(id[0]);
    }

    public void donationpost(int id){
        RequestQueue donationqueue = Volley.newRequestQueue(Signup.this);
        String donor_url = "http://192.168.43.243:8080/donationpost/" + id;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("volunteer_id", "2222");
            jsonBody.put("track_url", "null");
            jsonBody.put("declaration_form", "null");
            jsonBody.put("awb_attachment", "null");
            jsonBody.put("status", "1");
            jsonBody.put("address", "address.getText().toString()");
            jsonBody.put("country", "101");
            jsonBody.put("state", "24");
            jsonBody.put("city", "1475");
            jsonBody.put("zipcode", "700156");
            jsonBody.put("no_of_book", "approx_no_of_books.getText().toString()");
            jsonBody.put("no_of_cartons", "no_of_cartoons.getText().toString()");
            jsonBody.put("app_books_weight", "25");
            jsonBody.put("donated_book_category", "donated_book_category.getText().toString()");
            jsonBody.put("pickup_date_time", "1459222380");
            jsonBody.put("donation_image", "0528475001459164128.jpg");
            jsonBody.put("how_do_u_know_abt_us", "from_where_you_got_to_know_about_mypustak.getText().toString()");
            jsonBody.put("wastage", "90");
            jsonBody.put("document_mail_sent", "N");
            jsonBody.put("is_blocked", "N");
            jsonBody.put("i_date", "1459164134");
            jsonBody.put("u_date", "1459164134");
            jsonBody.put("tracking_no", "");
            jsonBody.put("is_paid_donation", "N");
            jsonBody.put("paymrnt_url", "null");
            jsonBody.put("payment_id", "null");
            jsonBody.put("mobile", "contact_no.getText().toString()");
            jsonBody.put("landmark", "landmark.getText().toString()");

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, donor_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                        Log.d("onResponseString", "response: " + responseString);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            donationqueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
