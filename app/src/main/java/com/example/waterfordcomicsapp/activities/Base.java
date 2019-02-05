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

import org.json.JSONArray;
import org.json.JSONObject;

public class Base extends AppCompatActivity {
    String ReturnString;
    //Declare a private RequestQueue variable

    private static Base mInstance;





    public void test(RequestQueue requestQueue){

        String url = "http://gateway.marvel.com/v1/public/comics?ts=" + getString(R.string.ts) +"&apikey="+ getString(R.string.public_key) +"&hash=" + getString(R.string.hash);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            JSONObject init_list = response.getJSONObject("data");
                            //JSONArray result_list = init_list.getJSONArray("results");
                            Log.i("INFO",init_list.toString());
                            ReturnString = init_list.toString();
                        }
                        catch(Exception e)
                        {
                            Log.i("ERROR",e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TEST",error.toString());
                    }
                });
        // Adding the request to the queue along with a unique string tag
        requestQueue.add(jsonObjReq);

    }






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