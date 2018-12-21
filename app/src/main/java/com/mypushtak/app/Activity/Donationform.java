package com.mypushtak.app.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

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
import com.mypushtak.app.DatabaseHelper.ZipcodeDatabaseHelper;
import com.mypushtak.app.Fragment.CityFragment;
import com.mypushtak.app.Fragment.StateFragment;
import com.mypushtak.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Donationform extends FragmentActivity {

    Button submit_button;

    ImageView home_back;
    ImageView cal;
    ImageView fromwherearrow_down;
    ImageView donatedarrow_image;
    ImageView statearrow_down;
    ImageView cityarrow_down;

    EditText name;
    EditText email_id;
    EditText contact_no;
    EditText pincode;
    EditText address;
    EditText landmark;
    EditText city;
    EditText state;
    EditText approx_no_of_books;
    EditText no_of_cartoons;
    EditText preffered_pick_update;
    EditText donated_book_category;
    EditText from_where_you_got_to_know_about_mypustak;

    Calendar calendar = null;

    FragmentManager fm = getSupportFragmentManager();
    StateFragment stateFragment;
    CityFragment cityFragment;
    int state_id;

    ZipcodeDatabaseHelper zipcodeDatabaseHelper;

    @Override
    protected void onStart() {
        super.onStart();
        zipcodeDatabaseHelper = new ZipcodeDatabaseHelper(this);
        zipcodeDatabaseHelper.insertZipcode("123456");
        Log.d("onStart", "count" + zipcodeDatabaseHelper.getZipcodeCount());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationform);

        submit_button = findViewById(R.id.submit_button);
        home_back = findViewById(R.id.home_back);
        cal = findViewById(R.id.cal);
        fromwherearrow_down = findViewById(R.id.fromwherearrow_down);
        donatedarrow_image = findViewById(R.id.donatedarrow_down);
        statearrow_down = findViewById(R.id.statearrow_down);
        cityarrow_down = findViewById(R.id.cityarrow_down);

        name = findViewById(R.id.name);
        email_id = findViewById(R.id.email_id);
        contact_no = findViewById(R.id.contact_no);
        pincode = findViewById(R.id.pincode);
        address = findViewById(R.id.address);
        landmark = findViewById(R.id.landmark);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        approx_no_of_books = findViewById(R.id.approx_no_of_books);
        no_of_cartoons = findViewById(R.id.no_of_cartoons);
        preffered_pick_update = findViewById(R.id.preferred_pickup_date);
        donated_book_category = findViewById(R.id.donated_book_category);
        from_where_you_got_to_know_about_mypustak = findViewById(R.id.from_where_you_got_to_know_about_mypustak);

        fromwherearrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Donationform.this, fromwherearrow_down);
                popupMenu.getMenuInflater().inflate(R.menu.popmenu_fromwhere, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.quoradigest)
                            from_where_you_got_to_know_about_mypustak.setText("Quora Digest");
                        else if (item.getItemId() == R.id.google)
                            from_where_you_got_to_know_about_mypustak.setText("Google");
                        else if (item.getItemId() == R.id.facebook)
                            from_where_you_got_to_know_about_mypustak.setText("Facebook");
                        else if (item.getItemId() == R.id.yahoo)
                            from_where_you_got_to_know_about_mypustak.setText("Yahoo");
                        else if (item.getItemId() == R.id.twitter)
                            from_where_you_got_to_know_about_mypustak.setText("Twitter");
                        else if (item.getItemId() == R.id.others)
                            from_where_you_got_to_know_about_mypustak.setText("Other");
                        else if (item.getItemId() == R.id.newpaper)
                            from_where_you_got_to_know_about_mypustak.setText("Newpaper");
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

        donatedarrow_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Donationform.this, donatedarrow_image);
                popupMenu.getMenuInflater().inflate(R.menu.popmenu_category, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.engineering)
                            donated_book_category.setText("Engineering");
                        else if (item.getItemId() == R.id.novel)
                            donated_book_category.setText("Novel");
                        else if (item.getItemId() == R.id.ncert)
                            donated_book_category.setText("NCERT");
                        else if (item.getItemId() == R.id.others)
                            donated_book_category.setText("Others");
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

        statearrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateFragment = new StateFragment();
                stateFragment.show(fm, "Dialog Fragment");
            }
        });

        cityarrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.getText().toString().equals(""))
                    state.setError("select a state");
                else {
                    cityFragment = new CityFragment().newInstance(state_id);
                    cityFragment.show(fm, "Dialog Fragment");
                }
            }
        });

        calendar = Calendar.getInstance();
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatePickerDialog mDatePicker = new DatePickerDialog(Donationform.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals(""))
                    name.setError("can't be blank");
                else if (!isValidEmail(email_id.getText().toString()))
                    email_id.setError("should be a valid email");
                else if (contact_no.getText().toString().equals(""))
                    contact_no.setError("can't be blank");
                else if (!contact_no.getText().toString().matches("[0-9]+"))
                    contact_no.setError("only numbers are allowed");
                else if (contact_no.getText().toString().length() != 10)
                    contact_no.setError("number should contain 10 digits");
                else if (pincode.getText().toString().equals(""))
                    pincode.setError("can't be blank");
                else if (!pincode.getText().toString().matches("[0-9]+"))
                    pincode.setError("only numbers are allowed");
                else if (pincode.getText().toString().length() != 6)
                    pincode.setError("should contain only 6 digits");
                else if (address.getText().toString().equals(""))
                    address.setError("can't be blank");
                else if (landmark.getText().toString().equals(""))
                    landmark.setError("can't be blank");
