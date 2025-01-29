package com.example.myapplication;



import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InicioActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;


    private boolean isFabOpen = false;
    private TextView tvProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);
        FloatingActionsMenu floatingActionsMenu = findViewById(R.id.grupoFab);

        FloatingActionButton fabCitas = findViewById(R.id.idFabConfirmar);
        FloatingActionButton fabMedicina = findViewById(R.id.idFabActualizar);
        FloatingActionButton fabActividad = findViewById(R.id.Actividad);
        FloatingActionButton fabMedico = findViewById(R.id.medico);

        fabMedico.setTitle("Medicos");

        // Configura listeners para los botones
        fabCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad para "Citas"
                Intent intent = new Intent(InicioActivity.this, AggCitaActivity.class);
                startActivity(intent);
            }
        });
        fabActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad para "Citas"
                Intent intent = new Intent(InicioActivity.this, AggActividadFisicaActivity.class);
                startActivity(intent);
            }
        });

        fabMedicina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad para "Citas"
                Intent intent = new Intent(InicioActivity.this, AgregarMedicinaActivity.class);
                startActivity(intent);
            }
        });

        fabMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad para "Citas"
                Intent intent = new Intent(InicioActivity.this, ListadoMedicosActivity.class);
                startActivity(intent);
            }
        });


        initWidgets();
        // Asegúrate de que selectedDate está inicializado
        // Inicializa la fecha seleccionada
        if (CalendarUtils.selectedDate == null) {
            CalendarUtils.selectedDate = LocalDate.now();
        }

        setWeekView();
        // Vincula el TextView
        tvProfileName = findViewById(R.id.profile_name);

        // Recupera el nombre del perfil del Intent
        PerFil perfilData = (PerFil) getIntent().getSerializableExtra("perfil_data");
        if (perfilData != null) {
            // Muestra el nombre del perfil en el TextView
            tvProfileName.setText(perfilData.getNombre());
        }


    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView() {
        monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }

    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
        loadMedicinesFromFile();
        loadActivitiesFromFile();

    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        ArrayList<ActividadFisica> dailyActivities = getActivitiesForDate(CalendarUtils.selectedDate);
        ArrayList<String> dailyMedicines = getMedicinesForDate(CalendarUtils.selectedDate);

        ArrayList<String> dailyCitas = getCitasForDate(CalendarUtils.selectedDate); // Citas del día

        ArrayList<String> combinedList = new ArrayList<>();

        for (Event event : dailyEvents) {
            combinedList.add("Evento: " + event.getName() + " - " + event.getTime());
        }

        for (ActividadFisica activity : dailyActivities) {
            combinedList.add("Actividad: " + activity.getActivityType() + " - " + activity.getTimeOfDay() + " - " + activity.getDuration() + " mins");
        }

        for (String medicine : dailyMedicines) {
            combinedList.add("Medicina: " + medicine);
        }

        for (String cita : dailyCitas) {
            combinedList.add(cita);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, combinedList);
        eventListView.setAdapter(adapter);
    }



    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    private ArrayList<ActividadFisica> getActivitiesForDate(LocalDate date) {
        ArrayList<ActividadFisica> activitiesForDate = new ArrayList<>();
        for (ActividadFisica activity : AggActividadFisicaActivity.activityList) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date activityDate = sdf.parse(activity.getDate());
                LocalDate activityLocalDate = activityDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (activityLocalDate.equals(date)) {
                    activitiesForDate.add(activity);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return activitiesForDate;
    }

    private void loadActivitiesFromFile() {
        try {
            FileInputStream fis = openFileInput("activities.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] activityData = line.split(",");
                String date = activityData[0];
                String activityType = activityData[1];
                int duration = Integer.parseInt(activityData[2]);
                String timeOfDay = activityData[3];
                ActividadFisica activity = new ActividadFisica(date, activityType, duration, timeOfDay);
                if (!AggActividadFisicaActivity.activityList.contains(activity)) {
                    AggActividadFisicaActivity.activityList.add(activity);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadMedicinesFromFile() {
        try {
            FileInputStream fis = openFileInput("medicines_data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                // Verifica si la medicina corresponde a la fecha actual
                String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
                if (line.startsWith(currentDate) && !AgregarMedicinaActivity.medicines.contains(line)) {
                    AgregarMedicinaActivity.medicines.add(line); // Solo agregar si no está ya en la lista
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<String> getMedicinesForDate(LocalDate date) {
        ArrayList<String> medicinesForDate = new ArrayList<>();
        String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        for (String medicine : AgregarMedicinaActivity.medicines) {
            if (medicine.startsWith(selectedDate)) {
                medicinesForDate.add(medicine);
            }
        }

        return medicinesForDate;
    }

    private ArrayList<String> getCitasForDate(LocalDate date) {
        ArrayList<String> citasForDate = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try (FileInputStream fis = openFileInput(AggCitaActivity.FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            StringBuilder cita = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    String[] citaData = cita.toString().split("\n");
                    String fechaCita = citaData[2].split(": ")[1]; // Fecha está en la línea 3 (índice 2)
                    Date citaDate = sdf.parse(fechaCita);
                    LocalDate citaLocalDate = citaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (citaLocalDate.equals(date)) {
                        citasForDate.add("Cita: " + citaData[0].split(": ")[1] + " con " + citaData[1].split(": ")[1]);
                    }
                    cita.setLength(0); // Reinicia para la próxima cita
                } else {
                    cita.append(line).append("\n");
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return citasForDate;
    }



}
