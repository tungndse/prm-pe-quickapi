package com.example.prmpemobile.api;
import com.example.prmpemobile.model.LoginRequest;
import com.example.prmpemobile.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/login")
    Call<LoginResponse> authenticateUser(@Body LoginRequest loginRequest);
}

