package com.example.waterfordcomicsapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.waterfordcomicsapp.R;
import com.example.waterfordcomicsapp.models.Comic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Base extends AppCompatActivity {

    //Declare a private RequestQueue variable
    public String userEmail = "";

    private static Base mInstance;









//    public static synchronized Base getInstance() {
//        return mInstance;
//    }
//    public RequestQueue getRequestQueue() {
//        if (requestQueue == null)
//            requestQueue = Volley.newRequestQueue(this);
//        return requestQueue;
//    }
//    public void addToRequestQueue(Request request, String tag) {
//        request.setTag(tag);
//        getRequestQueue().add(request);
//    }
//    public void cancelAllRequests(String tag) {
//        getRequestQueue().cancelAll(tag);
//    }

//    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//            url, (String) null,
//            new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    //Success Callback
//                }
//            },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    //Failure Callback
//                }
//            });
//    // Adding the request to the queue along with a unique string tag
//    requestQueue.add(jsonObjectReq);

}