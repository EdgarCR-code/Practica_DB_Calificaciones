package com.example.practicadb_calificaciones;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class organizarAlumnos extends AppCompatActivity {

    private baseDatos db;
    private ListView listaAprobados, listaReprobados;
    private ArrayAdapter<String> adaptadorAprobados, adaptadorReprobados;
    private ArrayList<String> listaDeAprobados, listaDeReprobados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizar_alumnos);

        db = new baseDatos(this);
        listaAprobados = findViewById(R.id.listaAprobados);
        listaReprobados = findViewById(R.id.listaReprobados);
        Button botonVolverInicio = findViewById(R.id.botonVolverInicio);

        listaDeAprobados = new ArrayList<>();
        listaDeReprobados = new ArrayList<>();

        adaptadorAprobados = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeAprobados);
        adaptadorReprobados = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeReprobados);

        listaAprobados.setAdapter(adaptadorAprobados);
        listaReprobados.setAdapter(adaptadorReprobados);

        cargarAlumnos();

        botonVolverInicio.setOnClickListener(v -> {
            Intent intent = new Intent(organizarAlumnos.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void cargarAlumnos() {
        listaDeAprobados.clear();
        listaDeReprobados.clear();
        Cursor cursor = db.obtenerTodosLosAlumnos();
        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                double promedio = cursor.getDouble(cursor.getColumnIndex("promedio"));
                if (promedio >= 7) {
                    listaDeAprobados.add(nombre + " - Promedio: " + promedio);
                } else {
                    listaDeReprobados.add(nombre + " - Promedio: " + promedio);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        adaptadorAprobados.notifyDataSetChanged();
        adaptadorReprobados.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}

