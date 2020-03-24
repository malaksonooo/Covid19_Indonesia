package com.fahmisbas.covid19indonesia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProvinsiDataAdapter extends RecyclerView.Adapter<ProvinsiDataAdapter.ViewHolder> {

    ArrayList<ProvinsiModel> provinsiData;

    ProvinsiDataAdapter(ArrayList<ProvinsiModel> provinsiData){
        this.provinsiData = provinsiData;
    }

    @NonNull
    @Override
    public ProvinsiDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dataprovinsi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinsiDataAdapter.ViewHolder holder, int position) {
        ProvinsiModel model = provinsiData.get(position);

        holder.tvSembuh.setText(model.getKasusSembuh());
        holder.tvProvinsi.setText(model.getNamaProvinsi());
        holder.tvPositif.setText(model.getKasusPositif());
        holder.tvMeninggal.setText(model.getKasusMeninggal());

    }

    @Override
    public int getItemCount() {
        return provinsiData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSembuh,tvMeninggal,tvPositif,tvProvinsi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSembuh = itemView.findViewById(R.id.tv_sembuh);
            tvMeninggal = itemView.findViewById(R.id.tv_meninggal);
            tvPositif = itemView.findViewById(R.id.tv_positif);
            tvProvinsi = itemView.findViewById(R.id.tv_provinsi);
        }
    }
}
