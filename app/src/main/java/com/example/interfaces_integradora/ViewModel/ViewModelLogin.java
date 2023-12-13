package com.example.interfaces_integradora.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Repository.LoginRepository;
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
    private LoginRepository loginRepository = new LoginRepository();
    private MutableLiveData<ResponsePostUserLogin> loginresult = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public MutableLiveData<ResponsePostUserLogin> getLoginresult() {
        return loginresult;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void forgetPassword(String email){
        loginRepository.forgetPassword(email, new LoginRepository.ForgetPasswordCallback() {
            @Override
            public void onSuccess(ResponsePostUserForgetPassword data) {
                toastMessage.postValue(data.getMsg());
            }

            @Override
            public void onError(Throwable t) {
                toastMessage.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void loginUser(PostUserLogin postUserLogin) {
        loginRepository.loginUser(postUserLogin, new LoginRepository.LoginCallback() {
            @Override
            public void onSuccess(ResponsePostUserLogin data) {
                loginresult.postValue(data);
            }

            @Override
            public void onError(Throwable t) {
                toastMessage.postValue(t.getMessage());
            }
        });
    }
}
