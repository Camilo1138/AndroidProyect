package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AgregarMedicoActivity;
import com.example.myapplication.GlobalMedicos;
import com.example.myapplication.Medico;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ListadoMedicosActivity extends AppCompatActivity {

    private ListView listViewMedicos;
    private Button btnAgregarMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_medicos);

        listViewMedicos = findViewById(R.id.listViewMedicos);
        btnAgregarMedico = findViewById(R.id.btnAgregarMedico);

        // Mostrar la lista de médicos
        actualizarLista();

        // Abrir la actividad para agregar un médico
        btnAgregarMedico.setOnClickListener(v -> {
            Intent intent = new Intent(this, AgregarMedicoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista(); // Actualiza la lista cuando regresas a esta actividad
    }

    private void actualizarLista() {
        ArrayList<String> medicos = new ArrayList<>();
        for (Medico medico : GlobalMedicos.listaMedicos) {
            medicos.add(medico.getNombre() + " - " + medico.getEspecialidad() + "\nTel: " + medico.getTelefono());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicos);
        listViewMedicos.setAdapter(adapter);
    }
}
