package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder> {
    private List<Perfil> perfiles;
    private Context context;

    public PerfilAdapter(List<Perfil> perfiles, Context context) {
        this.perfiles = perfiles;
        this.context = context;
    }

    @NonNull
    @Override
    public PerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil, parent, false);
        return new PerfilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilViewHolder holder, int position) {
        Perfil perfil = perfiles.get(position);
        holder.nombreTextView.setText(perfil.getNombre());
        holder.relacionTextView.setText(perfil.getRelacion());
        holder.emailTextView.setText(perfil.getEmail());
    }

    @Override
    public int getItemCount() {
        return perfiles.size();
    }

    public static class PerfilViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView;
        TextView relacionTextView;
        TextView emailTextView;

        public PerfilViewHolder(View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.tvNombre);
            relacionTextView = itemView.findViewById(R.id.tvrelacion);
            emailTextView = itemView.findViewById(R.id.tvEmail);
        }
    }
}