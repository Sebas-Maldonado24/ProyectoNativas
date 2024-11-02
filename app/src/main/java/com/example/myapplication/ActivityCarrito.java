package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivitycarritoBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class ActivityCarrito extends AppCompatActivity {

    public ActivitycarritoBinding binding;

    private static final int CODIGO_PERMISO_UBICACION = 100;
    private boolean isPermisos = false;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitycarritoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar el botón "Atrás"
        Button atrasButton = findViewById(R.id.button_atras2);
        atrasButton.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityCarrito.this, ProductListActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual
        });

        Button mapaButton = findViewById(R.id.mapa);
        mapaButton.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityCarrito.this, Mapa.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Solicitud de permisos
        solicitarPermisosUbicacion();
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void solicitarPermisosUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isPermisos = true;
            iniciarLocalizacion();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                mostrarDialogoExplicativo();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        },
                        CODIGO_PERMISO_UBICACION);
            }
        }
    }

    private void mostrarDialogoExplicativo() {
        new AlertDialog.Builder(this)
                .setTitle("Permisos necesarios")
                .setMessage("Esta aplicación requiere acceder a tu ubicación para mostrar tus coordenadas. ¿Deseas permitir el acceso?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    ActivityCompat.requestPermissions(this,
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                            },
                            CODIGO_PERMISO_UBICACION);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                    mostrarMensajePermisosDenegados();
                })
                .create()
                .show();
    }

    private void mostrarMensajePermisosDenegados() {
        Toast.makeText(this, "La aplicación necesita permisos de ubicación para funcionar", Toast.LENGTH_LONG).show();
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void iniciarLocalizacion() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30000); // Intervalo de 30 segundos
            locationRequest.setFastestInterval(5000); // 5 segundos como el intervalo más rápido

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        actualizarUbicacion(locationResult.getLastLocation());
                    }
                }
            };

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        } catch (SecurityException e) {
            Log.e("Location", "Error de permisos: " + e.getMessage());
            Toast.makeText(this, "Error: Permisos no disponibles", Toast.LENGTH_LONG).show();
        }
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void actualizarUbicacion(android.location.Location location) {
        binding.tvLatitud.setText(String.format("%.6f", location.getLatitude()));
        binding.tvLongitud.setText(String.format("%.6f", location.getLongitude()));
        Log.d("Location", "Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude());
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_PERMISO_UBICACION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isPermisos = true;
                iniciarLocalizacion();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    mostrarDialogoExplicativo();
                } else {
                    mostrarDialogoConfiguracion();
                }
            }
        }
    }

    private void mostrarDialogoConfiguracion() {
        new AlertDialog.Builder(this)
                .setTitle("Permisos requeridos")
                .setMessage("Es necesario habilitar los permisos desde la configuración de la aplicación para poder funcionar correctamente.")
                .setPositiveButton("Ir a Configuración", (dialog, which) -> abrirConfiguracionAplicacion())
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                    mostrarMensajePermisosDenegados();
                })
                .create()
                .show();
    }

    private void abrirConfiguracionAplicacion() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(android.net.Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationCallback != null && fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}
