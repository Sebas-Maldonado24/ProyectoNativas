package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Button buttonAtras = findViewById(R.id.button_atras);
        Button buttoncarrito = findViewById(R.id.button_carrito);

        // Lógica para el botón "Atrás"
        buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Lógica para el botón "Carrito"
        buttoncarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el intent para abrir ActivityCarrito
                Intent intent = new Intent(ProductListActivity.this, ActivityCarrito.class);
                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });

        // Configurar botones de productos
        setupButtons();
    }

    //Método para configurar todos los botones de productos
    private void setupButtons() {
        setupProductButtons(R.id.button_detalles_silla, R.id.button_comparar_silla);
        setupProductButtons(R.id.button_detalles_mesa, R.id.button_comparar_mesa);
        setupProductButtons(R.id.button_detalles_lampa, R.id.button_comparar_lampa);
        setupProductButtons(R.id.button_detalles_clo, R.id.button_comparar_clo);

    }

    // Método para configurar los listeners de los botones de detalles y comparar
    private void setupProductButtons(int detallesButtonId, int compararButtonId) {
        Button detallesButton = findViewById(detallesButtonId);
        detallesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar un mensaje indicando que se van a mostrar los detalles del producto
                Toast.makeText(ProductListActivity.this, "Detalles del producto", Toast.LENGTH_SHORT).show();
            }
        });

        Button compararButton = findViewById(compararButtonId);
        compararButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar un mensaje indicando que se va a comparar el producto
                Toast.makeText(ProductListActivity.this, "Comprar productos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
