package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class AgregarMedicoActivity extends AppCompatActivity {

    private EditText etNombre, etEspecialidad, etTelefono, etEmail, etDireccion;
    private Button btnGuardar, btnCancelar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medico);

        etNombre = findViewById(R.id.etNombre);
        etEspecialidad = findViewById(R.id.etEspecialidad);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        etDireccion = findViewById(R.id.etDireccion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnGuardar.setOnClickListener(v -> guardarMedico());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void guardarMedico() {
        String nombre = etNombre.getText().toString().trim();
        String especialidad = etEspecialidad.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();

        if (nombre.isEmpty() || especialidad.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear nuevo médico
        Medico nuevoMedico = new Medico(nombre, especialidad, telefono, email, direccion);

        // Cargar la lista de médicos desde el archivo
        ArrayList<Medico> listaMedicos = Medico.cargarMedicosDesdeArchivo(getArchivoMedicosPath());

        // Agregar el nuevo médico
        listaMedicos.add(nuevoMedico);

        // Guardar la lista actualizada en el archivo
        Medico.guardarMedicosEnArchivo(getArchivoMedicosPath(), listaMedicos);

        Toast.makeText(this, "Médico guardado exitosamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getArchivoMedicosPath() {
        return new File(getFilesDir(), "medicos.dat").getPath();
    }
}
