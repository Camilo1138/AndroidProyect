package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // Recibe la lista de actividades desde otra actividad (puedes pasarla como un Singleton o estática)
        activityList = AggActividadFisicaActivity.activityList; // Asegúrate de que sea accesible.

        // Ordenar de más reciente a más antigua
        Collections.reverse(activityList);

        // Configurar el RecyclerView
        activityAdapter = new ActFisicaAdapter(activityList);
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewActivities.setAdapter(activityAdapter);
    }
}
//3