package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CrearPerfilActivity extends AppCompatActivity {
    private EditText etNombre, etRelacion, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil);

        etNombre = findViewById(R.id.etNombre);
        etRelacion = findViewById(R.id.etrelacion);
        etEmail = findViewById(R.id.etEmail);
    }

    public void onGuardarPerfilClick(View view) {
        String nombre = etNombre.getText().toString();
        String relacion = etRelacion.getText().toString();
        String email = etEmail.getText().toString();

        if (nombre.isEmpty() || relacion.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
            return;
        }

        Perfil perfil = new Perfil(nombre, relacion, email);
        PerfilManager.guardarPerfil(this, perfil);
        Toast.makeText(this, "Perfil guardado exitosamente.", Toast.LENGTH_SHORT).show();

        // Limpiar los campos de entrada
        etNombre.setText("");
        etRelacion.setText("");
        etEmail.setText("");

        // Redirigir a la actividad anterior
        Intent intent = new Intent(this, SeleccionarPerfilActivity.class);
        startActivity(intent);
        finish();
    }

    public void onCerrarClick(View view) {
        // Redirigir a la actividad anterior
        Intent intent = new Intent(this, SeleccionarPerfilActivity.class);
        startActivity(intent);
        finish();
    }
}