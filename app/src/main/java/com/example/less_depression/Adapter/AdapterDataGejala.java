package com.example.less_depression.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.less_depression.Detailgejala;
import com.example.less_depression.Model.DataModel;
import com.example.less_depression.R;

import java.util.List;

public class AdapterDataGejala extends RecyclerView.Adapter<AdapterDataGejala.HolderData> {

    private Context ctx;
    private List<DataModel> listData;

    public AdapterDataGejala(Context ctx, List<DataModel> listData){
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gejala, parent, false);
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

        TextView tvKodeGejala, tvNamaGejala, tvBobot, tvRingan, tvSedang, tvBerat;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvKodeGejala = itemView.findViewById(R.id.tv_kode_gejala);
            tvNamaGejala = itemView.findViewById(R.id.tv_nama_gejala);
            tvBobot = itemView.findViewById(R.id.tv_bobot_gejala);
            tvRingan = itemView.findViewById(R.id.tv_depresi_ringan);
            tvSedang = itemView.findViewById(R.id.tv_depresi_sedang);
            tvBerat = itemView.findViewById(R.id.tv_depresi_berat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kode_gejala =tvKodeGejala.getText().toString();
                    String nama_gejala =tvNamaGejala.getText().toString();
                    String bobot_gejala =tvBobot.getText().toString();
                    String gejala_ringan =tvRingan.getText().toString();
                    String gejala_sedang =tvSedang.getText().toString();
                    String gejala_berat =tvBerat.getText().toString();

                    Intent i = new Intent(view.getContext(), Detailgejala.class);
                    i.putExtra("kode_gejala", kode_gejala);
                    i.putExtra("nama_gejala", nama_gejala);
                    i.putExtra("bobot_gejala", bobot_gejala);
                    i.putExtra("gejala_ringan", gejala_ringan);
                    i.putExtra("gejala_sedang", gejala_sedang);
                    i.putExtra("gejala_berat", gejala_berat);
                    view.getContext().startActivity(i);

                }
            });
        }
    }
}
