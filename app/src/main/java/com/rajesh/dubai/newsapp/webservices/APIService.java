package com.rajesh.dubai.newsapp.webservices;

import com.rajesh.dubai.newsapp.models.Articles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @Headers("Content-Type:application/json")
    @GET("all-sections/{type}")
    Call<Articles> loadArticles(@Path("type") String type, @Query("api-key") String apiKey);
}
