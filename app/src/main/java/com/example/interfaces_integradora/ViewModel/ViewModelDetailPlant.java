package com.example.interfaces_integradora.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Repository.PlantRepository;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserValuesPlant;

import java.util.List;

public class ViewModelDetailPlant extends ViewModel {
    private PlantRepository repository;
    private MutableLiveData<List<ResponseGetUserValuesPlant.Data>> plantData;
    private MutableLiveData<String> Message;
    public ViewModelDetailPlant() {
        repository = new PlantRepository();
        plantData = new MutableLiveData<>();
        Message = new MutableLiveData<>();
    }
    public LiveData<String> getMessage() {
        return Message;
    }
    public LiveData<List<ResponseGetUserValuesPlant.Data>> getPlantData() {
        return plantData;
    }

    public void loadPlantData(String token) {
        repository.getPlantData(token, new PlantRepository.DataCallback() {
            @Override
            public void onSuccess(List<ResponseGetUserValuesPlant.Data> data) {
                plantData.setValue(data);
            }

            @Override
            public void onError(Throwable t) {
                // Maneja el error
            }
        });
    }

    public void sendButton(String token) {
        repository.sendButton(token, new PlantRepository.ButtonCallback() {
            @Override
            public void onSuccess(String message) {
                Message.setValue(message);
            }

            @Override
            public void onError(Throwable t) {
                Message.setValue("Error");
            }
        });
    }
}
