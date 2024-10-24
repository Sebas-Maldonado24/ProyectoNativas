package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Encuentra el botón "INGRESAR" por su ID
        Button ingresarButton = findViewById(R.id.buttoningresar); // ID del botón INGRESAR
        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la actividad de listado de productos
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                startActivity(intent);// Inicia la actividad;
            }
        });

        // Encuentra el botón "REGISTRARSE" por su ID
        Button registrarseButton = findViewById(R.id.button_registrarse); // ID del botón REGISTRARSE
        registrarseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la actividad de registro de clientes
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent); // Inicia la actividad
            }
        });
    }
}
