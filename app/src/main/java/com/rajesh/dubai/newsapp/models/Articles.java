package com.rajesh.dubai.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Articles implements Parcelable{

    @SerializedName("status")
    public String status;

    @SerializedName("results")
    public ArrayList<Article> list_articles;

    protected Articles(Parcel in) {
        status = in.readString();
        list_articles = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Creator<Articles> CREATOR = new Creator<Articles>() {
        @Override
        public Articles createFromParcel(Parcel in) {
            return new Articles(in);
        }

        @Override
        public Articles[] newArray(int size) {
            return new Articles[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeTypedList(list_articles);
    }
}
