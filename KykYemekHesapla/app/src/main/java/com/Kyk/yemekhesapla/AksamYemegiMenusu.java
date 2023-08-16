package com.Kyk.yemekhesapla;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AksamYemegiMenusu extends RecyclerView.Adapter<AksamYemegiMenusu.AksamYemegiNesneTutucu>{



    private Context aContext;
    private List<YemekVerileri> aksamYemegiVerileriList;
    private VeriTabaniAksam aksamVeriTabani;
    private ITiklamaArayuzu tiklamaArayuzuAksam;


    public AksamYemegiMenusu(Context aContext, List<YemekVerileri> aksamYemegiVerileriList
            , VeriTabaniAksam aksamVeriTabani, ITiklamaArayuzu tiklamaArayuzuAksam) {

        this.aContext = aContext;
        this.aksamYemegiVerileriList = aksamYemegiVerileriList;
        this.aksamVeriTabani = aksamVeriTabani;
        this.tiklamaArayuzuAksam = tiklamaArayuzuAksam;

    }


    @NonNull
    @Override
    public AksamYemegiNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View a;
        a = LayoutInflater.from(aContext).inflate(R.layout.aksam_yemek_siralamasi,parent,false);



        final AksamYemegiNesneTutucu aHolder = new AksamYemegiNesneTutucu(a);



        return aHolder;
    }




    public class AksamYemegiNesneTutucu extends RecyclerView.ViewHolder{


        private TextView aksamGelenYemekAdi;
        private TextView aksamGelenYemekUcreti;
        private ImageView imageViewAksamNokta;
        private CardView aksamSatirCardView;
        private  TextView textViewAksamSayac;



        public AksamYemegiNesneTutucu(View itemView){
            super(itemView);

            aksamGelenYemekAdi =  itemView.findViewById(R.id.aksamGelenYemekAdi);
            aksamGelenYemekUcreti = itemView.findViewById(R.id.aksamGelenYemekUcreti);
            imageViewAksamNokta = itemView.findViewById(R.id.imageViewAksamNokta);
            aksamSatirCardView = itemView.findViewById(R.id.kahvaltiSatirCardView);
            textViewAksamSayac = itemView.findViewById(R.id.textViewAksamSayac);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (tiklamaArayuzuAksam != null) {
                        // tiklamaArayuzu.onItemClick(view,getAdapterPosition());
                        tiklamaArayuzuAksam.onItemLongClick(getAdapterPosition());
                        textViewAksamSayac.setTag(Integer.parseInt(textViewAksamSayac.getTag().toString())-1);
                        textViewAksamSayac.setText(textViewAksamSayac.getTag().toString());

                        if (textViewAksamSayac.getTag().toString().equals("0")){
                            textViewAksamSayac.setText(textViewAksamSayac.getTag().toString());
                            aksamSatirCardView.setCardBackgroundColor(Color.parseColor("#C9A3DD"));

                        }

                        if(textViewAksamSayac.getTag().toString().equals("-1")){

                            textViewAksamSayac.setTag(Integer.parseInt(textViewAksamSayac.getTag().toString())+1);
                            textViewAksamSayac.setText(textViewAksamSayac.getTag().toString());

                            Toast.makeText(aContext, "Lütfen ürün ekleyiniz", Toast.LENGTH_LONG).show();

                            AlertDialog.Builder uyari3 = new AlertDialog.Builder(aContext);
                            uyari3.setTitle("DİKKAT");
                            uyari3.setMessage("SEÇTİĞİNİZ ÜRÜNÜ EKLEMEDİNİZ");
                            uyari3.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            uyari3.show();

                        }



                    }


                    return true;
                }
            });



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tiklamaArayuzuAksam != null) {
                        // tiklamaArayuzu.onItemClick(view,getAdapterPosition());
                        tiklamaArayuzuAksam.onItemClick(getAdapterPosition());
                        aksamSatirCardView.setCardBackgroundColor(Color.GREEN);

                        textViewAksamSayac.setTag(Integer.parseInt(textViewAksamSayac.getTag().toString())+1);

                        textViewAksamSayac.setText(textViewAksamSayac.getTag().toString());
                    }
                }
            });



        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull AksamYemegiNesneTutucu holder, @SuppressLint("RecyclerView") int position) {


        holder.aksamGelenYemekAdi.setText(aksamYemegiVerileriList.get(position).getYemekAdi());
        holder.aksamGelenYemekUcreti.setText(""+aksamYemegiVerileriList.get(position).getYemekUcreti());

        final YemekVerileri aksamYemekVerileri = aksamYemegiVerileriList.get(position);




        holder.imageViewAksamNokta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(aContext,holder.imageViewAksamNokta);
                popupMenu.getMenuInflater().inflate(R.menu.card_menu,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.menuSil:

                                new AksamYemeklereUlasim().yemekSil(aksamVeriTabani,aksamYemekVerileri.getYemekId());
                                aksamYemegiVerileriList = new AksamYemeklereUlasim().tumYemekler(aksamVeriTabani);
                                notifyDataSetChanged();

                                Toast.makeText(aContext, aksamYemekVerileri.getYemekAdi()+" yemeği silindi", Toast.LENGTH_SHORT).show();

                                return true;
                            case R.id.menuDuzenle:
                                aksamGoster(aksamYemekVerileri);
                                return true;
                            default:
                                return false;
                        }

                    }
                });



            }
        });

    }




    public void aksamGoster(final YemekVerileri aksamYemekVerileri){
        LayoutInflater layoutInflater = LayoutInflater.from(aContext);
        View tasarim = layoutInflater.inflate(R.layout.alert_tasarim,null);

        final EditText editTextYemekAdi = tasarim.findViewById(R.id.editTextFabAd);
        final EditText editTextYemekUcreti = tasarim.findViewById(R.id.editTextFabUcret);

        editTextYemekAdi.setText(aksamYemekVerileri.getYemekAdi());
        editTextYemekUcreti.setText(aksamYemekVerileri.getYemekUcreti().toString());

        AlertDialog.Builder ad = new AlertDialog.Builder(aContext);
        ad.setTitle("Akşam Yemeği Güncelle");
        ad.setView(tasarim);
        ad.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String yemekAdi = editTextYemekAdi.getText().toString().trim();
                Double yemekUcreti = Double.valueOf(editTextYemekUcreti.getText().toString().trim());

                new AksamYemeklereUlasim().yemekGuncelle(aksamVeriTabani,aksamYemekVerileri.getYemekId()
                        ,yemekAdi,yemekUcreti);

                aksamYemegiVerileriList = new AksamYemeklereUlasim().tumYemekler(aksamVeriTabani);
                notifyDataSetChanged();
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
        return null!=aksamYemegiVerileriList?aksamYemegiVerileriList.size():0;
    }

}