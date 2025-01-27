package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
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
        ArrayList<Medico> listaMedicos = Medico.cargarMedicosDesdeArchivo(getArchivoMedicosPath());
        ArrayList<String> medicosInfo = new ArrayList<>();

        for (Medico medico : listaMedicos) {
            medicosInfo.add(
                    "Nombre: " + medico.getNombre() +
                            "\nEspecialidad: " + medico.getEspecialidad() +
                            "\nTeléfono: " + medico.getTelefono() +
                            "\nEmail: " + medico.getEmail() +
                            "\nDirección: " + medico.getDireccion()
            );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicosInfo);
        listViewMedicos.setAdapter(adapter);
    }

    private String getArchivoMedicosPath() {
        return new File(getFilesDir(), "medicos.dat").getPath();
    }
}
