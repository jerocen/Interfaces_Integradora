package com.example.interfaces_integradora.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Models.PostUserRegister;
import com.example.interfaces_integradora.Repository.SignUpRepository;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserRegister;

public class ViewModelSingUp extends ViewModel {

    private SignUpRepository signUpRepository = new SignUpRepository();
    private MutableLiveData<ResponsePostUserRegister> registerResult = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<ResponsePostUserRegister.ErrorResponse> errorResponse = new MutableLiveData<>();

    public LiveData<ResponsePostUserRegister.ErrorResponse> getErrorResponse() {
        return errorResponse;
    }

    public LiveData<ResponsePostUserRegister> getRegisterResult() {
        return registerResult;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void registerUser(PostUserRegister user) {
        signUpRepository.registerUser(user, new SignUpRepository.RegisterCallback() {
            @Override
            public void onSuccess(ResponsePostUserRegister data) {
                registerResult.postValue(data);
            }

            @Override
            public void onErrorResp(ResponsePostUserRegister.ErrorResponse errorResp) {
                errorResponse.postValue(errorResp);
            }

            @Override
            public void onError(Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }
}
