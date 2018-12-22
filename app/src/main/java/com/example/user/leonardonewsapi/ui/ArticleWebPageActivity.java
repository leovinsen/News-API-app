package com.example.user.leonardonewsapi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.user.leonardonewsapi.R;

public class ArticleWebPageActivity extends AppCompatActivity{

    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_article_web_page);
        mWebView = findViewById(R.id.article_web_view);
        mWebView.loadUrl("http://www.example.com");
    }
}
