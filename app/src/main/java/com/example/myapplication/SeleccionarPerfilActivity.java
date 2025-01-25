package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SeleccionarPerfilActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PerfilAdapter adapter;
    private List<PerFil> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seleccionar_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linerlayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Utilizar el método estático para leer los datos
        dataList = PerfilUtil.leeDelArchivo(this);

        recyclerView = findViewById(R.id.rvPerfiles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PerfilAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
    }


}
