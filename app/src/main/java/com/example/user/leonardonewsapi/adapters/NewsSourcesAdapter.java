package com.example.user.leonardonewsapi.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.leonardonewsapi.R;
import com.example.user.leonardonewsapi.data.Repository;
import com.example.user.leonardonewsapi.model.NewsSource;
import com.example.user.leonardonewsapi.ui.ArticlesActivity;

import java.util.ArrayList;

public class NewsSourcesAdapter extends RecyclerView.Adapter<NewsSourcesAdapter.MyViewHolder> {
    private ArrayList<NewsSource> list;
    private Context mContext;

    public NewsSourcesAdapter(ArrayList<NewsSource>list){
        this.list=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.news_source_grid,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        NewsSource source = list.get(position);
        holder.mSourceName.setText(source.name);
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsSource source = list.get(position);

                //Open a list of articles from that source
                Intent intent = new Intent(mContext, ArticlesActivity.class);
                //Save the chosen source to repository
                Repository.getInstance().setLastChosenSource(source);
                mContext.startActivity(intent);
            }
        });
    }

    public void setData(ArrayList<NewsSource> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(list == null) return 0;
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mParent;
        TextView mSourceName;
        ImageView mSourceLogo;

        public MyViewHolder(View itemView) {
            super(itemView);
            mParent = itemView.findViewById(R.id.news_source_card);
            mSourceName= itemView.findViewById(R.id.news_source_name);
            mSourceLogo = itemView.findViewById(R.id.news_source_logo);
        }
    }

}
