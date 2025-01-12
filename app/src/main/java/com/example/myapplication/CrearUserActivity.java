package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CrearUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_user);

        Button btnCreateProfile = findViewById(R.id.btn_create_profile);
        Button btnVerifyCode = findViewById(R.id.btn_verify_code);

        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearUserActivity.this, CrearPerfilActivity.class);
                startActivity(intent);
            }
        });
        Button btnSeleccionarProfile = findViewById(R.id.btn_seleccionar_perfil);
        btnSeleccionarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearUserActivity.this, SeleccionarPerfilActivity.class);
                startActivity(intent);
            }
        });

        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
