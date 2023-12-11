package com.example.interfaces_integradora.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Retrofit.ApiRequest;
import com.example.interfaces_integradora.Models.PostUserLogin;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserForgetPassword;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewModelLogin extends ViewModel {
    Peticiones Peticiones = new Peticiones();
    private MutableLiveData<ResponsePostUserLogin> loginresult = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(MutableLiveData<String> toastMessage) {
        this.toastMessage = toastMessage;
    }

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

    public void forgetPassword(String email){
        Call<ResponsePostUserForgetPassword> call = Peticiones.forgetPassword(email);

        call.enqueue(new Callback<ResponsePostUserForgetPassword>() {
            @Override
            public void onResponse(Call<ResponsePostUserForgetPassword> call, Response<ResponsePostUserForgetPassword> response) {
                if (response.isSuccessful()) {
                    toastMessage.setValue(response.body().getMsg());
                } else {
                    toastMessage.setValue("Error al enviar el correo");
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserForgetPassword> call, Throwable t) {
                toastMessage.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }


}
