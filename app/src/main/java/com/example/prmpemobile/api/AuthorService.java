package com.example.prmpemobile.api;

import com.example.prmpemobile.model.Author;
import com.example.prmpemobile.model.ResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthorService {
    @GET("authors")
    Call<ResponseObject<List<Author>>> getAuthors();
}
