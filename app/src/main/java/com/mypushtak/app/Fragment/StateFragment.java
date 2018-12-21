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
import com.mypushtak.app.DatabaseHelper.StateDatabaseHelper;
import com.mypushtak.app.R;
import com.mypushtak.app.Helper.State;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StateFragment extends DialogFragment {

    ListView lvStates;
    ArrayList<State> states=new ArrayList<>();
    ArrayAdapter<State> stateAdapter;

    StateDatabaseHelper db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v=getActivity().getLayoutInflater().inflate(R.layout.state_fragment, null);
        db=new StateDatabaseHelper(getActivity());
        lvStates=v.findViewById(R.id.lvStaes);
        stateAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, states);

        lvStates.setAdapter(stateAdapter);
        refreshStateList();
        if(db.getStatesCount()==0)
            stateApi(v);

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        lvStates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Donationform callingActivity= (Donationform) getActivity();
                callingActivity.onUserStateSelectValue(states.get(position).getState_name(), states.get(position).getId());
            }
        });

        builder.setView(v);
        return builder.create();
    }

    private void stateApi(View v){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.43.243:8080/getState";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject state_object=jsonArray.getJSONObject(i);
                        String state_name=state_object.getString("stateName");
                        db.insertNote(state_name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refreshStateList();
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
        List<State> stateslist=db.getAllStates();
        states.clear();
        states.addAll(stateslist);
        stateAdapter.notifyDataSetChanged();
    }
}
