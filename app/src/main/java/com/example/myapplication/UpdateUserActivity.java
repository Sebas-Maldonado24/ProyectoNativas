package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUserActivity extends AppCompatActivity {
    private EditText editName, editEmail, editCity, editAddress, editIdentification,editPassword;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Inicializa los elementos
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editCity = findViewById(R.id.editCity);
        editAddress = findViewById(R.id.editAddress);
        editIdentification = findViewById(R.id.editIdentification);
        btnSave = findViewById(R.id.btnSave);
        editPassword=findViewById(R.id.editPassword);
        databaseHelper = new DatabaseHelper(this);

        // Obtén el ID del usuario desde el Intent
        userId = getIntent().getLongExtra("USER_ID", -1);

        // Carga los datos del usuario
        loadUserData();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    @SuppressLint("Range")
    private void loadUserData() {
        Cursor cursor = databaseHelper.getUserById(userId);
        if (cursor != null && cursor.moveToFirst()) {
            editName.setText(cursor.getString(cursor.getColumnIndex("nombre")));
            editEmail.setText(cursor.getString(cursor.getColumnIndex("correo")));
            editCity.setText(cursor.getString(cursor.getColumnIndex("ciudad")));
            editAddress.setText(cursor.getString(cursor.getColumnIndex("direccion")));
            editIdentification.setText(cursor.getString(cursor.getColumnIndex("identificacion")));
            editPassword.setText(cursor.getString(cursor.getColumnIndex("password")));
            cursor.close();
        }
    }

    private void saveUserData() {
        String nombre = editName.getText().toString();
        String correo = editEmail.getText().toString();
        String ciudad = editCity.getText().toString();
        String direccion = editAddress.getText().toString();
        String identificacion = editIdentification.getText().toString();
String password =editPassword.getText().toString();
        boolean updated = databaseHelper.updateUser(userId, nombre, correo, ciudad, direccion, identificacion,password);

        if (updated) {
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
            // Regresa a la lista de usuarios
            Intent intent = new Intent(UpdateUserActivity.this, UserListActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }
}
