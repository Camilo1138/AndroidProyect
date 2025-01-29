package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AggCitaActivity extends AppCompatActivity {
    public static final String FILE_NAME = "citas.txt";
    private Spinner spinnerMedicos;
    private String fechaSeleccionada;
    private String horaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_cita);

        Button btnSelectDate = findViewById(R.id.btnSelectDate);
        Button btnSelectTime = findViewById(R.id.btnSelectTime);
        spinnerMedicos = findViewById(R.id.spinnerMedicos);
        EditText etTitulo = findViewById(R.id.etTitulo);
        Button btnlista_citas = findViewById(R.id.btnlista_citas);
        Button btnGuardar= findViewById(R.id.btnGuardar);
        Button btnSalir = findViewById(R.id.btnSalir);

        // Cargar médicos en el Spinner
        cargarMedicosEnSpinner();

        // Obtener la fecha y hora actuales
        Calendar calendar = Calendar.getInstance();

        btnSelectDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AggCitaActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        fechaSeleccionada = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        Toast.makeText(AggCitaActivity.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
                    },
                    year,
                    month,
                    day
            );
            datePickerDialog.show();
        });

        btnSalir.setOnClickListener(v -> {
            finish(); // Finaliza esta actividad y regresa a la actividad anterior
        });

        btnSelectTime.setOnClickListener(v -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AggCitaActivity.this,
                    (view, selectedHour, selectedMinute) -> {
                        horaSeleccionada = selectedHour + ":" + selectedMinute;
                        Toast.makeText(AggCitaActivity.this, "Hora seleccionada: " + horaSeleccionada, Toast.LENGTH_SHORT).show();

                    },
                    hour,
                    minute,
                    true
            );
            timePickerDialog.show();
        });

        btnGuardar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString();
            String medico = spinnerMedicos.getSelectedItem().toString();
            guardarCita(titulo, medico);
        });
        btnlista_citas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AggCitaActivity.this, CitasActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cargarMedicosEnSpinner() {
        // Cargar la lista de médicos desde el archivo
        ArrayList<Medico> listaMedicos = Medico.cargarMedicosDesdeArchivo(getArchivoMedicosPath());

        if (listaMedicos.isEmpty()) {
            // Mostrar Toast si no hay médicos registrados
            spinnerMedicos.setOnTouchListener((v, event) -> {
                Toast.makeText(this, "No hay médicos registrados", Toast.LENGTH_SHORT).show();
                return true; // Evitar mostrar el Spinner vacío
            });
        } else {
            // Crear un ArrayAdapter con los nombres de los médicos
            ArrayList<String> nombresMedicos = new ArrayList<>();
            for (Medico medico : listaMedicos) {
                nombresMedicos.add(medico.getNombre());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    nombresMedicos
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMedicos.setAdapter(adapter);
        }
    }
    private String getArchivoMedicosPath() {
        return new File(getFilesDir(), "medicos.dat").getPath();
    }

    private void guardarCita(String titulo, String medico) {
        // Validar datos vacíos
        if (titulo.isEmpty() || medico.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fechaSeleccionada == null || horaSeleccionada == null) {
            Toast.makeText(this, "Por favor, seleccione una fecha y una hora.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear la cita
        String cita = "titulo: " + titulo + "\nmedico: " + medico + "\nFecha: " + fechaSeleccionada + "\nHora: " + horaSeleccionada + "\n\n";

        // Leer citas existentes
        ArrayList<String> citasExistentes = leerCitasDesdeArchivo();
        String citaNormalizada = cita.trim();

        for (String citaExistente : citasExistentes) {
            if (citaExistente.trim().equals(citaNormalizada)) {
                Toast.makeText(this, "Esta cita ya existe.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Guardar la cita en el archivo
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND)) {
            fos.write(cita.getBytes());
            fos.flush();
            Toast.makeText(this, "Cita guardada correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar la cita: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private ArrayList<String> leerCitasDesdeArchivo() {
        ArrayList<String> citas = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            int character;
            while ((character = fis.read()) != -1) {
                sb.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] citasArray = sb.toString().split("\n\n");
        Collections.addAll(citas, citasArray);

        return citas;
    }

    // Método para leer todas las citas desde el archivo
    private ArrayList<Cita> leerCitasComoObjetos() {
        ArrayList<Cita> citas = new ArrayList<>();
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
        for (String citaStr : citasArray) {
            String[] campos = citaStr.split("\n");
            String titulo = campos[0].split(": ")[1];
            String medico = campos[1].split(": ")[1];
            String fecha = campos[2].split(": ")[1];
            String hora = campos[3].split(": ")[1];

            citas.add(new Cita(titulo, medico, fecha, hora));
        }

        return citas;
    }


}
