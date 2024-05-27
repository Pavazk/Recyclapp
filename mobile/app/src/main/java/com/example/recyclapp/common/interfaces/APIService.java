package com.example.recyclapp.common.interfaces;

import com.example.recyclapp.modules.main.data.User;
import com.example.recyclapp.modules.main.data.UserLogin;
import com.example.recyclapp.modules.main.data.UserUpdate;
import com.example.recyclapp.modules.bins.model.Bin;
import com.example.recyclapp.modules.bins.model.Color;
import com.example.recyclapp.modules.events.model.CollectionType;
import com.example.recyclapp.modules.events.model.EventType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @POST("user/register")
    Call<User> registerUser(@Body User user);

    @POST("user/login")
    Call<User> loginUser(@Body UserLogin user);

    @POST("user/update")
    Call<User> updateUser(@Body UserUpdate user);

    @POST("user/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("user")
    Call<List<User>> getAllUsers();

    @GET("eventtype")
    Call<List<EventType>> getAllEventType();

    @GET("collectiontype")
    Call<List<CollectionType>> getAllCollectionType();

    @GET("bins")
    Call<List<Bin>> getAllBins();

    @GET("colors")
    Call<List<Color>> getAllColors();

}
