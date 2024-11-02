package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "Usuarios";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "nombre";
    public static final String COLUMN_EMAIL = "correo";
    public static final String COLUMN_CITY = "ciudad";
    public static final String COLUMN_ADDRESS = "direccion";
    public static final String COLUMN_IDENTIFICATION = "identificacion";
    public static final String COLUMN_PASSWORD = "password";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_IDENTIFICATION + " TEXT, "+
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }
    // Método para insertar un usuario
    public boolean insertUser(String nombre, String correo, String ciudad, String direccion, String identificacion, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, nombre);
        contentValues.put(COLUMN_EMAIL, correo);
        contentValues.put(COLUMN_CITY, ciudad);
        contentValues.put(COLUMN_ADDRESS, direccion);
        contentValues.put(COLUMN_IDENTIFICATION, identificacion);
        contentValues.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();
        return result != -1;  // Devuelve true si la inserción fue exitosa
    }

    // Método para eliminar un usuario
    public boolean deleteUser(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USERS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Método para actualizar un usuario
    public boolean updateUser(long id, String nombre, String correo, String ciudad, String direccion, String identificacion,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, nombre);
        contentValues.put(COLUMN_EMAIL, correo);
        contentValues.put(COLUMN_CITY, ciudad);
        contentValues.put(COLUMN_ADDRESS, direccion);
        contentValues.put(COLUMN_IDENTIFICATION, identificacion);
        contentValues.put(COLUMN_PASSWORD, password);

        int result = db.update(TABLE_USERS, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
    public Cursor getUserById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
