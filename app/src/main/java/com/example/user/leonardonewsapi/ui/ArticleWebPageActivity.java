package com.example.user.leonardonewsapi.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.user.leonardonewsapi.R;
import com.example.user.leonardonewsapi.model.NewsArticle;

public class ArticleWebPageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        initWebView();
    }

    private void initActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        actionBar.setTitle("");
    }

    private void initWebView(){
        Intent intent = getIntent();
        String url = intent.getStringExtra(NewsArticle.articleUrl);
        WebView myWebView = new WebView(getApplicationContext());
        myWebView.getSettings().setJavaScriptEnabled(true);
        setContentView(myWebView);
        myWebView.loadUrl(url);
    }
}
