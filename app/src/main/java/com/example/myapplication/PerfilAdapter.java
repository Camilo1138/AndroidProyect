package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<PerFil> dataList;
    private Context context;

    // Constructor con Context
    public PerfilAdapter(Context context, List<PerFil> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    // Constructor sin Context
    public PerfilAdapter(List<PerFil> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PerFil data = dataList.get(position);
        holder.tvNombre.setText(data.getNombre());
        holder.tvEmail.setText(data.getEmail());
        holder.tvRelacion.setText(data.getRelacion());

        // Aseguramos que el Context no sea nulo
        holder.btnAbrir.setOnClickListener(v -> {
            if (context == null) {
                context = v.getContext();
            }
            Intent intent = new Intent(context, InicioActivity.class);
            intent.putExtra("perfil_data", data);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
