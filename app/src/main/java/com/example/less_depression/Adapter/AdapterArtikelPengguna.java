package com.example.less_depression.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.less_depression.Model.DataModel;
import com.example.less_depression.Pengguna.detail_artikel;
import com.example.less_depression.R;
import com.example.less_depression.ubah_artikel;

import java.util.List;

public class AdapterArtikelPengguna extends RecyclerView.Adapter<AdapterArtikelPengguna.HolderData> {


    private Context ctx;
    private List<DataModel> listDataArtikel;

    public AdapterArtikelPengguna(Context ctx, List<DataModel> listData){
        this.ctx = ctx;
        this.listDataArtikel = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artikel, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArtikelPengguna.HolderData holder, int position) {
        DataModel dm = listDataArtikel.get(position);

        holder.tvKodeArtikel.setText(dm.getKode_artikel());
        holder.tvJudulArtikel.setText(dm.getJudul_artikel());
        holder.tvIsiArtikel.setText(dm.getIsi_artikel());
        holder.tvTingkatanDepresi.setText(dm.getTingkat_depresi());
        holder.tvGambarArtikel.setText(dm.getGambar_artikel());
        holder.tvLokasiGambar.setText(dm.getLokasi_artikel());

    }

    @Override
    public int getItemCount() {
        return listDataArtikel.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        TextView tvJudulArtikel, tvKodeArtikel, tvIsiArtikel, tvTingkatanDepresi, tvGambarArtikel, tvLokasiGambar;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvKodeArtikel = itemView.findViewById(R.id.tv_kode_artikel);
            tvJudulArtikel = itemView.findViewById(R.id.tv_judul_artikel);
            tvIsiArtikel = itemView.findViewById(R.id.tv_isi_artikel);
            tvTingkatanDepresi = itemView.findViewById(R.id.tv_tingkatan_depresi);
            tvGambarArtikel = itemView.findViewById(R.id.tv_gambar_artikel);
            tvLokasiGambar = itemView.findViewById(R.id.tv_lokasi_artikel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kode_artikel =tvKodeArtikel.getText().toString();
                    String judul_artikel =tvJudulArtikel.getText().toString();
                    String isi_artikel =tvIsiArtikel.getText().toString();
                    String tingkatan_depresi =tvTingkatanDepresi.getText().toString();
                    String gambar_artikel =tvGambarArtikel.getText().toString();
                    String lokasi_gambar =tvLokasiGambar.getText().toString();

                    Intent i = new Intent(view.getContext(), detail_artikel.class);
                    i.putExtra("kode_artikel", kode_artikel);
                    i.putExtra("judul_artikel", judul_artikel);
                    i.putExtra("isi_artikel", isi_artikel);
                    i.putExtra("tingkatan_depresi", tingkatan_depresi);
                    i.putExtra("gambar_artikel", gambar_artikel);
                    i.putExtra("lokasi_gambar", lokasi_gambar);
                    view.getContext().startActivity(i);

                }
            });
        }
    }
}
