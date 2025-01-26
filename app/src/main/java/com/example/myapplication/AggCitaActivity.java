package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AggCitaActivity extends AppCompatActivity {
    private static final String FILE_NAME = "citas.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_cita);

        Button btnSelectDate = findViewById(R.id.btnSelectDate);
        Button btnSelectTime = findViewById(R.id.btnSelectTime);

        // Obtener la fecha y hora actuales
        Calendar calendar = Calendar.getInstance();

        btnSelectDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Mostrar el DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AggCitaActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) ->
                            Toast.makeText(
                                    AggCitaActivity.this,
                                    "Fecha seleccionada: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear,
                                    Toast.LENGTH_SHORT
                            ).show(),
                    year,
                    month,
                    day
            );
            datePickerDialog.show();
        });

        btnSelectTime.setOnClickListener(v -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // Mostrar el TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AggCitaActivity.this,
                    (view, selectedHour, selectedMinute) ->
                            Toast.makeText(
                                    AggCitaActivity.this,
                                    "Hora seleccionada: " + selectedHour + ":" + selectedMinute,
                                    Toast.LENGTH_SHORT
                            ).show(),
                    hour,
                    minute,
                    true // true para formato de 24 horas
            );
            timePickerDialog.show();
        });
    }
    private void guardarCita(String titulo, String medico, String hora, boolean addToCalendar) {
        String cita = "Título: " + titulo + "\nMédico: " + medico + "\nHora: " + hora + "\nAgregar al calendario: " + addToCalendar + "\n\n";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(cita.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String leerCitas() {
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();

        try {
            fis = openFileInput(FILE_NAME);
            int ch;
            while ((ch = fis.read()) != -1) {
                sb.append((char) ch);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
