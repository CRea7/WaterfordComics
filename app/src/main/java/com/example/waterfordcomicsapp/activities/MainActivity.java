package com.example.waterfordcomicsapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.waterfordcomicsapp.R;
import com.example.waterfordcomicsapp.adapters.MyAdapter;
import com.example.waterfordcomicsapp.models.Comic;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Base{
    RequestQueue requestQueue;
    Button getButton;
    TextView responseTv;
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    String ReturnString;
    ArrayList<Comic> comicList  = new ArrayList<Comic>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(this);

        test(requestQueue);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(comicList);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test(requestQueue);
                String Text_View = ReturnString;
                //responseTv =  findViewById(R.id.response);
                //responseTv.setText(Text_View);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    int check = 0;

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
                                check = 1;

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
                                    String imageHold = image.getString("path");
                                    extenstion_list = image.getString("extension");
                                    image_list = imageHold + "/portrait_xlarge." + extenstion_list;

                                }

                                if(image_list == "")
                                {
                                    check = 1;
                                }

                                if(check == 1) {
                                    comicList.add(new Comic(id_list, Title_list, image_list, extenstion_list, issueNum_list, storeDate_list, price_list));
                                }
                                Log.i("comic", comicList.get(0).toString());
                                int comiclength = comicList.size();
                                Log.i("ComicCount", String.valueOf(comiclength));
                            }
                            mAdapter.notifyDataSetChanged();
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

    public void AddComic(){

    }
}
