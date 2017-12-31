package com.mixxamm.smartpassalpha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WachtwoordInstellen extends AppCompatActivity {

    EditText Gebruikersnaam, Wachtwoord, HerhaalWachtwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wachtwoord_instellen);
        Gebruikersnaam = (EditText) findViewById(R.id.gebruikersnaamwachtwoordinstellen);
        Wachtwoord = (EditText) findViewById(R.id.etwachtwoordinstellen);
        HerhaalWachtwoord = (EditText) findViewById(R.id.etherhaalwachtwoordinstellen);
        Button stelWachtwoordIn = (Button) findViewById(R.id.btnwachtwoordinstellen);

        stelWachtwoordIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gebruikersnaam = Gebruikersnaam.getText().toString();
                String wachtwoord = Wachtwoord.getText().toString();
                String type = "stelWachtwoordIn";
                StelWachtwoordIn stelWachtwoordIn1 = new StelWachtwoordIn(WachtwoordInstellen.this);
                stelWachtwoordIn1.execute(type, gebruikersnaam, wachtwoord);
            }
        });
    }
}
