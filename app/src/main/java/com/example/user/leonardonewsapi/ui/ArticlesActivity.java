package com.example.user.leonardonewsapi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.leonardonewsapi.R;
import com.example.user.leonardonewsapi.adapters.NewsArticlesAdapter;
import com.example.user.leonardonewsapi.data.Repository;
import com.example.user.leonardonewsapi.model.NewsArticle;
import com.example.user.leonardonewsapi.model.NewsSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticlesActivity extends AppCompatActivity {

    ArrayList<NewsArticle> newsArticles = new ArrayList<>();
    RecyclerView mRecyclerView;
    NewsArticlesAdapter mAdapter;
    final int pageSize = 30;
    int pageIndex = 1;

    boolean searching = false;
    boolean loadingArticles = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);

        NewsSource source = Repository.getInstance().getLastChosenSource();
        final String sourceId = source.id;
        final String sourceName = source.name;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(sourceName);

        mAdapter = new NewsArticlesAdapter(newsArticles);

        mRecyclerView = findViewById(R.id.articles_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        //Load more articles when user reaches end of list
        //Only when user is not searching
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if (!searching) {
                        pageIndex++;
                        loadData(sourceId, pageSize, pageIndex);
                    }
                }
            }
        });

        loadData(sourceId, pageSize, pageIndex);
    }

    private void loadData(String sourceId, int pageSize, int pageNo){
        if(!loadingArticles){
            loadingArticles = true;
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = String.format("https://newsapi.org/v2/everything" +
                    "?sources=%s" +
                    "&pageSize=%d" +
                    "&page=%d" +
                    "&apiKey=deea836cb7a942699f2e9024b039c2e6", sourceId, pageSize, pageNo);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("articles");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jo = jsonArray.getJSONObject(i);
                                    NewsArticle article = NewsArticle.fromJson(jo);
                                    newsArticles.add(article);
                                }
                                mAdapter.notifyDataSetChanged();
                                loadingArticles = false;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s == null || s.isEmpty()) searching = false;
                else searching = true;

                mAdapter.getFilter().filter(s);
                return false;
            }
        });


        return true;
    }

}
