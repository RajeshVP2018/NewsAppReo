package com.rajesh.dubai.newsapp.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rajesh.dubai.newsapp.R;
import com.rajesh.dubai.newsapp.models.Article;
import com.rajesh.dubai.newsapp.models.MediaMetadatum;
import com.rajesh.dubai.newsapp.models.Medium;

import java.util.ArrayList;

public class ArticlesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Article> list;
    private Context mContext;
    private static OnArticleClickListener onArticleClickListener;

    public ArticlesListAdapter(Context mContext, ArrayList<Article> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_article_item, parent, false);
        return new ArticleItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ArticleItemHolder) {
            ArticleItemHolder articleItemHolder = (ArticleItemHolder) holder;

            Article article = list.get(position);

            articleItemHolder.txt_title.setText(article.getTitle());
            articleItemHolder.txt_by.setText(article.getByline());
            articleItemHolder.row_article_txt_date.setText(article.getPublishedDate());

            ArrayList<Medium> medium = (ArrayList<Medium>) article.getMedia();

            ArrayList<MediaMetadatum> mmData =null;
            String imageURL = "";

            if(medium.size()>0)
            {
                mmData= (ArrayList<MediaMetadatum>) medium.get(0).getMediaMetadata();

                if (mmData != null) {

                    imageURL = mmData.get(0).getUrl();
                } else
                    imageURL = "";
            }

            Glide.with(mContext)
                    .load(imageURL)
                    .apply(RequestOptions.circleCropTransform())
                    .into(articleItemHolder.img_pic);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private static class ArticleItemHolder extends RecyclerView.ViewHolder {

        private TextView txt_title;
        private TextView txt_by;
        private TextView row_article_txt_date;
        private ImageView img_pic;

        ArticleItemHolder(View v) {
            super(v);

            txt_title = v.findViewById(R.id.row_article_txt_title);
            txt_by = v.findViewById(R.id.row_article_txt_by);
            row_article_txt_date = v.findViewById(R.id.row_article_txt_date);
            img_pic = v.findViewById(R.id.row_article_img);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    onArticleClickListener.onArticleClick(getAdapterPosition());
                }
            });
        }


    }

    public void setOnArticleClickListener(OnArticleClickListener onArticleClickListener) {
        this.onArticleClickListener = onArticleClickListener;
    }

    public interface OnArticleClickListener {
        void onArticleClick(int position);
    }
}
