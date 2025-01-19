package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tvNombre, tvEmail, tvRelacion;
    Button btnAbrir;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNombre = itemView.findViewById(R.id.tvNombre);
        tvEmail = itemView.findViewById(R.id.tvEmail);
        tvRelacion = itemView.findViewById(R.id.tvrelacion);
        btnAbrir = itemView.findViewById(R.id.btnAbrir);
    }
}
