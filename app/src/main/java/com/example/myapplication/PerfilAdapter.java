package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<PerFil> dataList;

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

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
