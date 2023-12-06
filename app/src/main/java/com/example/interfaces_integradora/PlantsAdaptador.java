package com.example.interfaces_integradora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaces_integradora.Models.ItemPlant;

import java.util.List;

public class PlantsAdaptador extends RecyclerView.Adapter<PlantsHolder>
{

    Context context;
    List<ItemPlant> items;

    public PlantsAdaptador(Context context, List<ItemPlant> items)
    {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PlantsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlantsHolder(LayoutInflater.from(context).inflate(R.layout.item_plant_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsHolder holder, int position)
    {
        holder.nombre.setText(items.get(position).getNombre());
        holder.imageview.setImageResource(items.get(position).getImage());

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
