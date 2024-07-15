package com.example.practicadb_calificaciones;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private baseDatos db;
    private ListView listaAlumnos;
    private SimpleCursorAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new baseDatos(this);
        listaAlumnos = findViewById(R.id.listaAlumnos);
        Button botonAgregar = findViewById(R.id.botonAgregar);

        cargarAlumnos();

        botonAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, agregarAlumno.class);
            startActivity(intent);
        });

        listaAlumnos.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Eliminar Alumno")
                    .setMessage("¿Está seguro que desea eliminar a este alumno?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        db.eliminarAlumno((int) id);
                        cargarAlumnos();
                        Toast.makeText(MainActivity.this, "Alumno eliminado", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private void cargarAlumnos() {
        Cursor cursor = db.obtenerTodosLosAlumnos();
        String[] from = new String[]{"nombre", "promedio"};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};
        adaptador = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        listaAlumnos.setAdapter(adaptador);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
