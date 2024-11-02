package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserListActivity extends AppCompatActivity {
    ListView userListView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userListView = findViewById(R.id.userListView);
        databaseHelper = new DatabaseHelper(this);

        loadUserList();
    }

    private void loadUserList() {
        Cursor cursor = databaseHelper.getAllUsers();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay usuarios disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] from = { DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_CITY, DatabaseHelper.COLUMN_ADDRESS, DatabaseHelper.COLUMN_IDENTIFICATION,  DatabaseHelper.COLUMN_PASSWORD};
        int[] to = { R.id.userInfo, R.id.usercorreo,
                R.id.userCiudad };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.user_list_item,
                cursor,
                from,
                to,
                0
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView btnUpdate = view.findViewById(R.id.btnUpdate);
                ImageView btnDelete = view.findViewById(R.id.btnDelete);
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));

                btnUpdate.setOnClickListener(v -> {
                    Log.d("UserListActivity", "Botón actualizar clickeado para ID: " + id);
                    Intent intent = new Intent(UserListActivity.this, UpdateUserActivity.class);
                    intent.putExtra("USER_ID", id);
                    startActivity(intent);
                });

                btnDelete.setOnClickListener(v -> {
                    Log.d("UserListActivity", "Botón eliminar clickeado para ID: " + id);
                    databaseHelper.deleteUser(id);
                    Toast.makeText(UserListActivity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    loadUserList(); // Refresca la lista después de eliminar
                });

                return view;
            }
        };

        userListView.setAdapter(adapter);
    }


}
