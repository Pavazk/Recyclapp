package com.example.isana.interfaces;

import com.example.isana.models.User;
import com.example.isana.models.UserLogin;
import com.example.isana.models.UserUpdate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("user/register")
    Call<User> registerUser(@Body User user);

    @POST("user/login")
    Call<User> loginUser(@Body UserLogin user);

    @POST("user/update")
    Call<User> updateUser(@Body UserUpdate user);

}
