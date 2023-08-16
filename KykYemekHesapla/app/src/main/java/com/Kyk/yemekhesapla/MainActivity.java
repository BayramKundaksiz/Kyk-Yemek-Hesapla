package com.Kyk.yemekhesapla;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Kahvalti kahvalti = new Kahvalti();
    Aksam aksam = new Aksam();

    private AdView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        banner = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, kahvalti)
                    .commit();
        }


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.Breakfast:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,kahvalti).commit();
                        return true;
                    case R.id.Dinner:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, aksam).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.anamenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.iletisim:

                Intent IletisimeGec = new Intent(getApplicationContext(), Iletisim.class);
                startActivity(IletisimeGec);

                break;

            case R.id.hakkinda:
                Intent hakkinda = new Intent(getApplicationContext(), Hakkinda.class);
                startActivity(hakkinda);

                break;



        }

        return super.onOptionsItemSelected(item);
    }
}