package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.*;
        import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
        import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AgregarMedicinaActivity extends AppCompatActivity {

    private EditText etMedicineName, etQuantity, etDosage;
    private Spinner spinnerPresentation;
    private TimePicker timePicker;
    private Button btnAdd, btnDelete, btnRegister;
    private ListView lvMedicines;

    private ArrayAdapter<String> medicineAdapter;
    public static ArrayList<String> medicines = new ArrayList<>();

    private static final String FILE_NAME = "medicines_data.txt";  // Nombre del archivo donde guardaremos los datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medicina);

        // Inicializar vistas
        etMedicineName = findViewById(R.id.et_medicine_name);
        etQuantity = findViewById(R.id.et_quantity);
        etDosage = findViewById(R.id.et_dosage);
        spinnerPresentation = findViewById(R.id.spinner_presentation);
        timePicker = findViewById(R.id.tp_time);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnRegister = findViewById(R.id.btn_register);
        lvMedicines = findViewById(R.id.lv_medicines);

        // Inicializar datos
        medicines = new ArrayList<>();
        medicineAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, medicines);
        lvMedicines.setAdapter(medicineAdapter);
        lvMedicines.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Cargar medicinas desde el archivo al iniciar la actividad
        loadMedicinesFromFile();

        // Configurar spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.presentations, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPresentation.setAdapter(spinnerAdapter);

        // Botón para añadir medicamentos
        btnAdd.setOnClickListener(v -> addMedicine());

        // Botón para eliminar medicamentos
        btnDelete.setOnClickListener(v -> deleteMedicine());

        // Botón para registrar toma
        btnRegister.setOnClickListener(v -> registerDose());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMedicinesFromFile(); // Filtrar medicinas por la fecha actual al reanudar
    }

    private void addMedicine() {
        String name = etMedicineName.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String dosage = etDosage.getText().toString().trim();
        String presentation = spinnerPresentation.getSelectedItem().toString();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        if (name.isEmpty() || quantity.isEmpty() || dosage.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener la fecha actual
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());

        // Formatear el medicamento con la fecha
        String medicine = currentDate + " - " + name + " - " + quantity + " unidades - " + presentation +
                " - " + dosage + " dosis - Hora: " + String.format("%02d:%02d", hour, minute);

        medicines.add(medicine);
        medicineAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Medicamento añadido", Toast.LENGTH_SHORT).show();

        // Guardar medicinas en el archivo
        saveMedicinesToFile();

        // Limpiar los campos de entrada
        etMedicineName.setText("");
        etQuantity.setText("");
        etDosage.setText("");
        spinnerPresentation.setSelection(0);
    }

    private void deleteMedicine() {
        int position = lvMedicines.getCheckedItemPosition();
        if (position == ListView.INVALID_POSITION) {
            Toast.makeText(this, "Seleccione un medicamento para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Eliminar Medicamento")
                .setMessage("¿Está seguro de que desea eliminar este medicamento?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    medicines.remove(position);
                    medicineAdapter.notifyDataSetChanged();
                    lvMedicines.clearChoices(); // Limpiar selección
                    Toast.makeText(this, "Medicamento eliminado", Toast.LENGTH_SHORT).show();

                    // Guardar medicinas en el archivo después de eliminar
                    saveMedicinesToFile();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void registerDose() {
        int position = lvMedicines.getCheckedItemPosition();
        if (position == ListView.INVALID_POSITION) {
            Toast.makeText(this, "Seleccione un medicamento para registrar", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedMedicine = medicines.get(position);
        new AlertDialog.Builder(this)
                .setTitle("Registrar Toma")
                .setMessage("¿Qué acción desea realizar con este medicamento?\n\n" + selectedMedicine)
                .setPositiveButton("Registrar", (dialog, which) -> {
                    Toast.makeText(this, "Toma registrada", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Reprogramar", (dialog, which) -> {
                    Toast.makeText(this, "Toma reprogramada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Saltar", (dialog, which) -> {
                    Toast.makeText(this, "Toma saltada", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    // Cargar medicinas desde el archivo
    private void loadMedicinesFromFile() {
        medicines.clear(); // Limpiar la lista antes de cargar
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()); // Fecha actual

        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(currentDate)) { // Verificar si la medicina corresponde a la fecha actual
                    medicines.add(line);
                }
            }

            reader.close();
            medicineAdapter.notifyDataSetChanged(); // Actualizar el adaptador con las medicinas filtradas
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Guardar medicinas en el archivo
    private void saveMedicinesToFile() {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            for (String medicine : medicines) {
                writer.write(medicine);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
