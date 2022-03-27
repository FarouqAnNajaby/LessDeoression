package com.example.less_depression;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity {

    Animation fromBottom,fromtop;
    RelativeLayout btn_lanjut,bg_putih;
    private EditText edt_nama , edt_email, edt_pass ;
    private ImageView ivDate;
    private TextView tvDate,tvUmur;
    private String nama,email,password,umur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bg_putih = (RelativeLayout) findViewById(R.id.bg_putih);
        bg_putih.setAnimation(fromBottom);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        btn_lanjut = (RelativeLayout) findViewById(R.id.btn_lanjut);

        edt_nama = findViewById(R.id.et_namaPengguna);
        edt_email = findViewById(R.id.et_email);
        edt_pass = findViewById(R.id.et_pass_pengguna);
        ivDate = findViewById(R.id.IV_date);
        tvDate = findViewById(R.id.tv_tglLahir);
        tvUmur = findViewById(R.id.tv_umur);

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int tahun = c.get(Calendar.YEAR);
                int bulan = c.get(Calendar.MONTH);
                int hari = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        datePickerListener,tahun,bulan,hari);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanjut();
            }
        });

    }

    private void lanjut() {
        if(edt_nama.getText().length()==0) {
            edt_nama.setError("Harap isi nama anda!");
        }else if(edt_email.getText().toString().length()==0) {
            edt_email.setError("Harap isi email anda!");
        }else if(edt_pass.getText().toString().length()==0) {
            edt_pass.setError("Harap isi password anda!");
        }else if(tvDate.getText().toString().equalsIgnoreCase("Pilih Tanggal Lahir")) {
            Toast.makeText(this, "Harap isi tanggal lahir", Toast.LENGTH_SHORT).show();
        }else{
            nama = edt_nama.getText().toString();
            email = edt_email.getText().toString();
            password = edt_pass.getText().toString();
            umur = tvUmur.getText().toString();

            Intent i = new Intent(Register.this, Register2.class);
            i.putExtra("nama", nama);
            i.putExtra("email", email);
            i.putExtra("password", password);
            i.putExtra("umur",umur);
            startActivity(i);
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR,year);
                    c.set(Calendar.MONTH,month);
                    c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    String format = new SimpleDateFormat("dd-MM-YYYY").format(c.getTime());
                    tvDate.setText(format);
                    tvUmur.setText(Integer.toString(hitungUmur(c.getTimeInMillis())));
                }
            };

    private int hitungUmur(long date) {
        Calendar b = Calendar.getInstance();
        b.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - b.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_MONTH) < b.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }

}