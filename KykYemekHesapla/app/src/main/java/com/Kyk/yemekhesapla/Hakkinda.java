package com.Kyk.yemekhesapla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Hakkinda extends AppCompatActivity {

    private TextView textViewAciklamaBir,textViewAciklamaUc,textViewAciklamaIki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkinda);

        textViewAciklamaBir = findViewById(R.id.textViewAciklamaBir);
        textViewAciklamaIki = findViewById(R.id.textViewAciklamaIki);
        textViewAciklamaUc = findViewById(R.id.textViewAciklamaUc);



    }


}