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
        Button buttonOpenSecondActivity = findViewById(R.id.startButton);
        buttonOpenSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearUserActivity.class);
                startActivity(intent);
            }
        });
    }

    public void irRegistro(View view) {
        Intent intent = new Intent(this, CrearPerfilActivity.class);
        startActivity(intent);
    }

    public void irSeleccion(View view) {
        Intent intent = new Intent(this, SeleccionarPerfilActivity.class);
        startActivity(intent);
    }
}
