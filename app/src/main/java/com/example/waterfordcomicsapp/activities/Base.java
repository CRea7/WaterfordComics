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
    String ReturnString;
    ArrayList<Comic> comicList  = new ArrayList<Comic>();
    //Declare a private RequestQueue variable

    private static Base mInstance;


    public void test(RequestQueue requestQueue){

        String url = "http://gateway.marvel.com/v1/public/comics?ts=" + getString(R.string.ts) +"&apikey="+ getString(R.string.public_key) +"&hash=" + getString(R.string.hash) + "&limit=100";


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            JSONObject init_list = response.getJSONObject("data");
                            JSONArray result_list = init_list.getJSONArray("results");
                            int count = result_list.length();
                            String countS = String.valueOf(count);
                            Log.i("count", countS);
                            for(int x=0; x < result_list.length(); x++){
                                String Title_list;
                                String issueNum_list;
                                String image_list="";
                                String extenstion_list="";
                                String storeDate_list="";
                                String price_list="";
                                String id_list;

                                //need to get JSONObjects here and add them to model
                                JSONObject item = result_list.getJSONObject(x);
                                JSONArray images = item.getJSONArray("images");

                                    //add stuff here as i want images for all comics displayed
                                    //JSONObject ComicTitle = item.getJSONObject("title");
                                    Title_list = item.getString("title");

                                    //JSONObject ComicIssueNum = item.getJSONObject("issueNumber");
                                    issueNum_list = item.getString("issueNumber");

                                    //JSONObject ComicID = item.getJSONObject("id");
                                    id_list = item.getString("id");

                                    JSONArray Prices = item.getJSONArray("prices");
                                    for(int y=0; y < Prices.length(); y++)
                                    {
                                        JSONObject priceItem = Prices.getJSONObject(y);
                                        String imageType = priceItem.getString("type");
                                        if(imageType == "printPrice")
                                        {
                                            //JSONObject price = priceItem.getJSONObject("price");
                                            price_list = item.getString("price");
                                        }
                                    }
                                    JSONArray Dates = item.getJSONArray("dates");
                                    for(int z = 0; z < Dates.length(); z++) {
                                        JSONObject dateItem = Dates.getJSONObject(z);
                                        String dateType = dateItem.getString("type");
                                        if (dateType == "onsaleDate")
                                        {
                                            //JSONObject date = dateItem.getJSONObject("date");
                                            storeDate_list = item.getString("date");
                                        }
                                    }
                                    JSONArray Images = item.getJSONArray("images");
                                    for(int c = 0; c < Images.length(); c++)
                                    {
                                        JSONObject image = Images.getJSONObject(c);

                                        //JSONObject path = image.getJSONObject("path");
                                        image_list = image.getString("path");

                                        //JSONObject extenstion = image.getJSONObject("extension");
                                        extenstion_list = image.getString("extension");
                                    }

                                    comicList.add(new Comic(id_list,Title_list,image_list,extenstion_list,issueNum_list,storeDate_list,price_list));

                                Log.i("comic", comicList.get(0).toString());
                            }
                            Log.i("INFO",init_list.toString());
                            ReturnString = comicList.toString();
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