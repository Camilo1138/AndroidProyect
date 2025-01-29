package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActividadFisicaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewActivities;
    private ActFisicaAdapter activityAdapter;
    private List<ActividadFisica> activityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_fisica);

        recyclerViewActivities = findViewById(R.id.recyclerViewActivities);

        // Cargar actividades desde el archivo
        activityList = loadActivitiesFromFile();

        // Ordenar de más reciente a más antigua
        Collections.reverse(activityList);
        Button btnRegresar = findViewById(R.id.btnRegresar);

        // Configurar el RecyclerView
        activityAdapter = new ActFisicaAdapter(activityList);
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewActivities.setAdapter(activityAdapter);

        btnRegresar.setOnClickListener(v -> {
            finish();
        });


    }

    List<ActividadFisica> loadActivitiesFromFile() {
        List<ActividadFisica> activities = new ArrayList<>();

        try {
            String filename = "activities.txt";
            FileInputStream fis = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String date = parts[0];
                    String activityType = parts[1];
                    int duration = Integer.parseInt(parts[2]);
                    String timeOfDay = parts[3];

                    activities.add(new ActividadFisica(date, activityType, duration, timeOfDay));

                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return activities;


    }




}
