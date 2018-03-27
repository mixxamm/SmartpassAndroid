package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

public class Instellingen extends AppCompatActivity {

    //Voorkeuren
    public static final String PREFS_ALGEMEEN = "Algemeen";
    static String naamGebruiker;
    Switch donkereModus;
    ImageView uitloggen, donkereModusImageView, bugReportImageView, ondersteuningImageView, nieuwWachtwoordImageView;
    TextView bugReport, ondersteuning, nieuwWachtwoordTextView;
    LinearLayout nieuwWachtwoord, testVersie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instellingen);

        SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
        naamGebruiker = account.getString("naamGebruiker", "");

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_instellingen);

        // Maak items
        AHBottomNavigationItem betalen = new AHBottomNavigationItem("Betalen", R.drawable.betalen, R.color.colorAccent);
        AHBottomNavigationItem leerlingenkaart = new AHBottomNavigationItem("Kaart", R.drawable.account1, R.color.colorAccent);
        AHBottomNavigationItem instellingen = new AHBottomNavigationItem("Instellingen", R.drawable.instellingen, R.color.colorAccent);

        // Items toevoegen
        bottomNavigation.addItem(betalen);
        bottomNavigation.addItem(leerlingenkaart);
        bottomNavigation.addItem(instellingen);

        bottomNavigation.setCurrentItem(2);

        //Listeners maken
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position){
                    case 0:
                        Intent betalen = new Intent(Instellingen.this, Betalen.class);
                        startActivity(betalen);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case 1:
                        Intent kaart = new Intent(Instellingen.this, LeerlingenKaartActivity.class);
                        startActivity(kaart);
                        overridePendingTransition(0, 0);
                        finish();
                    case 2:
                        break;
                }
                return true;
            }
        });

        bugReport = findViewById(R.id.bugReport);
        bugReport.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String url = "https://github.com/mixxamm/SmartpassAndroid/issues";
                Intent link = new Intent(Intent.ACTION_VIEW);
                link.setData(Uri.parse(url));
                startActivity(link);
            }
        });

        testVersie = findViewById(R.id.testVersie);
        testVersie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Instellingen.this, LeerlingActivity.class);
                startActivity(intent);
            }
        });

        ondersteuning = findViewById(R.id.ondersteuning);
        ondersteuning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail();
            }
        });

        nieuwWachtwoord = findViewById(R.id.nieuwWachtwoordLinearLayout);
        nieuwWachtwoord.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                WachtwoordInstellen wachtwoordInstellen = new WachtwoordInstellen();
                wachtwoordInstellen.type = "login";
                Intent intent = new Intent(Instellingen.this, WachtwoordInstellen.class);
                intent.putExtra("gebruikersnaam", naamGebruiker);
                startActivity(intent);
            }
        });

        TextView uitLogKnop = (TextView) findViewById(R.id.uitLogKnop);
        uitLogKnop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetLeerlingNaam();
                Intent main = new Intent(Instellingen.this, MainActivity.class);
                startActivity(main);//Teruggaan naar hoofdactiviteit
                finish();
            }
        });


        donkereModus = findViewById(R.id.donkereModus);
        uitloggen = findViewById(R.id.uitloggen);
        donkereModusImageView = findViewById(R.id.donkereModusImageView);
        bugReportImageView = findViewById(R.id.bugReportImageView);
        ondersteuningImageView = findViewById(R.id.ondersteuningImageView);
        nieuwWachtwoordImageView = findViewById(R.id.nieuwWachtwoordImageView);
        nieuwWachtwoordTextView = findViewById(R.id.nieuwWachtwoordTextView);
        //Voorkeuren
        SharedPreferences algemeen = getSharedPreferences(PREFS_ALGEMEEN, 0);
        boolean donker = algemeen.getBoolean("donkereModus", false);


        if(donker){
            donkereModus.setChecked(true);
            int color = Color.parseColor("#000000");
            int color2 = Color.parseColor("#000000");
            bottomNavigation.setDefaultBackgroundColor(color);
            bottomNavigation.setAccentColor(Color.WHITE);
            bottomNavigation.setInactiveColor(Color.parseColor("#455A64"));
            setActivityBackgroundColor(color, color2);
            donkereModus.setTextColor(Color.WHITE);
            uitLogKnop.setTextColor(Color.WHITE);
            uitloggen.setColorFilter(Color.WHITE);
            donkereModusImageView.setColorFilter(Color.WHITE);
            bugReport.setTextColor(Color.WHITE);
            bugReportImageView.setColorFilter(Color.WHITE);
            ondersteuning.setTextColor(Color.WHITE);
            ondersteuningImageView.setColorFilter(Color.WHITE);
            nieuwWachtwoordImageView.setColorFilter(Color.WHITE);
            nieuwWachtwoordTextView.setTextColor(Color.WHITE);
        }
        donkereModus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Boolean donker = donkereModus.isChecked();
                if(donker){
                    SharedPreferences algemeen = getSharedPreferences(PREFS_ALGEMEEN, 0);
                    SharedPreferences.Editor editor = algemeen.edit();
                    editor.putBoolean("donkereModus", true);
                    editor.commit();
                }
                else{
                    SharedPreferences algemeen = getSharedPreferences(PREFS_ALGEMEEN, 0);
                    SharedPreferences.Editor editor = algemeen.edit();
                    editor.putBoolean("donkereModus", false);
                    editor.commit();

                }
                Intent instellingen = new Intent(Instellingen.this, Instellingen.class);
                startActivity(instellingen);
                finish();
            }
        });

    }

    public void resetLeerlingNaam(){
        SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
        SharedPreferences.Editor editor = account.edit();
        editor.putString("naamGebruiker", "");//Aangezien bij het inloggen enkel op de naam wordt gecontroleerd, hoeven we het wachtwoord niet te resetten
        editor.commit();//Dit voert de wijzigingen door
    }
    public void setActivityBackgroundColor(int color, int color2) {
        View layout;
        layout = findViewById(R.id.instellingenLayout);
        layout.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color2);
        }
    }
    public void composeEmail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "support.android@smartpass.one" });
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}
