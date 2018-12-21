package com.mypushtak.app.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mypushtak.app.Activity.Donationform;
import com.mypushtak.app.Helper.City;
import com.mypushtak.app.DatabaseHelper.CityDatabaseHelper;
import com.mypushtak.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CityFragment extends DialogFragment {

    ListView lvCities;
    ArrayList<City> cities=new ArrayList<>();
    ArrayAdapter<City> cityAdapter;

    CityDatabaseHelper db;

    int stateId;

    public static CityFragment newInstance(int state_id) {

        Bundle args = new Bundle();

        CityFragment fragment = new CityFragment();
        args.putInt("state_id",state_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v=getActivity().getLayoutInflater().inflate(R.layout.city_fragment, null);
        stateId=getArguments().getInt("state_id");
        Log.d("onCreateDialog","stateId"+stateId);
        db=new CityDatabaseHelper(getActivity());
        lvCities=v.findViewById(R.id.lvCities);
        cityAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, cities);

        lvCities.setAdapter(cityAdapter);

        if(db.getCitiesCount()==0) {
            cityApi();
            refreshStateList();
        }
        else
            refreshStateList();

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        lvCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Donationform callingActivity= (Donationform) getActivity();
                callingActivity.onUserCitySelectValue(cities.get(position).getCity());
            }
        });

        builder.setView(v);
        return builder.create();
    }

    private void cityApi(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.43.243:8080/getCity";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject city_object=jsonArray.getJSONObject(i);
                        String city=city_object.getString("city");
                        int state_id=city_object.getInt("stateId");
                        Log.d("onCreateDialog", "state_id"+state_id);
                        db.insertCity(city, state_id);
                    }
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
        queue.add(postRequest);
    }

    private void refreshStateList(){
        List<City> citylist=db.getCity(stateId);
        cities.clear();
        cities.addAll(citylist);
        Log.d("onRefresh","cities"+db.getCity(stateId));
        cityAdapter.notifyDataSetChanged();
    }
}
