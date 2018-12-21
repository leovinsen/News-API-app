package com.example.user.leonardonewsapi.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.leonardonewsapi.adapters.NewsSourcesAdapter;
import com.example.user.leonardonewsapi.model.NewsSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Repository {

    private static Repository repo;

    private ArrayList<NewsSource> newsSources;

    public static Repository getInstance() {
        if (repo == null) {
            repo = new Repository();
        }

        return repo;
    }

    public ArrayList<NewsSource> getSourcesList() {
        return newsSources;
    }

    public void fetchNewsSources(Context context, final NewsSourcesAdapter newsSourcesAdapter) {

        newsSources = new ArrayList<>();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://newsapi.org/v2/sources?apiKey=deea836cb7a942699f2e9024b039c2e6";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("sources");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                NewsSource source = NewsSource.fromJson(jo);
                                newsSources.add(source);
                            }
                            newsSourcesAdapter.setData(newsSources);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        queue.add(stringRequest);

    }


}
