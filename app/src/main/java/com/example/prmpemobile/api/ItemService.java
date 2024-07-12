package com.example.prmpemobile.api;

import com.example.prmpemobile.model.Item;
import com.example.prmpemobile.model.ItemGetDto;
import com.example.prmpemobile.model.ItemPostDto;
import com.example.prmpemobile.model.LoginResponse;
import com.example.prmpemobile.model.ResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ItemService {

    @GET("items")  // Adjust the endpoint as per your server configuration
    Call<ResponseObject<List<Item>>> getItems();

    @POST("items") // Adjust the endpoint according to your server's configuration
    Call<ResponseObject<ItemGetDto>> createItem(@Body ItemPostDto itemPostDto);

    @PUT("items/{id}") // Adjust the endpoint according to your server's configuration
    Call<ResponseObject<ItemGetDto>> updateItem(@Path("id") Long id, @Body ItemPostDto itemPostDto);

    @DELETE("items/{id}")
    Call<Void> deleteItem(@Path("id") Long id);

}
