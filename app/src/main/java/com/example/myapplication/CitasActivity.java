package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CitasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CitaAdapter citasAdapter;
    private ArrayList<String> listaCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> {
            finish();
        });

        recyclerView = findViewById(R.id.recyclerViewcitas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listaCitas = new ArrayList<>();
        leerCitasDesdeArchivo();
    }

    private void leerCitasDesdeArchivo() {
        StringBuilder sb = new StringBuilder();

        try (FileInputStream fis = openFileInput(AggCitaActivity.FILE_NAME)) {
            int character;
            while ((character = fis.read()) != -1) {
                sb.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] citasArray = sb.toString().split("\n\n");
        Collections.addAll(listaCitas, citasArray);

        // Ordenar citas
        Collections.sort(listaCitas, (c1, c2) -> {
            String fecha1 = c1.split("\n")[2].split(": ")[1];
            String fecha2 = c2.split("\n")[2].split(": ")[1];
            return fecha1.compareTo(fecha2);
        });

        citasAdapter = new CitaAdapter(listaCitas);
        recyclerView.setAdapter(citasAdapter);
    }
    private void filtrarCitasPorFecha(String fechaFiltrar) {
        ArrayList<String> citasFiltradas = new ArrayList<>();

        for (String cita : listaCitas) {
            if (cita.contains("Fecha: " + fechaFiltrar)) {
                citasFiltradas.add(cita);
            }
        }

        // Actualizar el adaptador con las citas filtradas
        citasAdapter = new CitaAdapter(citasFiltradas);
        recyclerView.setAdapter(citasAdapter);
    }

}
