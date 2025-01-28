package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.CitaViewHolder> {

    private List<String> citasList;

    public CitaAdapter(List<String> citasList) {
        this.citasList = citasList;
    }

    @Override
    public CitaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita, parent, false);
        return new CitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitaViewHolder holder, int position) {
        String cita = citasList.get(position);
        holder.tvCita.setText(cita);
    }

    @Override
    public int getItemCount() {
        return citasList.size();
    }

    public static class CitaViewHolder extends RecyclerView.ViewHolder {
        TextView tvCita;

        public CitaViewHolder(View itemView) {
            super(itemView);
            tvCita = itemView.findViewById(R.id.tvCita);
        }
    }
}
