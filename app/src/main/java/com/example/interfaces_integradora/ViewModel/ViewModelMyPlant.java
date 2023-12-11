package com.example.interfaces_integradora.ViewModel;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserPlant;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserMe;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserPlant;
import com.example.interfaces_integradora.View.MyPlants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelMyPlant extends ViewModel {
    private Peticiones peticiones = new Peticiones();
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
        Call<ResponsePostUserMe> call = peticiones.obtenerDatosUser(token);
        call.enqueue(new Callback<ResponsePostUserMe>() {
            @Override
            public void onResponse(Call<ResponsePostUserMe> call, Response<ResponsePostUserMe> response) {
                if (response.isSuccessful()) {
                    nombrePerfil.setValue(response.body().getName());
                    correo.setValue(response.body().getEmail());
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserMe> call, Throwable t) {

            }
        });
    }


    public void obtenerDatosPlant(String token) {
        Call<ResponseGetUserPlant> call = peticiones.obtenerDatosPlant(token);
        call.enqueue(new Callback<ResponseGetUserPlant>() {
            @Override
            public void onResponse(Call<ResponseGetUserPlant> call, Response<ResponseGetUserPlant> response) {
                if (response.isSuccessful()) {
                    itemPlants.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResponseGetUserPlant> call, Throwable t) {

            }
        });
    }

    public void agregarPlanta(String token, String nombrePlanta) {
        Call<ResponsePostUserPlant> call = peticiones.createplant(token, nombrePlanta);
        call.enqueue(new Callback<ResponsePostUserPlant>() {
            @Override
            public void onResponse(Call<ResponsePostUserPlant> call, Response<ResponsePostUserPlant> response) {
                if (response.isSuccessful()) {
                    obtenerDatosPlant(token);
                    toastMessage.setValue("Planta agregada exitosamente");

                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserPlant> call, Throwable t) {
                toastMessage.setValue("Error al agregar planta");
            }
        });
    }

}

