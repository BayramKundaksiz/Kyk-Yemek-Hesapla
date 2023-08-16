package com.Kyk.yemekhesapla;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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


public class Aksam extends Fragment implements ITiklamaArayuzu {


    View rootViewAksam;
    private RecyclerView aksamRecyclerView;
    private List<YemekVerileri> aksamYemekVerileriList;
    private TextView textViewAksamSonuc, textViewAksamKalan;
    private AksamYemegiMenusu aksamRvAdapter;
    private VeriTabaniAksam aksamVt;
    public Button buttonAksamSifirlayici;
    private List<Double> AksamSonEklenenler;
    private FloatingActionButton aksamFloatingActionButtonEkle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootViewAksam = inflater.inflate(R.layout.fragment_aksam, container, false);

        aksamVt = new VeriTabaniAksam(getContext());

        aksamFloatingActionButtonEkle = rootViewAksam.findViewById(R.id.aksamFloatingActionButtonEkle);
        textViewAksamSonuc = rootViewAksam.findViewById(R.id.textViewAksamSonuc);
        textViewAksamKalan = rootViewAksam.findViewById(R.id.textViewAksamKalan);
        buttonAksamSifirlayici = rootViewAksam.findViewById(R.id.buttonAksamSifirlayici);
        aksamRecyclerView = rootViewAksam.findViewById(R.id.aksamRecyclerView);

