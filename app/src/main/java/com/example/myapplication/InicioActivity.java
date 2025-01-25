package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InicioActivity extends AppCompatActivity {
    private FloatingActionButton fabMain, fabHeart, fabPill;
    private boolean isFabOpen = false;
    private TextView tvProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inicio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincula el TextView
        tvProfileName = findViewById(R.id.profile_name);

        // Recupera el nombre del perfil del Intent
        PerFil perfilData = (PerFil) getIntent().getSerializableExtra("perfil_data");
        if (perfilData != null) {
            // Muestra el nombre del perfil en el TextView
            tvProfileName.setText(perfilData.getNombre());
        }
        FloatingActionsMenu multipleActions = findViewById(R.id.multiple_actions);

        FloatingActionButton actionA = findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Action A clicked", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton actionB = findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Action B clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
