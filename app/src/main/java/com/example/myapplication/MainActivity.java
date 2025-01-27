package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private EditText etMedicineName, etQuantity, etDosage;
    private Spinner spinnerPresentation;
    private TimePicker tpTime;
    private ListView lvMedicines;
    private ArrayList<String> medicineList;
    private ArrayAdapter<String> adapter;
    private static final String FILE_NAME = "medicines_data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medicina);

        // Inicialización de los elementos de la interfaz
        etMedicineName = findViewById(R.id.et_medicine_name);
        etQuantity = findViewById(R.id.et_quantity);
        etDosage = findViewById(R.id.et_dosage);
        spinnerPresentation = findViewById(R.id.spinner_presentation);
        tpTime = findViewById(R.id.tp_time);
        lvMedicines = findViewById(R.id.lv_medicines);

        // Inicialización del ArrayList y el ArrayAdapter
        medicineList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicineList);
        lvMedicines.setAdapter(adapter);

        // Cargar los datos guardados en el archivo
        loadMedicinesFromFile();

        // Configurar el Spinner con el arreglo de presentaciones
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.presentations, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPresentation.setAdapter(spinnerAdapter);

        // Botón para añadir medicina
        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etMedicineName.getText().toString();
                String quantityText = etQuantity.getText().toString();
                String dosageText = etDosage.getText().toString();

                if (nombre.isEmpty() || quantityText.isEmpty() || dosageText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int cantidad;
                double dosis;

                try {
                    cantidad = Integer.parseInt(quantityText);
                    dosis = Double.parseDouble(dosageText);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Por favor ingrese números válidos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String presentacion = spinnerPresentation.getSelectedItem().toString();
                String hora = String.format("%02d:%02d", tpTime.getCurrentHour(), tpTime.getCurrentMinute());

                // Crear la representación de la medicina como una cadena
                String medicina = nombre + "," + cantidad + "," + presentacion + "," + dosis + "," + hora;
                medicineList.add(medicina);
                adapter.notifyDataSetChanged();

                // Guardar los datos en archivo
                saveMedicinesToFile();
                Toast.makeText(MainActivity.this, "Medicina añadida", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveMedicinesToFile() {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            for (String medicina : medicineList) {
                osw.write(medicina + "\n");
            }
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMedicinesFromFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                medicineList.add(line);
            }
            reader.close();
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}
