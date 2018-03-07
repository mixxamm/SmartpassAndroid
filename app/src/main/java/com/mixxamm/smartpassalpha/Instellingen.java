package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

public class Instellingen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instellingen);

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
                        break;
                    case 1:
                        Intent kaart = new Intent(Instellingen.this, LeerlingenKaartActivity.class);
                        startActivity(kaart);
                        overridePendingTransition(0, 0);
                    case 2:
                        break;
                }
                return true;
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
    }

    public void resetLeerlingNaam(){
        SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
        SharedPreferences.Editor editor = account.edit();
        editor.putString("naamGebruiker", "");//Aangezien bij het inloggen enkel op de naam wordt gecontroleerd, hoeven we het wachtwoord niet te resetten
        editor.commit();//Dit voert de wijzigingen door
    }
}
