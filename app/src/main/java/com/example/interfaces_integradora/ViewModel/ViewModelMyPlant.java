package com.example.interfaces_integradora.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Repository.MyPlantRepository;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserPlant;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserMe;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserPlant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelMyPlant extends ViewModel {
    private MyPlantRepository myPlantRepository = new MyPlantRepository();
    private MutableLiveData<String> nombrePerfil = new MutableLiveData<>();
    private MutableLiveData<String> correo = new MutableLiveData<>();
    private MutableLiveData<List<ResponseGetUserPlant.data>> itemPlants = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<String> getNombrePerfil() {
        return nombrePerfil;
    }

    public LiveData<String> getCorreo() {
        return correo;
    }

    public MutableLiveData<List<ResponseGetUserPlant.data>> getItemPlants() {
        return itemPlants;
    }

    public void obtenerDatosUser(String token) {
        myPlantRepository.obtenerDatosUser(token, new MyPlantRepository.UserCallback() {
            @Override
            public void onSuccess(ResponseGetUserMe data) {
                nombrePerfil.setValue(data.getName());
                correo.setValue(data.getEmail());
            }

            @Override
            public void onError(Throwable t) {
                // Manejar error
            }
        });
    }

    public void obtenerDatosPlant(String token) {
        myPlantRepository.obtenerDatosPlant(token, new MyPlantRepository.PlantCallback() {
            @Override
            public void onSuccess(ResponseGetUserPlant data) {
                itemPlants.setValue(data.getData());
            }

            @Override
            public void onError(Throwable t) {
                // Manejar error
            }
        });
    }

    public void agregarPlanta(String token, String nombrePlanta) {
        myPlantRepository.agregarPlanta(token, nombrePlanta, new MyPlantRepository.AddPlantCallback() {
            @Override
            public void onSuccess(ResponsePostUserPlant data) {
                obtenerDatosPlant(token);
                toastMessage.setValue("Planta agregada exitosamente");
            }

            @Override
            public void onError(Throwable t) {
                toastMessage.setValue("Error al agregar planta");
            }
        });
    }
}