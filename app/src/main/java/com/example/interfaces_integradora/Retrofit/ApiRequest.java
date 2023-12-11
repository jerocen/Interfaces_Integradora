package com.example.interfaces_integradora.Retrofit;

import com.example.interfaces_integradora.Models.PostUserChangePassword;
import com.example.interfaces_integradora.Models.PostUserLogin;
import com.example.interfaces_integradora.Models.PostUserPlant;
import com.example.interfaces_integradora.Models.PostUserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiRequest {

@POST("/api/auth/register")
Call<ResponsePostUserRegister> registerUser(@Body PostUserRegister postUserRegister);

@POST("/api/auth/login")
Call<ResponsePostUserLogin> loginUser(@Body PostUserLogin postUserLogin);

@POST("/api/auth/logout")
Call<ResponsePostUserLogout> logoutUser(@Header("Authorization") String token);

@GET("/api/auth/me")
Call<ResponsePostUserMe> meUser(@Header("Authorization") String token);

@POST("/api/auth/create/group")
Call<ResponsePostUserPlant> createPlant(@Header("Authorization") String token, @Body PostUserPlant postUserPlant);

@POST("/api/auth/changepassword")
    Call<ResponsePostUserChangePassword> changePassword(@Header("Authorization") String token, @Body PostUserChangePassword postUserChangePassword);

@GET("/api/auth/all/group")
    Call<ResponseGetUserPlant> getPlants(@Header("Authorization") String token);

}

