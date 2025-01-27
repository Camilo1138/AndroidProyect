package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Botón para abrir la actividad de agregar medicamentos
        Button buttonOpenAddMedicineActivity = findViewById(R.id.openAddMedicineActivity);  // Asegúrate de usar el ID correcto
        buttonOpenAddMedicineActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad de agregar medicinas
                Intent intent = new Intent(MainActivity.this, AgregarMedicinaActivity.class);
                startActivity(intent);
            }
        });
    }
}
