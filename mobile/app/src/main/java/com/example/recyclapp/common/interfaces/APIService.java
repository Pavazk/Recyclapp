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
import com.example.recyclapp.modules.products.model.Item;

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



    @GET("event/registerevent/{id}")
    Call<RegisterEvent> getRegisterEvent(@Path("id") Integer id);

    @POST("event/delete")
    Call<String> deleteEvent(@Body Event event);

    @POST("event/update/{id}")
    Call<Event> updateEvent(@Body RegisterEvent event, @Path("id") Integer id);

    @GET("event/user/{code}")
    Call<List<Event>> getAllEventsByUser(@Path("code") String code);

    @GET("event/owner/{code}")
    Call<List<Event>> getAllEventsByOwner(@Path("code") String code);

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

    @GET("item")
    Call<List<Item>> getAllItems();

}
