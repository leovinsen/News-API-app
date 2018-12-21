package com.example.user.leonardonewsapi.model;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsSource {

    static public final String sourceId = "com.example.user.leonardonewsapi.sourceId";
    static public final String sourceName = "com.example.user.leonardonewsapi.sourceName";
    public final String id;
    public final String name;
    public final String imgUrl; //for News Source icon

    public NewsSource(String id, String name, String imgUrl){
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public static NewsSource fromJson(JSONObject jsonObject) {
       NewsSource source;
        // Deserialize json into object fields
        try {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String imgUrl = null;
             source = new NewsSource(id, name, imgUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return source;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ");
        sb.append(id);
        sb.append(", Name: ");
        sb.append(name);
        return sb.toString();
    }
}
