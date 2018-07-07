package com.rajesh.dubai.newsapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.rajesh.dubai.newsapp.adapters.ArticlesListAdapter;
import com.rajesh.dubai.newsapp.models.Article;
import com.rajesh.dubai.newsapp.models.Articles;
import com.rajesh.dubai.newsapp.webservices.APIService;
import com.rajesh.dubai.newsapp.webservices.ApiClient;
import com.rajesh.dubai.newsapp.webservices.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    RecyclerView list_recyl_news;
    List<Article> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("News App");

        // Initialize GUI elements
        initializeGUI();

        // Loading news list from api
        loadArticles();
    }

    private void initializeGUI() {

        list_recyl_news = findViewById(R.id.list_recyl_news);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_recyl_news.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void loadArticles(){

        if (Util.networkStatus(this)) {
            try {

                Util.showProgress(this);

                APIService apiService = ApiClient.createService(APIService.class, this);

                // Most Viewed
                Call<Articles> call = apiService.loadArticles("30.json","fcb018ce7be04b8fb9588df578ae9378");

                // Most emailed
                //Call<Articles> call = apiService.loadArticles("1.json","fcb018ce7be04b8fb9588df578ae9378");

                // Most Shared
                //Call<Articles> call = apiService.loadArticles("7.json","fcb018ce7be04b8fb9588df578ae9378");

                call.enqueue(new Callback<Articles>() {
                    @Override
                    public void onResponse(Call<Articles> call, Response<Articles> response) {
                        Util.hideProgress();
                        if (response.code() == 200) {
                            String status = response.body().status;
                            if(status != null && status.equalsIgnoreCase("OK")){
                                list = response.body().list_articles;

                                ArticlesListAdapter articlesListAdapter = new ArticlesListAdapter(MainActivity.this, (ArrayList<Article>) list);
                                list_recyl_news.setAdapter(articlesListAdapter);
                                articlesListAdapter.setOnArticleClickListener(new ArticlesListAdapter.OnArticleClickListener() {
                                    @Override
                                    public void onArticleClick(int position) {
                                        Article article=list.get(position);

                                        Bundle b = new Bundle();
                                        b.putString("title", article.getTitle());
                                        b.putString("image", article.getMedia().get(0).getMediaMetadata().get(0).getUrl());
                                        b.putString("by", article.getByline());
                                        b.putString("date", article.getPublishedDate());
                                        b.putString("detail", article.getNewsDetail());

                                        Intent detailActivity=new Intent(MainActivity.this,NewsDetailActivity.class);
                                        detailActivity.putExtras(b);
                                        //detailActivity.putExtra("url",article.getUrl());
                                        startActivity(detailActivity);

                                    }


                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Articles> call, Throwable t) {
                        Util.hideProgress();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Util.hideProgress();
            }
        }else{
            Util.hideProgress();
        }
    }

}
