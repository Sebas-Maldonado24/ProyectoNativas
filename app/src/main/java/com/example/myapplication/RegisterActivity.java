package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    Button camara;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ImageView foto;
    EditText editTextNombre, editTextCorreo, editTextCiudad, editTextDireccion, editTextIdentificacion, Textpassword;

    // Definimos el ActivityResultLauncher fuera de onCreate

    ActivityResultLauncher<Intent> camaraL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap img = (Bitmap) extras.get("data");
                        foto.setImageBitmap(img);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // inicializar las vistas
        camara = findViewById(R.id.Camarabutton);
        foto = findViewById(R.id.imagen);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextCiudad = findViewById(R.id.editTextCiudad);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextIdentificacion = findViewById(R.id.editTextIdentificacion);
        Textpassword=findViewById(R.id.Textpassword);
        Button btnRegistrar = findViewById(R.id.buttonRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editTextNombre.getText().toString();
                String correo = editTextCorreo.getText().toString();
                String ciudad = editTextCiudad.getText().toString();
                String direccion = editTextDireccion.getText().toString();
                String identificacion = editTextIdentificacion.getText().toString();
String password =Textpassword.getText().toString();
                // Llamada al método de inserción
                boolean isInserted = databaseHelper.insertUser(nombre, correo, ciudad, direccion, identificacion,password);

                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camaraL.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        // Configurar el botón "Atrás"
        Button atrasButton = findViewById(R.id.button_atras);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Regresar a la actividad principal
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finaliza la actividad actual
            }
        });

        Button btnVerUsuarios = findViewById(R.id.button_usuarios);
        btnVerUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });

        // Manejar los insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