//                else if (state.getText().toString().equals(""))
//                    state.setError("can't be blank");
//                else if (city.getText().toString().equals(""))
//                    city.setError("can't be blank");
                else if (approx_no_of_books.getText().toString().equals(""))
                    approx_no_of_books.setError("can't be blank");
                else if (no_of_cartoons.getText().toString().equals(""))
                    no_of_cartoons.setError("can't be blank");
                else if (preffered_pick_update.getText().toString().equals(""))
                    preffered_pick_update.setError("can't be blank");
                else if (donated_book_category.getText().toString().equals(""))
                    donated_book_category.setError("can't be blank");
                else if (from_where_you_got_to_know_about_mypustak.getText().toString().equals(""))
                    from_where_you_got_to_know_about_mypustak.setError("can't be blank");
                else if (pincode.getText().toString().length() == 6) {
                    if (!zipcodeDatabaseHelper.getSearch(pincode.getText().toString()))
                        pincode.setError("service not available");

                    else {

                        Intent i=new Intent(Donationform.this,Signup.class);
                        i.putExtra("signup",email_id.getText().toString());
                        startActivity(i);

                        RequestQueue queue = Volley.newRequestQueue(Donationform.this);

                        String url = "http://192.168.43.243:8080/getUsers/" + email_id.getText().toString();
                        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (!response.equals("null") && !response.equals("")) {
                                    try {
                                        Toast.makeText(Donationform.this, "submitted successfully", Toast.LENGTH_LONG).show();
                                        JSONObject jsonBody = new JSONObject(response);
                                        int id = jsonBody.getInt("id");
                                        Log.d("onResponseId","id: "+id);
                                        donationpostapi(id);
                                        Toast.makeText(Donationform.this, "submitted successfully", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Donationform.this, donationlastpart.class);
                                        startActivity(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(Donationform.this, "email_id not registered", Toast.LENGTH_LONG).show();
                                }

                                Log.d("onResponse", "response: " + response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(postRequest);

                    }
                }
            }
        });

        home_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Donationform.this, Donation.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void onUserStateSelectValue(String selectValue, int id) {
        state.setText(selectValue);
        state_id = id;
        Log.d("onUserSelectValue", "stateId" + state_id);
        stateFragment.dismiss();
    }

    public void onUserCitySelectValue(String selectValue) {
        city.setText(selectValue);
        cityFragment.dismiss();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        preffered_pick_update.setText(sdf.format(calendar.getTime()));
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void donationpostapi(int id) {
        RequestQueue donationqueue = Volley.newRequestQueue(Donationform.this);
        String donor_url = "http://192.168.43.243:8080/donationpost/" + id;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("volunteer_id", "2222");
            jsonBody.put("track_url", "null");
            jsonBody.put("declaration_form", "null");
            jsonBody.put("awb_attachment", "null");
            jsonBody.put("status", "1");
            jsonBody.put("address", address.getText().toString());
            jsonBody.put("country", "101");
            jsonBody.put("state", "24");
            jsonBody.put("city", "1475");
            jsonBody.put("zipcode", "700156");
            jsonBody.put("no_of_book", approx_no_of_books.getText().toString());
            jsonBody.put("no_of_cartons", no_of_cartoons.getText().toString());
            jsonBody.put("app_books_weight", "25");
            jsonBody.put("donated_book_category", donated_book_category.getText().toString());
            jsonBody.put("pickup_date_time", "1459222380");
            jsonBody.put("donation_image", "0528475001459164128.jpg");
            jsonBody.put("how_do_u_know_abt_us", from_where_you_got_to_know_about_mypustak.getText().toString());
            jsonBody.put("wastage", "90");
            jsonBody.put("document_mail_sent", "N");
            jsonBody.put("is_blocked", "N");
            jsonBody.put("i_date", "1459164134");
            jsonBody.put("u_date", "1459164134");
            jsonBody.put("tracking_no", "");
            jsonBody.put("is_paid_donation", "N");
            jsonBody.put("paymrnt_url", "null");
            jsonBody.put("payment_id", "null");
            jsonBody.put("mobile", contact_no.getText().toString());
            jsonBody.put("landmark", landmark.getText().toString());

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

    public void createNewUserApi() {
        final int[] id = new int[1];
        RequestQueue donationqueue = Volley.newRequestQueue(Donationform.this);
        String donor_url = "http://192.168.43.243:8080/userpost/" + email_id.getText().toString();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("first_name", "MyPustak");
            jsonBody.put("last_name", ".com");
            jsonBody.put("alternative_email", "smallboy7311@gmail.com");
            jsonBody.put("password", "12345678");
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

            donationqueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getUserId() {
        final int[] id = new int[1];
        RequestQueue getUserqueue = Volley.newRequestQueue(Donationform.this);
        String getUser_url = "http://192.168.43.243:8080/getUsers/" + email_id.getText().toString();
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
        return id[0];
    }
}
