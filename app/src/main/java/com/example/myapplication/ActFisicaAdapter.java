package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//k
public class ActFisicaAdapter extends RecyclerView.Adapter<ActFisicaAdapter.ActivityViewHolder> {

    private List<ActividadFisica> activityList;

    public ActFisicaAdapter(List<ActividadFisica> activityList) {
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actfisica, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActividadFisica activity = activityList.get(position);
        holder.tvDate.setText("Fecha: " + activity.getDate());
        holder.tvActivityType.setText("Actividad: " + activity.getActivityType());
        holder.tvDuration.setText("Duración: " + activity.getDuration() + " minutos");
        holder.tvTimeOfDay.setText("Horario: " + activity.getTimeOfDay());
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvActivityType, tvDuration, tvTimeOfDay;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvActivityType = itemView.findViewById(R.id.tvActivityType);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvTimeOfDay = itemView.findViewById(R.id.tvTimeOfDay);
        }
    }
}
