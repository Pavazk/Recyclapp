package com.example.recyclapp.common.interfaces;

import com.example.recyclapp.modules.events.model.Event;
import com.example.recyclapp.modules.events.model.RegisterEvent;
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

    @GET("user/{code}")
    Call<User> getUserByCode(@Path("code") String code);

    @GET("user")
    Call<List<User>> getAllUsers();



    @GET("registerevent/{id}")
    Call<RegisterEvent> getRegisterEvent();

    @POST("event/delete")
    Call<String> deleteEvent(@Body Event event);

    @GET("event/user")
    Call<List<Event>> getAllEventsByUser(@Body User user);

    @GET("event/owner")
    Call<List<Event>> getAllEventsByOwner(@Body User user);

    @POST("event/register")
    Call<Event> registerEvent(@Body RegisterEvent event);

    @GET("event/type")
    Call<List<EventType>> getAllEventType();

    @GET("event/collection")
    Call<List<CollectionType>> getAllCollectionType();

    @GET("bins")
    Call<List<Bin>> getAllBins();

    @POST("bins/register")
    Call<Bin> registerBin(@Body Bin bin);

    @POST("bins/update/{id}")
    Call<Bin> updateBin(@Body Bin bin, @Path("id") Integer id);

    @GET("bins/colors")
    Call<List<Color>> getAllColors();

    @POST("bins/delete")
    Call<String> deleteBin(@Body Bin bin);

}
