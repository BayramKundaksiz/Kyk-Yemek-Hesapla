package com.Kyk.yemekhesapla;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KahvaltiMenusu extends RecyclerView.Adapter<KahvaltiMenusu.NesneTutucu>{



    private Context mContext;
    private List<YemekVerileri> kahvaltiVerileri;
    private VeriTabaniKahvalti vt;
    private ITiklamaArayuzu tiklamaArayuzu;



    public KahvaltiMenusu(Context mContext, List<YemekVerileri> kahvaltiVerileri,
                          VeriTabaniKahvalti vt, ITiklamaArayuzu tiklamaArayuzu) {

        this.mContext = mContext;
        this.kahvaltiVerileri = kahvaltiVerileri;
        this.vt = vt;
        this.tiklamaArayuzu = tiklamaArayuzu;
    }




    @NonNull
    @Override
    public NesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.kahvalti_siralamasi,parent,false);



        final NesneTutucu vHolder = new NesneTutucu(view);





        return vHolder;
    }






    public class NesneTutucu extends RecyclerView.ViewHolder{


        private TextView gelenKahvaltiAdi;
        private TextView gelenKahvaltiUcreti;
        public TextView textViewKahvaltiSayac;
        private ImageView imageViewAksamNokta;
        private CardView kahvaltiSatirCardView;


        public NesneTutucu(View itemView){
            super(itemView);

            gelenKahvaltiAdi =  itemView.findViewById(R.id.kahvaltiGelenAdi);
            gelenKahvaltiUcreti = itemView.findViewById(R.id.kahvaltiGelenUcreti);
            textViewKahvaltiSayac = itemView.findViewById(R.id.textViewKahvaltiSayac);
            imageViewAksamNokta = itemView.findViewById(R.id.imageViewAksamNokta);
            kahvaltiSatirCardView = itemView.findViewById(R.id.kahvaltiSatirCardView);



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (tiklamaArayuzu != null) {
                        // tiklamaArayuzu.onItemClick(view,getAdapterPosition());
                        tiklamaArayuzu.onItemLongClick(getAdapterPosition());
                        textViewKahvaltiSayac.setTag(Integer.parseInt(textViewKahvaltiSayac.getTag().toString()) - 1);
                        textViewKahvaltiSayac.setText(textViewKahvaltiSayac.getTag().toString());

                        if (textViewKahvaltiSayac.getTag().toString().equals("0")){
                            textViewKahvaltiSayac.setText(textViewKahvaltiSayac.getTag().toString());
                            kahvaltiSatirCardView.setCardBackgroundColor(Color.parseColor("#7FBEE1"));
                        }

                        if(textViewKahvaltiSayac.getTag().toString().equals("-1")){

                            textViewKahvaltiSayac.setTag(Integer.parseInt(textViewKahvaltiSayac.getTag().toString())+1);
                            textViewKahvaltiSayac.setText(textViewKahvaltiSayac.getTag().toString());

                            Toast.makeText(mContext, "Lütfen ürün ekleyiniz", Toast.LENGTH_LONG).show();

                            AlertDialog.Builder uyari4 = new AlertDialog.Builder(mContext);
                            uyari4.setTitle("DİKKAT");
                            uyari4.setMessage("SEÇTİĞİNİZ ÜRÜNÜ EKLEMEDİNİZ");
                            uyari4.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            uyari4.show();

                        }

                    }
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (tiklamaArayuzu != null) {

                        tiklamaArayuzu.onItemClick(getAdapterPosition());
                        kahvaltiSatirCardView.setCardBackgroundColor(Color.GREEN);

                        textViewKahvaltiSayac.setTag(Integer.parseInt(textViewKahvaltiSayac.getTag().toString())+1);

                        textViewKahvaltiSayac.setText(textViewKahvaltiSayac.getTag().toString());


                    }

                }
            });


        }

    }


    @Override
    public void onBindViewHolder(@NonNull NesneTutucu holder, @SuppressLint("RecyclerView") int position) {





        holder.gelenKahvaltiAdi.setText(kahvaltiVerileri.get(position).getYemekAdi());
        holder.gelenKahvaltiUcreti.setText(""+kahvaltiVerileri.get(position).getYemekUcreti());



        final YemekVerileri kahvaltiMenusu = kahvaltiVerileri.get(position);

        holder.imageViewAksamNokta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(mContext,holder.imageViewAksamNokta);
                popupMenu.getMenuInflater().inflate(R.menu.card_menu,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.menuSil:

                                new KahvaltiyaUlasim().yemekSil(vt,kahvaltiMenusu.getYemekId());
                                kahvaltiVerileri = new KahvaltiyaUlasim().tumYemekler(vt);
                                notifyDataSetChanged();


                                return true;
                            case R.id.menuDuzenle:
                                kahvaltiGoster(kahvaltiMenusu);
                                return true;

                            default:
                                return false;
                        }

                    }
                });



            }
        });


    }

    public void kahvaltiGoster(final YemekVerileri kahvaltiMenusu){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View tasarim = layoutInflater.inflate(R.layout.alert_tasarim,null);

        EditText editTextYemekAdi = tasarim.findViewById(R.id.editTextFabAd);
        EditText editTextYemekUcreti = tasarim.findViewById(R.id.editTextFabUcret);

        editTextYemekAdi.setText(kahvaltiMenusu.getYemekAdi());
        editTextYemekUcreti.setText(kahvaltiMenusu.getYemekUcreti().toString());

        AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
        ad.setTitle("Yemek Güncelle");
        ad.setView(tasarim);
        ad.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (editTextYemekAdi.getText().toString().trim().equals("")) {

                    AlertDialog.Builder boslukUyarisi = new AlertDialog.Builder(mContext);
                    boslukUyarisi.setTitle("DİKKAT");
                    boslukUyarisi.setMessage("Yemek adını boş bırakmayınız");
                    boslukUyarisi.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    boslukUyarisi.show();
                }

                else if (editTextYemekUcreti.getText().toString().trim().equals("0.0")
                        || editTextYemekUcreti.getText().toString().trim().equals("0") ||
                        editTextYemekUcreti.getText().toString().trim().equals("0.00")) {

                    AlertDialog.Builder sifirUyarisi = new AlertDialog.Builder(mContext);
                    sifirUyarisi.setTitle("DİKKAT");
                    sifirUyarisi.setMessage("Lütfen yemek ücreti alanına uygun ücret giriniz.");
                    sifirUyarisi.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    sifirUyarisi.show();
                }

                else {
                    String yemekAdi = editTextYemekAdi.getText().toString().trim();
                    Double yemekUcreti = Double.valueOf(editTextYemekUcreti.getText().toString().trim());

                    new KahvaltiyaUlasim().yemekGuncelle(vt, kahvaltiMenusu.getYemekId()
                            , yemekAdi, yemekUcreti);


                    kahvaltiVerileri = new KahvaltiyaUlasim().tumYemekler(vt);
                    notifyDataSetChanged();
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



    @Override
    public int getItemCount() {
        return kahvaltiVerileri == null ? 0 : kahvaltiVerileri.size();
    }


}