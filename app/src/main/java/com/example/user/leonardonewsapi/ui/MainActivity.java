package com.example.user.leonardonewsapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.leonardonewsapi.adapters.NewsSourcesAdapter;
import com.example.user.leonardonewsapi.R;
import com.example.user.leonardonewsapi.data.Repository;

public class MainActivity extends AppCompatActivity {

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
    }
}
