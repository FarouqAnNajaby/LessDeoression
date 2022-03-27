package com.example.less_depression.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.less_depression.Detailgejala;
import com.example.less_depression.Model.DataModel;
import com.example.less_depression.Pengguna.cekDepresi;
import com.example.less_depression.Pengguna.detail_artikel;
import com.example.less_depression.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCekGejala extends RecyclerView.Adapter<AdapterCekGejala.HolderData> {

    private Context ctx;
    private List<DataModel> listData;


    public AdapterCekGejala(Context ctx, List<DataModel> listData){
        this.ctx = ctx;
        this.listData = listData;
    }


    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gejala_pengguna, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listData.get(position);

        holder.tvKodeGejala.setText(dm.getKode_gejala());
        holder.tvNamaGejala.setText(dm.getNama_gejala());
        holder.tvBobot.setText(String.valueOf(dm.getBobot_gejala()));
        holder.tvRingan.setText(dm.getDepresi_ringan());
        holder.tvSedang.setText(dm.getDepresi_sedang());
        holder.tvBerat.setText(dm.getDepresi_berat());
        holder.tvSedang.setText(dm.getDepresi_sedang());
        holder.tvBerat.setText(dm.getDepresi_berat());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        TextView tvNamaGejala, tvKodeGejala, tvBobot, tvRingan, tvSedang, tvBerat;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvKodeGejala = itemView.findViewById(R.id.tv_kode_pilihan);
            tvNamaGejala = itemView.findViewById(R.id.tv_nama_gejala_pilihan);
            tvBobot = itemView.findViewById(R.id.tv_bobot_pilihan);
            tvRingan = itemView.findViewById(R.id.tv_depresi_ringan_pilihan);
            tvSedang = itemView.findViewById(R.id.tv_depresi_sedang_pilihan);
            tvBerat = itemView.findViewById(R.id.tv_depresi_berat_pilihan);

        }

    }
}
