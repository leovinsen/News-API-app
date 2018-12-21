package com.example.user.leonardonewsapi.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.leonardonewsapi.adapters.NewsSourcesAdapter;
import com.example.user.leonardonewsapi.R;
import com.example.user.leonardonewsapi.data.Repository;

public class MainActivity extends AppCompatActivity {

    //IDEA: static arraylist so t is cached
    //final ArrayList<NewsSource> newsSources = new ArrayList<>();

    RecyclerView mRecyclerView;
    NewsSourcesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new NewsSourcesAdapter(Repository.getInstance().getSourcesList());
        Repository.getInstance().fetchNewsSources(getApplicationContext(), mAdapter);

        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.HORIZONTAL));
    }
}
