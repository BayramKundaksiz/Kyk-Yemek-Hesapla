package com.Kyk.yemekhesapla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Iletisim extends AppCompatActivity {

    EditText editTextKonu,editTextMesaj,editTextGonderilecekKisi;
    Button buttonMesajGonder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim);

        editTextGonderilecekKisi = findViewById(R.id.editTextGonderilecekKisi);
        editTextKonu = findViewById(R.id.editTextKonu);
        editTextMesaj = findViewById(R.id.editTextMesaj);
        buttonMesajGonder = findViewById(R.id.buttonMesajGonder);


        buttonMesajGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                epostaGonder();


            }
        });


    }

    private void epostaGonder(){

        String gonderilecekKisi = editTextGonderilecekKisi.getText().toString().trim();

        String konu = editTextKonu.getText().toString().trim();
        String mesaj = editTextMesaj.getText().toString().trim();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{gonderilecekKisi});
        intent.putExtra(Intent.EXTRA_SUBJECT, konu);
        intent.putExtra(Intent.EXTRA_TEXT,mesaj);

        if (mesaj.equals("")){
            Toast.makeText(this, "Lütfen mesajınızı yazınız", Toast.LENGTH_SHORT).show();
        }else {
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Lütfen E-posta seçiniz..."));
        }


    }
}