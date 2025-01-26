package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarMedicoActivity extends AppCompatActivity {

    private EditText etNombre, etEspecialidad, etTelefono;
    private Button btnGuardar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medico);

        etNombre = findViewById(R.id.etNombre);
        etEspecialidad = findViewById(R.id.etEspecialidad);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnGuardar.setOnClickListener(v -> guardarMedico());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void guardarMedico() {
        String nombre = etNombre.getText().toString().trim();
        String especialidad = etEspecialidad.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        if (nombre.isEmpty() || especialidad.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Medico nuevoMedico = new Medico(nombre, especialidad, telefono);
        GlobalMedicos.listaMedicos.add(nuevoMedico);

        Toast.makeText(this, "Médico guardado exitosamente", Toast.LENGTH_SHORT).show();
        finish();
    }
}
