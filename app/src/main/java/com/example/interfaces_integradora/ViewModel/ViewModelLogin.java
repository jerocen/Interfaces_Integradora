package com.example.interfaces_integradora.ViewModel;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Retrofit.ApiRequest;
import com.example.interfaces_integradora.Retrofit.PostUserLogin;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserLogin;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserLogout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewModelLogin extends ViewModel {
    private MutableLiveData<ResponsePostUserLogin> loginresult = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MutableLiveData<ResponsePostUserLogin> getLoginresult() {
        return loginresult;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void loginUser(PostUserLogin user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.0.199.187")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest apiRequest = retrofit.create(ApiRequest.class);
        Call<ResponsePostUserLogin> call = apiRequest.loginUser(user);

        call.enqueue(new Callback<ResponsePostUserLogin>() {
            @Override
            public void onResponse(Call<ResponsePostUserLogin> call, Response<ResponsePostUserLogin> response) {
                if (response.isSuccessful()) {
                    loginresult.postValue(response.body());
                } else {
                    loginresult.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserLogin> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }

}
