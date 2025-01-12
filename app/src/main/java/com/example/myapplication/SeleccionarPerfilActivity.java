package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeleccionarPerfilActivity extends AppCompatActivity {

    private RecyclerView rvPerfiles;
    private PerfilAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_perfil);

        rvPerfiles = findViewById(R.id.rvPerfiles);
        rvPerfiles.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarPerfiles();
    }

    private void cargarPerfiles() {
        List<Perfil> perfiles = PerfilManager.cargarPerfiles(this);
        Log.d("SeleccionarPerfil", "Número de perfiles cargados: " + perfiles.size());

        if (perfiles.isEmpty()) {
            Log.d("SeleccionarPerfil", "No se encontraron perfiles.");
        }

        adapter = new PerfilAdapter(perfiles, this);
        rvPerfiles.setAdapter(adapter);
    }
}