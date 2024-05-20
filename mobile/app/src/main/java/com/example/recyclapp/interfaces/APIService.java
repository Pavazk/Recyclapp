package com.example.recyclapp.interfaces;

import com.example.recyclapp.models.User;
import com.example.recyclapp.models.UserLogin;
import com.example.recyclapp.models.UserUpdate;
import com.example.recyclapp.modules.bins.model.Bin;
import com.example.recyclapp.modules.bins.model.Color;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("user/register")
    Call<User> registerUser(@Body User user);

    @POST("user/login")
    Call<User> loginUser(@Body UserLogin user);

    @POST("user/update")
    Call<User> updateUser(@Body UserUpdate user);

    @GET("bins")
    Call<List<Bin>> getAllBins();

    @GET("colors")
    Call<List<Color>> getAllColors();

}
