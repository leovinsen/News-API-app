package com.example.user.leonardonewsapi.model;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsArticle {

    final public String title;
    final public String description;
    final public String url;
    final public String urlToImage;
    final public String publishedAt;

    public NewsArticle(String title, String description, String url, String urlToImage, String publishedAt) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public static NewsArticle fromJson(JSONObject jsonObject) {
        NewsArticle article;
        // Deserialize json into object fields
        try {
            String title = jsonObject.getString("title");
            String desc = jsonObject.getString("description");
            String url = jsonObject.getString("url");
            String urlToImage = jsonObject.getString("urlToImage");
            String publishedAt = jsonObject.getString("publishedAt");
            article = new NewsArticle(title, desc, url, urlToImage, publishedAt);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return article;
    }
}