        buttonAksamSifirlayici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AksamSonEklenenler.clear();
                textViewAksamKalan.setText("");
                textViewAksamSonuc.setTextColor(Color.parseColor("#61000000"));
                textViewAksamSonuc.setText("\t\t\t" + "Ücret Sıfırlandı");
                textViewAksamSonuc.setTextSize(20);
                aksamRvAdapter = new AksamYemegiMenusu(getContext(),aksamYemekVerileriList,aksamVt,Aksam.this);
                aksamRecyclerView.setAdapter(aksamRvAdapter);
            }
        });

        aksamFloatingActionButtonEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aksamGoster();
            }
        });


        aksamRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        aksamYemekVerileriList = new AksamYemeklereUlasim().tumYemekler(aksamVt);

        aksamRvAdapter = new AksamYemegiMenusu(getContext(), aksamYemekVerileriList, aksamVt, this);

        aksamRecyclerView.setAdapter(aksamRvAdapter);


        return rootViewAksam;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AksamSonEklenenler = new ArrayList<>();


    }

    public void aksamGoster() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View tasarim = layoutInflater.inflate(R.layout.alert_tasarim, null);
        EditText editTextYemekAdi = tasarim.findViewById(R.id.editTextFabAd);
        EditText editTextYemekUcreti = tasarim.findViewById(R.id.editTextFabUcret);

        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Akşam Yemeği Ekle");
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
                } else if (editTextYemekAdi.getText().toString().trim().equals("")) {

                    AlertDialog.Builder uyari2 = new AlertDialog.Builder(getContext());
                    uyari2.setTitle("DİKKAT");
                    uyari2.setMessage("Lütfen yemek adı alanını doldurunuz.");
                    uyari2.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari2.show();
                } else if (editTextYemekUcreti.getText().toString().trim().equals("")) {

                    AlertDialog.Builder uyari3 = new AlertDialog.Builder(getContext());
                    uyari3.setTitle("DİKKAT");
                    uyari3.setMessage("Lütfen yemek ücreti alanını doldurunuz.");
                    uyari3.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari3.show();

                } else {

                    String yemekAdi = editTextYemekAdi.getText().toString().trim();
                    Double yemekUcreti = Double.valueOf(editTextYemekUcreti.getText().toString().trim());

                    new AksamYemeklereUlasim().yemekEkle(aksamVt, yemekAdi, yemekUcreti);

                    aksamYemekVerileriList = new AksamYemeklereUlasim().tumYemekler(aksamVt);

                    aksamRvAdapter = new AksamYemegiMenusu(getContext(), aksamYemekVerileriList, aksamVt, Aksam.this);

                    aksamRecyclerView.setAdapter(aksamRvAdapter);

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



        aksamYemekVerileriList = new AksamYemeklereUlasim().tumYemekler(aksamVt);

        aksamRvAdapter = new AksamYemegiMenusu(getContext(), aksamYemekVerileriList, aksamVt, Aksam.this);



        Toast.makeText(getContext(), aksamYemekVerileriList.get(position).getYemekAdi() + " yemeği eklenildi.",
                Toast.LENGTH_SHORT).show();


        AksamSonEklenenler.add(aksamYemekVerileriList.get(position).yemekUcreti);

        Double toplam = 0.0;


        for (Double diziElemani : AksamSonEklenenler) {
            toplam = toplam + diziElemani;
        }

        if (toplam > 17) {
            toplam = toplam - 17;
            textViewAksamKalan.setText("");
            textViewAksamSonuc.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textViewAksamSonuc.setTextColor(Color.parseColor("#335755"));
            textViewAksamSonuc.setTextSize(45);
            textViewAksamSonuc.setText(decimalFormat.format(toplam)+"\t\t"+"₺");
        } else if (toplam == 17) {
            textViewAksamKalan.setText("");
            textViewAksamSonuc.setTextColor(Color.parseColor("#0043a7"));
            textViewAksamSonuc.setText("\t\t\t\t\t" + "TAM TUTTU");

        }

        else {
            Double kalan = 0.0;
            kalan = 17 - toplam;
            textViewAksamKalan.setText("Kalan: " + kalan);
            textViewAksamSonuc.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            textViewAksamSonuc.setTextSize(27);
            textViewAksamKalan.setTextColor(Color.parseColor("#c0c0c0"));
            textViewAksamSonuc.setTextColor(Color.parseColor("#e61919"));
            textViewAksamSonuc.setText(""+ decimalFormat.format(toplam)+"\t\t"+"₺");
        }



    }

    @Override
    public void onItemLongClick(int position) {

        AksamSonEklenenler.remove(aksamYemekVerileriList.get(position).getYemekUcreti());

        Toast.makeText(getContext(), aksamYemekVerileriList.get(position).getYemekAdi()
                +" yemeği silindi", Toast.LENGTH_SHORT).show();

        Double aksamCikarilanEleman = 0.0;

        for(Double silinen : AksamSonEklenenler) {
            aksamCikarilanEleman = aksamCikarilanEleman+silinen;
        }
        textViewAksamSonuc.setText(aksamCikarilanEleman+"toplam");

        if (aksamCikarilanEleman>17){
            aksamCikarilanEleman = aksamCikarilanEleman-8;
            textViewAksamKalan.setText("");
            textViewAksamSonuc.setTextColor(Color.parseColor("#335755"));
            textViewAksamSonuc.setTextSize(45);
            textViewAksamSonuc.setText(decimalFormat.format(aksamCikarilanEleman)+"\t\t"+"₺");
        }else if (aksamCikarilanEleman ==17){

            textViewAksamSonuc.setText("");
            textViewAksamSonuc.setTextColor(Color.parseColor("#0043a7"));
            textViewAksamSonuc.setText("\t\t\t\t\t"+"TAM TUTTU");
        }

        else if (aksamCikarilanEleman == 0) {
            textViewAksamKalan.setText("");
            textViewAksamSonuc.setTextColor(Color.parseColor("#61000000"));
            textViewAksamSonuc.setText("\t\t\t" + "Ücret Sıfırlandı");
        }

        else {

            double kalanAksam = 0.0;
            kalanAksam = 17 - aksamCikarilanEleman;
            textViewAksamSonuc.setTextSize(27);
            textViewAksamSonuc.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            textViewAksamKalan.setText("Kalan\t\t"+kalanAksam);
            textViewAksamSonuc.setTextColor(Color.parseColor("#e61919"));
            textViewAksamSonuc.setText(""+ decimalFormat.format(aksamCikarilanEleman)+"\t\t"+"₺");
        }
    }
}