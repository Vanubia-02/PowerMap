package com.ifbaiano.powermap.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.dao.firebase.StorageDaoFirebase;
import com.ifbaiano.powermap.model.CarModel;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;

import java.util.ArrayList;

public class ModelCarAdapter extends RecyclerView.Adapter {
    ArrayList<CarModel>  carModels;
    Context context;
    private ClickListener mclickListener;

    public ModelCarAdapter(ArrayList<CarModel> carModels, Context context) {
        this.carModels = carModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model_car,parent,false);
        return new ViewHolderClass(view, mclickListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        CarModel carModel = carModels.get(position);
        vhClass.name.setText(carModel.getName());
        vhClass.year.setText(Integer.toString(carModel.getYear()));

        if (carModel instanceof EletricCarModel) {
            vhClass.energyConsumption.setText(
                    context.getString(R.string.energy_consumption_label, Float.toString(((EletricCarModel) carModel).getEnergyConsumption()))
            );
            if (carModel instanceof HybridCarModel) {
                vhClass.fuelConsumption.setText(
                        context.getString(
                                R.string.fuel_consumption_label, Float.toString(((HybridCarModel) carModel).getFuelConsumption())
                        )
                );
            }
        }

         new StorageDaoFirebase().transformInBitmap(carModel.getPathImg(), vhClass.imageView, vhClass.progressBar);
    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, year, energyConsumption, fuelConsumption;
        ImageView imageView;

        ProgressBar progressBar;
        ClickListener clickListener;

        public ViewHolderClass(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            year = itemView.findViewById(R.id.year);
            energyConsumption =  itemView.findViewById(R.id.energyConsumption);
            fuelConsumption =  itemView.findViewById(R.id.fuelConsumption);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar);

            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getBindingAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
