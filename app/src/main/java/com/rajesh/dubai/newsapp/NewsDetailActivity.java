package com.rajesh.dubai.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rajesh.dubai.newsapp.models.Article;

public class NewsDetailActivity extends AppCompatActivity {

    Article article;

    TextView article_txt_title,article_txt_by,article_txt_date,article_txt_detail;
    ImageView article_img;

    String title,imageURL,by,date,detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Bundle b=getIntent().getExtras();
        title=b.getString("title");
        imageURL=b.getString("image");
        by=b.getString("by");
        date=b.getString("date");
        detail=b.getString("detail");

        initializeGUI();

    }

    private void initializeGUI() {

        article_txt_title=findViewById(R.id.article_txt_title);
        article_txt_title.setText(title);
        article_txt_by=findViewById(R.id.article_txt_by);
        article_txt_by.setText(by);
        article_txt_date=findViewById(R.id.article_txt_date);
        article_txt_date.setText(date);
        article_txt_detail=findViewById(R.id.article_txt_detail);
        article_txt_detail.setText(detail);
        article_img=findViewById(R.id.article_img);

        Glide.with(NewsDetailActivity.this)
                .load(imageURL)
                .into(article_img);

    }
}
