package com.example.practicadb_calificaciones;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class agregarAlumno extends AppCompatActivity {

    private baseDatos db;
    private EditText editTextNombre, editTextCalificacion1, editTextCalificacion2, editTextCalificacion3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);

        db = new baseDatos(this);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCalificacion1 = findViewById(R.id.editTextCalificacion1);
        editTextCalificacion2 = findViewById(R.id.editTextCalificacion2);
        editTextCalificacion3 = findViewById(R.id.editTextCalificacion3);
        Button botonGuardar = findViewById(R.id.botonGuardar);
        Button botonOrganizar = findViewById(R.id.botonOrganizar);

        botonGuardar.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString();
            double calificacion1 = Double.parseDouble(editTextCalificacion1.getText().toString());
            double calificacion2 = Double.parseDouble(editTextCalificacion2.getText().toString());
            double calificacion3 = Double.parseDouble(editTextCalificacion3.getText().toString());

            if (db.agregarAlumno(nombre, calificacion1, calificacion2, calificacion3)) {
                Toast.makeText(agregarAlumno.this, "Alumno agregado", Toast.LENGTH_SHORT).show();
                editTextNombre.setText("");
                editTextCalificacion1.setText("");
                editTextCalificacion2.setText("");
                editTextCalificacion3.setText("");
            } else {
                Toast.makeText(agregarAlumno.this, "No se pudo agregar el alumno", Toast.LENGTH_SHORT).show();
            }
        });

        botonOrganizar.setOnClickListener(v -> {
            Intent intent = new Intent(agregarAlumno.this, organizarAlumnos.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
