package com.example.user.leonardonewsapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.leonardonewsapi.R;
import com.example.user.leonardonewsapi.model.NewsArticle;
import com.example.user.leonardonewsapi.ui.ArticleWebPageActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsArticlesAdapter extends RecyclerView.Adapter<NewsArticlesAdapter.MyViewHolder> implements Filterable{

    private ArrayList<NewsArticle> originalData;
    private ArrayList<NewsArticle> filteredData;
    private Context mContext;

    public NewsArticlesAdapter(ArrayList<NewsArticle>list){
        this.originalData=list;
        this.filteredData=list;
    }
    @Override
    public NewsArticlesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.news_article_card,parent,false);

        return new NewsArticlesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsArticlesAdapter.MyViewHolder holder, final int position) {
        NewsArticle article = filteredData.get(position);

        //Load image
        Picasso.get()
                .load(article.urlToImage)
                .error(R.drawable.ic_no_image)
                .centerCrop()
                .fit()
                .into(holder.mArticleThumbnail);
        //Set Title
        holder.mArticleTitle.setText(article.title);

        System.out.println(String.format("%d : publishedat: %s", position, article.publishedAt));

        //Get Date object of publication time
        Date publishedTime = formatDate(article.publishedAt);

        //Get time difference between current time and time of article publication
        //Then convert into a more user friendly string format
        String dateString = getPrettyTimeDifference(timeDifference(publishedTime, new Date()));

        //Set time difference
        holder.mArticlePublishTime.setText(dateString);

        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = filteredData.get(position).url;
                Intent intent = new Intent(mContext, ArticleWebPageActivity.class);
                intent.putExtra(NewsArticle.articleUrl, url); //Optional parameters
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = originalData;
                    results.count = originalData.size();
                }
                else
                {
                    ArrayList<NewsArticle> filterResultsData = new ArrayList<>();

                    for(NewsArticle article : originalData)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                        if(article.title.contains(charSequence))
                        {
                            filterResultsData.add(article);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredData = (ArrayList<NewsArticle>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        if(filteredData == null) return 0;
        return filteredData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCard;
        TextView mArticleTitle;
        TextView mArticlePublishTime;
       ImageView mArticleThumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCard = itemView.findViewById(R.id.article_card);
            mArticleTitle = itemView.findViewById(R.id.article_title);
            mArticlePublishTime = itemView.findViewById(R.id.article_time);
            mArticleThumbnail = itemView.findViewById(R.id.article_thumbnail);
        }
    }

    /*
        Takes in a String variable that contains ISO 8601 timestamp
        Returns a Date object from that timestamp
     */
    private Date formatDate(String dateString){
        SimpleDateFormat format;
        //If length > 20, the date contains milliseconds
        //and will throw a ParseException if parsed with pattern "yyyy-MM-dd'T'HH:mm:ss'Z'"
        //Java.Time class is not used because DateTimeFormatter.ofPattern requires API 26 and above

        //The following pattern includes milliseconds
        if(dateString.length() > 20) format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //The folowing pattern does not include milliseconds
        else format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            date = null;
            e.printStackTrace();
        }

        return date;
    }

    private long[] timeDifference(Date startDate, Date endDate){
        if(startDate == null | endDate == null) return null;

        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        return new long[]{elapsedHours, elapsedMinutes};
    }

    private String getPrettyTimeDifference(long[] timeDifferences){
        if(timeDifferences == null) return "Unable to get time difference";
        long elapsedHours = timeDifferences[0];
        long elapsedMinutes = timeDifferences[1];
        String dateString = null;
        //Show in days
        if(elapsedHours == 0){
            if(elapsedMinutes == 0) dateString = "now";
            else if(elapsedMinutes ==1) dateString ="1 minute ago";
            else dateString = String.format("%d minutes ago", elapsedMinutes);
        } else if(elapsedHours < 24){
            dateString = String.format("%d hours ago", elapsedHours);
        } else {
            long elapsedDays = elapsedHours / 24;
            if(elapsedDays == 1) dateString = "Yesterday";
            else dateString = String.format("%d days ago", elapsedHours / 24);
        }

        return dateString;
    }

}
