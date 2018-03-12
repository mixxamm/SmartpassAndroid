package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import static com.mixxamm.smartpassalpha.Instellingen.PREFS_ALGEMEEN;

import org.w3c.dom.Text;

import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

public class Betalen extends AppCompatActivity {

    String naam;
    TextView textViewNaam, saldo, saldoText, betaalgeschiedenisText, betaalgeschiedenis, geldOvermakenText, overmakenText, betaalverzoekText;
    ImageView overmaken, betaalverzoek;
    AHBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betalen);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_betalen);

        // Maak items
        AHBottomNavigationItem betalen = new AHBottomNavigationItem("Betalen", R.drawable.betalen, R.color.colorAccent);
        AHBottomNavigationItem leerlingenkaart = new AHBottomNavigationItem("Kaart", R.drawable.account1, R.color.colorAccent);
        AHBottomNavigationItem instellingen = new AHBottomNavigationItem("Instellingen", R.drawable.instellingen, R.color.colorAccent);

        // Items toevoegen
        bottomNavigation.addItem(betalen);
        bottomNavigation.addItem(leerlingenkaart);
        bottomNavigation.addItem(instellingen);

        bottomNavigation.setCurrentItem(0);

        //Listeners maken
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position){
                    case 0:
                        break;
                    case 1:
                        Intent leerlingen = new Intent(Betalen.this, LeerlingenKaartActivity.class);
                        startActivity(leerlingen);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case 2:
                        Intent instellingen = new Intent(Betalen.this, Instellingen.class);
                        startActivity(instellingen);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return true;
            }
        });

        textViewNaam = findViewById(R.id.naam);
        SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
        naam = account.getString("naamGebruiker", "");
        textViewNaam.setText(naam);

        overmaken = findViewById(R.id.overmaken);
        betaalverzoek = findViewById(R.id.betaalverzoek);

        overmaken.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(Betalen.this, "Uw account is niet gemachtigd om deze actie uit te voeren", Toast.LENGTH_SHORT).show();
            }
        });

        betaalverzoek.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(Betalen.this, "Uw account is niet gemachtigd om deze actie uit te voeren", Toast.LENGTH_SHORT).show();
            }
        });

        //Voorkeuren
        SharedPreferences algemeen = getSharedPreferences(PREFS_ALGEMEEN, 0);
        boolean donker = algemeen.getBoolean("donkereModus", false);

        //Variabeles van textviews maken
        saldoText = findViewById(R.id.saldoText);
        saldo = findViewById(R.id.saldo);
        betaalgeschiedenisText = findViewById(R.id.betaalgeschiedenisText);
        betaalgeschiedenis = findViewById(R.id.betaalgeschiedenis);
        overmakenText = findViewById(R.id.overmakenText);
        betaalverzoekText = findViewById(R.id.betaalverzoekText);

        if(donker){
            int color = Color.parseColor("#000000");
            int color2 = Color.parseColor("#000000");
            bottomNavigation.setDefaultBackgroundColor(color);
            bottomNavigation.setAccentColor(Color.WHITE);
            bottomNavigation.setInactiveColor(Color.parseColor("#455A64"));
            setActivityBackgroundColor(color, color2);
            textViewNaam.setTextColor(Color.WHITE);
            saldoText.setTextColor(Color.WHITE);
            saldo.setTextColor(Color.WHITE);
            betaalgeschiedenisText.setTextColor(Color.WHITE);
            betaalgeschiedenis.setTextColor(Color.WHITE);
            overmakenText.setTextColor(Color.WHITE);
            overmaken.setColorFilter(Color.WHITE);
            betaalverzoekText.setTextColor(Color.WHITE);
            betaalverzoek.setColorFilter(Color.WHITE);
        }
    }
    public void setActivityBackgroundColor(int color, int color2) {
        View layout;
        layout = findViewById(R.id.betalenLayout);
        layout.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color2);
        }
    }
}
