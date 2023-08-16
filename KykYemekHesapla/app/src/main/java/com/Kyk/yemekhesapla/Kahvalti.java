package com.Kyk.yemekhesapla;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.N)
public class Kahvalti extends Fragment implements ITiklamaArayuzu {

    View rootView;
    private RecyclerView recyclerView;
    private ArrayList<YemekVerileri> yemekVerileriList;
    private Button buttonKahvaltiSifirlayici;
    private KahvaltiMenusu rvAdapter;
    private TextView textViewKahvaltiSonuc,textViewKahvaltiKalan;
    private VeriTabaniKahvalti vt;
    private List<Double> sonKahvaltiEklenenler;
    private FloatingActionButton floatingActionButtonEkle;


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_kahvalti, container, false);

        vt = new VeriTabaniKahvalti(getContext());

        textViewKahvaltiKalan = rootView.findViewById(R.id.textViewKahvaltiKalan);
        floatingActionButtonEkle = rootView.findViewById(R.id.floatingActionButtonEkle);
        textViewKahvaltiSonuc = rootView.findViewById(R.id.textViewKahvaltiSonuc);
        buttonKahvaltiSifirlayici = rootView.findViewById(R.id.buttonKahvaltiSifirlayici);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        floatingActionButtonEkle = rootView.findViewById(R.id.floatingActionButtonEkle);




        buttonKahvaltiSifirlayici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sonKahvaltiEklenenler.clear();
                textViewKahvaltiKalan.setText("");
                textViewKahvaltiSonuc.setTextColor(Color.parseColor("#61000000"));
                textViewKahvaltiSonuc.setTextSize(20);
                textViewKahvaltiSonuc.setText("\t\t\t"+"Ücret Sıfırlandı");
                rvAdapter = new KahvaltiMenusu(getContext(),yemekVerileriList,vt,Kahvalti.this);
                recyclerView.setAdapter(rvAdapter);
            }
        });

        floatingActionButtonEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kahvaltiGoster();
            }
        });




        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        yemekVerileriList = new KahvaltiyaUlasim().tumYemekler(vt);

        rvAdapter = new KahvaltiMenusu(getContext(),yemekVerileriList,vt,this);

        recyclerView.setAdapter(rvAdapter);

        return rootView;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sonKahvaltiEklenenler = new ArrayList<Double>();
    }



    public void kahvaltiGoster(){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View tasarim = layoutInflater.inflate(R.layout.alert_tasarim,null);
        EditText editTextYemekAdi = tasarim.findViewById(R.id.editTextFabAd);
        EditText editTextYemekUcreti = tasarim.findViewById(R.id.editTextFabUcret);



        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Kahvaltı Ekle");
        ad.setView(tasarim);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (editTextYemekAdi.getText().toString().trim().equals("")
                        && editTextYemekUcreti.getText().toString().trim().equals("")) {

                    AlertDialog.Builder uyari = new AlertDialog.Builder(getContext());
                    uyari.setTitle("DİKKAT");
                    uyari.setMessage("Lütfen her iki alanıda doldurunuz.");
                    uyari.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari.show();
                }

                else if (editTextYemekAdi.getText().toString().trim().equals("")) {

                    AlertDialog.Builder uyari2 = new AlertDialog.Builder(getContext());
                    uyari2.setTitle("DİKKAT");
                    uyari2.setMessage("Lütfen yemek adı alanını doldurunuz.");
                    uyari2.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari2.show();
                }

                else if (editTextYemekUcreti.getText().toString().trim().equals("0.0")
                        || editTextYemekUcreti.getText().toString().trim().equals("0") ||
                        editTextYemekUcreti.getText().toString().trim().equals("0.00")) {

                    AlertDialog.Builder sifirUyarisi = new AlertDialog.Builder(getContext());
                    sifirUyarisi.setTitle("DİKKAT");
                    sifirUyarisi.setMessage("Lütfen yemek ücreti alanına uygun ücret giriniz.");
                    sifirUyarisi.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    sifirUyarisi.show();
                }

                else if (editTextYemekUcreti.getText().toString().trim().equals("")) {

                    AlertDialog.Builder uyari3 = new AlertDialog.Builder(getContext());
                    uyari3.setTitle("DİKKAT");
                    uyari3.setMessage("Lütfen yemek ücreti alanını doldurunuz.");
                    uyari3.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari3.show();

                }

                else if (editTextYemekUcreti.getText().toString().trim().equals(".")) {

                    AlertDialog.Builder uyari3 = new AlertDialog.Builder(getContext());
                    uyari3.setTitle("DİKKAT");
                    uyari3.setMessage("Lütfen yemek ücreti alanına nokta koymayınız.");
                    uyari3.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari3.show();

                }

                else {

                    String yemekAdi = editTextYemekAdi.getText().toString().trim();
                    Double yemekUcreti = Double.valueOf(editTextYemekUcreti.getText().toString().trim());


                    new KahvaltiyaUlasim().yemekEkle(vt, yemekAdi, yemekUcreti);

                    yemekVerileriList = new KahvaltiyaUlasim().tumYemekler(vt);

                    rvAdapter = new KahvaltiMenusu(getContext(), yemekVerileriList, vt, Kahvalti.this);

                    recyclerView.setAdapter(rvAdapter);


                }

            }
        });

        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ad.create().show();

    }




    android.icu.text.DecimalFormat decimalFormat = new DecimalFormat("###,###.###");


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemClick(int position) {


        yemekVerileriList = new KahvaltiyaUlasim().tumYemekler(vt);

        rvAdapter = new KahvaltiMenusu(getContext(), yemekVerileriList,vt, Kahvalti.this);

        Toast.makeText(getContext(), yemekVerileriList.get(position).getYemekAdi()+" kahvaltılığı eklenildi.",
                Toast.LENGTH_SHORT).show();


        sonKahvaltiEklenenler.add(yemekVerileriList.get(position).getYemekUcreti());



        android.icu.text.DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        Double toplam = 0.0;


        for( Double diziElemani : sonKahvaltiEklenenler){
            toplam = toplam+diziElemani;
        }

        if (toplam>8){
            toplam = toplam-8;
            textViewKahvaltiKalan.setText("");
            textViewKahvaltiSonuc.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textViewKahvaltiSonuc.setTextSize(45);
            textViewKahvaltiSonuc.setTextColor(Color.parseColor("#335755"));
            textViewKahvaltiSonuc.setText(decimalFormat.format(toplam)+"\t\t"+"₺");
        }else if (toplam ==8){

            textViewKahvaltiKalan.setText("");
            textViewKahvaltiSonuc.setTextColor(Color.parseColor("#0043a7"));
            textViewKahvaltiSonuc.setText("\t\t\t\t\t"+"TAM TUTTU");
        }

        else {

            double kalan = 0.0;
            kalan = 8 - toplam;
            textViewKahvaltiKalan.setText("Kalan\t\t"+kalan);
            textViewKahvaltiSonuc.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            textViewKahvaltiSonuc.setTextSize(27);
            textViewKahvaltiKalan.setTextColor(Color.parseColor("#c0c0c0"));
            textViewKahvaltiSonuc.setTextColor(Color.parseColor("#e61919"));
            textViewKahvaltiSonuc.setText(""+ decimalFormat.format(toplam)+"\t\t"+"₺");
        }

    }

    @Override
    public void onItemLongClick(int position) {

        sonKahvaltiEklenenler.remove(yemekVerileriList.get(position).getYemekUcreti());

        Toast.makeText(getContext(), yemekVerileriList.get(position).getYemekAdi()
                +" silindi", Toast.LENGTH_SHORT).show();

        Double cikarilanEleman = 0.0;

        for(Double silinen : sonKahvaltiEklenenler) {
            cikarilanEleman = cikarilanEleman+silinen;
        }
        textViewKahvaltiSonuc.setText(cikarilanEleman+"toplam");

        if (cikarilanEleman>8){
            cikarilanEleman = cikarilanEleman-8;
            textViewKahvaltiKalan.setText("");
            textViewKahvaltiSonuc.setGravity(Gravity.LEFT);
            textViewKahvaltiSonuc.setTextSize(45);
            textViewKahvaltiSonuc.setTextColor(Color.parseColor("#335755"));
            textViewKahvaltiSonuc.setText(decimalFormat.format(cikarilanEleman)+"\t\t"+"₺");
        }else if (cikarilanEleman ==8){

            textViewKahvaltiKalan.setText("");
            textViewKahvaltiSonuc.setTextColor(Color.parseColor("#0043a7"));
            textViewKahvaltiSonuc.setText("\t\t\t\t\t"+"TAM TUTTU");
        }

        else if (cikarilanEleman == 0) {
            textViewKahvaltiKalan.setText("");
            textViewKahvaltiSonuc.setTextColor(Color.parseColor("#61000000"));
            textViewKahvaltiSonuc.setText("\t\t\t" + "Ücret Sıfırlandı");
        }

        else {

            double kalan = 0.0;
            kalan = 8 - cikarilanEleman;
            textViewKahvaltiSonuc.setTextSize(27);
            textViewKahvaltiSonuc.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            textViewKahvaltiKalan.setText("Kalan\t\t"+kalan);
            textViewKahvaltiSonuc.setTextColor(Color.parseColor("#e61919"));
            textViewKahvaltiSonuc.setText(""+ decimalFormat.format(cikarilanEleman)+"\t\t"+"₺");
        }

    }
}