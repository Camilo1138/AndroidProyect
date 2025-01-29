package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AggActividadFisicaActivity extends AppCompatActivity {

    private EditText editTextDate, editTextDuration;
    private Spinner spinnerActivity, spinnerTime;
    private Button btnRegister;

    private Button bntmostrar;
    public static List<ActividadFisica> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_actividad_fisica);

        editTextDate = findViewById(R.id.editTextDate);
        editTextDuration = findViewById(R.id.editTextDuration);
        spinnerActivity = findViewById(R.id.spinnerActivity);
        spinnerTime = findViewById(R.id.spinnerTime);
        btnRegister = findViewById(R.id.btnRegister);
        bntmostrar= findViewById(R.id.btnmostrar);
        Button btnSalir = findViewById(R.id.btnSalir);

        // Configurar Spinners
        String[] activities = {"Caminar", "Trotar", "Correr", "Funcional", "Crossfit", "Entrenamiento de pesas", "Nadar"};
        ArrayAdapter<String> activityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activities);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(activityAdapter);

        String[] times = {"Mañana", "Tarde", "Noche"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);



        // Manejar el clic del botón de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = editTextDate.getText().toString();
                String activityType = spinnerActivity.getSelectedItem().toString();
                String timeOfDay = spinnerTime.getSelectedItem().toString();

                if (date.isEmpty() || editTextDuration.getText().toString().isEmpty()) {
                    Toast.makeText(AggActividadFisicaActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int duration = Integer.parseInt(editTextDuration.getText().toString());

                // Validar que no sea una fecha futura
                if (isFutureDate(date)) {
                    Toast.makeText(AggActividadFisicaActivity.this, "La fecha no puede ser futura", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear nueva actividad
                ActividadFisica newActivity = new ActividadFisica(date, activityType, duration, timeOfDay);

                // Agregar a la lista
                activityList.add(newActivity);

                // Guardar en archivo
                saveActivityToFile(newActivity);

                Toast.makeText(AggActividadFisicaActivity.this, "Actividad registrada", Toast.LENGTH_SHORT).show();
            }
        });
        bntmostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AggActividadFisicaActivity.this, ActividadFisicaActivity.class);
                startActivity(intent);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finaliza la actividad actual y regresa a la anterior
            }
        });
    }

    private void saveActivityToFile(ActividadFisica activity) {
        try {
            String filename = "activities.txt";
            FileOutputStream fos = openFileOutput(filename, Context.MODE_APPEND);
            PrintWriter writer = new PrintWriter(fos);

            // Escribir la actividad como una línea
            String line = activity.getDate() + "," + activity.getActivityType() + "," + activity.getDuration() + "," + activity.getTimeOfDay();
            writer.println(line);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isFutureDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date inputDate = sdf.parse(date);
            Date currentDate = new Date(); // Fecha actual
            return inputDate.after(currentDate); // Verifica si es futura
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Si hay un error de formato, se considera no futura
        }
    }
}
