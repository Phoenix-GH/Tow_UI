package com.noname.akio.named.clientAPI;

import com.noname.akio.named.clientAPI.Request.TowImageRequest;
import com.noname.akio.named.clientAPI.Request.TowRequest;
import com.noname.akio.named.clientAPI.Request.User;
import com.noname.akio.named.clientAPI.Request.VehicleItemRequest;
import com.noname.akio.named.clientAPI.Response.Contracts.Contracts;
import com.noname.akio.named.clientAPI.Response.Login.LoginResponse;
import com.noname.akio.named.clientAPI.Response.Me.MeResponse;
import com.noname.akio.named.clientAPI.Response.Prices.Prices;
import com.noname.akio.named.clientAPI.Response.Storages.Storages;
import com.noname.akio.named.clientAPI.Response.TowImage.TowImageResponse;
import com.noname.akio.named.clientAPI.Response.Tows.TowSingleResponse;
import com.noname.akio.named.clientAPI.Response.Tows.Tows;
import com.noname.akio.named.clientAPI.Response.Vehicles.VehicleLists;
import com.noname.akio.named.clientAPI.Response.Vehicles.Vehicles;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestAPI {
    String BASE_URL = "http://api.apartmentpermits.com/api/";
    // Login

    @POST("auth/login")
    Call<LoginResponse> postLogin(@Body User user);

    //Contracts
    @GET("contracts")
    Call<Contracts> getContracts();

    @GET("contracts/{id}")
    String getContracts(@Path("id") String id);

    @GET("contracts/{id}/vehicle_lists")
    Call<VehicleLists> getVehicleLists(@Path("id") int id);

    @POST("contracts/{id}/vehicle_lists")
    String postVehicleLists(@Path("id") String id);

    @GET("tows")
    Call<Tows> getTows();
    @POST("tows")
    Call<TowSingleResponse> postTows(@Body TowRequest request);
    @POST("tows/{id}/images")
    Call<TowImageResponse> postImagesForTows(@Path("id") int id, @Body TowImageRequest request);

    @GET("storages")
    Call<Storages> getStorages();

    @GET("auth/me")
    Call<MeResponse> getMe();

    @GET("prices")
    Call<Prices> getPrices();

    @GET("contracts/{id}/vehicle_lists/{id2}/vehicles")
    Call<Vehicles> getVehicleById(@Path("id") int id, @Path("id2") int id2);

    @POST("contracts/{id}/vehicle_lists/{id2}/vehicles")
    Call<Vehicles> postVehicleById(@Path("id") int id, @Path("id2") int id2, @Body VehicleItemRequest request);

    // API instance class
    class Factory {
        private static RestAPI service;

        public static RestAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(RestAPI.class);
                return service;
            } else {
                return service;
            }
        }
    }
}

