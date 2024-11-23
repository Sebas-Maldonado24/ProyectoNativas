package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class Pagos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

        Button finalizarPagoButton = findViewById(R.id.buttonFinalizarPago);
        finalizarPagoButton.setOnClickListener(view -> {
            // Aquí simplemente se muestra un mensaje
            Toast.makeText(Pagos.this, "Pago procesado (simulado)", Toast.LENGTH_SHORT).show();


        });
    }
}