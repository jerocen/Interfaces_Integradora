package com.example.interfaces_integradora.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Repository.PerfilRepository;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserMe;

public class ViewModelPerfil extends ViewModel {
    private PerfilRepository perfilRepository = new PerfilRepository();
    private MutableLiveData<ResponseGetUserMe> userData = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<ResponseGetUserMe> getUserData() {
        return userData;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void obtenerDatosUser(String token) {
        perfilRepository.obtenerDatosUser(token, new PerfilRepository.UserCallback() {
            @Override
            public void onSuccess(ResponseGetUserMe data) {
                userData.postValue(data);
            }

            @Override
            public void onError(Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }
}
