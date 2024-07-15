package com.example.practicadb_calificaciones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class baseDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "alumnos.db";
    private static final int VERSION_BASE_DATOS = 1;

    private static final String TABLA_ALUMNOS = "alumnos";
    private static final String COLUMNA_ID = "id";
    private static final String COLUMNA_NOMBRE = "nombre";
    private static final String COLUMNA_CALIFICACION1 = "calificacion1";
    private static final String COLUMNA_CALIFICACION2 = "calificacion2";
    private static final String COLUMNA_CALIFICACION3 = "calificacion3";
    private static final String COLUMNA_PROMEDIO = "promedio";

    public baseDatos(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAR_TABLA = "CREATE TABLE " + TABLA_ALUMNOS + " (" +
                COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNA_NOMBRE + " TEXT, " +
                COLUMNA_CALIFICACION1 + " REAL, " +
                COLUMNA_CALIFICACION2 + " REAL, " +
                COLUMNA_CALIFICACION3 + " REAL, " +
                COLUMNA_PROMEDIO + " REAL)";
        db.execSQL(CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ALUMNOS);
        onCreate(db);
    }

    public boolean agregarAlumno(String nombre, double calificacion1, double calificacion2, double calificacion3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        double promedio = (calificacion1 + calificacion2 + calificacion3) / 3;
        valores.put(COLUMNA_NOMBRE, nombre);
        valores.put(COLUMNA_CALIFICACION1, calificacion1);
        valores.put(COLUMNA_CALIFICACION2, calificacion2);
        valores.put(COLUMNA_CALIFICACION3, calificacion3);
        valores.put(COLUMNA_PROMEDIO, promedio);

        long resultado = db.insert(TABLA_ALUMNOS, null, valores);
        db.close();
        return resultado != -1;
    }

    public Cursor obtenerTodosLosAlumnos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id as _id, nombre, promedio FROM " + TABLA_ALUMNOS, null);
    }

    public void eliminarAlumno(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_ALUMNOS, COLUMNA_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
