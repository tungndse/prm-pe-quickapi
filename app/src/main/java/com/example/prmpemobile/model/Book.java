package com.example.prmpemobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Book {
    private String name;
    @SerializedName("date_published")
    private Date datePublished;

    @SerializedName("author_name")
    private String authorName;
    private String genre;

    // Add getters for the fields
    public String getName() {
        return name;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getGenre() {
        return genre;
    }
}
