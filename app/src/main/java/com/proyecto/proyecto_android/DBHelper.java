package com.proyecto.proyecto_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Favoritos.db";
    private static final int DB_SCHEME_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Favoritos(" +
                "Id TEXT PRIMARY KEY NOT NULL, " +
                "Nombre TEXT NOT NULL, " +
                "Artista TEXT NOT NULL, " +
                "Descripcion TEXT, " +
                "Direccion TEXT NOT NULL, " +
                "Ciudad TEXT NOT NULL, " +
                "Fecha TEXT NOT NULL, " +
                "Precio INTEGER NOT NULL, " +
                "urlImagen TEXT, " +
                "Latitud REAL, " +
                "Longitud REAL, " + // <-- Añade una coma
                "RutOrganizacion TEXT)"); // <-- NUEVA COLUMNA
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
