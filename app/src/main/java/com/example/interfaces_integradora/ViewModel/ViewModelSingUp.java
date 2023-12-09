package com.example.interfaces_integradora.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Retrofit.ApiRequest;
import com.example.interfaces_integradora.Models.PostUserRegister;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewModelSingUp extends ViewModel {

    private MutableLiveData<ResponsePostUserRegister> registerResult = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<ResponsePostUserRegister> getRegisterResult() {
        return registerResult;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void registerUser(PostUserRegister user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.0.199.187")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest apiRequest = retrofit.create(ApiRequest.class);
        Call<ResponsePostUserRegister> call = apiRequest.registerUser(user);

        call.enqueue(new Callback<ResponsePostUserRegister>() {
            @Override
            public void onResponse(Call<ResponsePostUserRegister> call, Response<ResponsePostUserRegister> response) {
                if (response.isSuccessful()) {
                    registerResult.postValue(response.body());
                } else {
                    registerResult.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserRegister> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }
}
