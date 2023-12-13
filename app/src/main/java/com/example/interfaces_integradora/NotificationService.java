package com.example.interfaces_integradora;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserValuesPlant;
import com.example.interfaces_integradora.View.DetallePlanta;
import com.example.interfaces_integradora.ViewModel.ViewModelDetailPlant;
import java.util.List;

public class NotificationService extends Service{
    ViewModelDetailPlant viewModel;
    Handler handler;
    Runnable runnableCode;
    String token;
    private static final String CHANNEL_ID = "canal";
    private PendingIntent pendingIntent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Servicio en ejecución")
                .setSmallIcon(R.drawable.icon1)
                .build();

        // Inicia el servicio en primer plano
        startForeground(1, notification);

        if (intent != null) {
            token = intent.getStringExtra("token");
        }
        viewModel = new ViewModelDetailPlant();
        viewModel.getPlantData().observeForever(new Observer<List<ResponseGetUserValuesPlant.Data>>() {
            @Override
            public void onChanged(List<ResponseGetUserValuesPlant.Data> data) {
                for (ResponseGetUserValuesPlant.Data dataItem : data) {
                    String value = dataItem.getValue();
                    String message = null;
                    try {
                        switch (dataItem.getFeedkey()) {
                            case "humedad":
                                if (Integer.parseInt(value) >= 60) {
                                    message = "La humedad es mayor a 60%";
                                }
                                break;
                            case "lluvia":
                                if ("1".equals(value)) {
                                    message = "Esta lloviendo";
                                }
                                break;
                            case "suelo":
                                if (Integer.parseInt(value) <= 50) {
                                    message = "La humedad de la planta es menor a 50%";
                                }
                                break;
                            case "temperatura":
                                if (Integer.parseInt(value) >= 30) {
                                    message = "La temperatura es mayor a 30°";
                                }
                                break;
                            case "agua":
                                if (Integer.parseInt(value) <= 20) {
                                    message = "Se esta acabando el agua";
                                }
                                break;
                            case "luz":
                                if (Integer.parseInt(value) < 20) {
                                    message = "Hay demasiada luz";
                                }
                                break;
                            case "movimiento":
                                if ("1".equals(value)) {
                                    message = "Hay movimiento cerca de tu planta!";
                                }
                                break;
                        }
                    } catch (NumberFormatException e) {
                        // Ignora el error si 'value' no es un número
                    }
                    if (message != null) {
                        showNotification("Alerta de planta", message);
                    }
                }
            }
        });
        viewModel.loadPlantData(token);

        handler = new Handler();
        runnableCode = new Runnable() {
            @Override
            public void run() {
                viewModel.loadPlantData(token);
                handler.postDelayed(this, 60000);
            }
        };
        handler.post(runnableCode);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

            private void showNotification(String title, String content) {
                if (checkNotificationPermission()) {
                    createNotificationChannel();
                    showNewNotification(title, content);
                } else {
                    Toast.makeText(this, "Permiso de notificación no concedido", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean checkNotificationPermission() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
                }
                return true;
            }

            private void createNotificationChannel() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                            "NEW", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.createNotificationChannel(channel);
                }
            }

            private void showNewNotification(String title, String content) {
                setPendingIntent(DetallePlanta.class);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                        CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                managerCompat.notify(1, builder.build());
            }

            private void setPendingIntent(Class<?> cls){
                Intent intent = new Intent(this, cls);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(cls);
                stackBuilder.addNextIntent(intent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                } else {
                    pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
                }
            }
        }
