package com.example.interfaces_integradora.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRequest {

@POST("/api/auth/register")
Call<ResponsePostUserRegister> registerUser(@Body PostUserRegister postUserRegister);

}
