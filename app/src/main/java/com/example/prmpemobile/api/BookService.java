package com.example.prmpemobile.api;

import com.example.prmpemobile.model.Book;
import com.example.prmpemobile.model.BookGetDto;
import com.example.prmpemobile.model.BookPostDto;
import com.example.prmpemobile.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BookService {

    @GET("books")
    Call<ResponseObject<List<Book>>> getBooks();

    @POST("books")
    Call<ResponseObject<Book>> createBook(@Body BookPostDto bookPostDto);

    @PUT("books/{id}")
    Call<ResponseObject<Book>> updateBook(@Path("id") Long id, @Body BookPostDto bookPostDto);

    @DELETE("books/{id}")
    Call<Void> deleteBook(@Path("id") Long id);
}
