package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class Betalen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betalen);

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_betalen);

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
                        break;
                    case 2:
                        Intent instellingen = new Intent(Betalen.this, Instellingen.class);
                        startActivity(instellingen);
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }
}
