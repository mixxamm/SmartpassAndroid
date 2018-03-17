package com.mixxamm.smartpassalpha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WachtwoordInstellen extends AppCompatActivity {

    static String type;
    EditText Gebruikersnaam, Wachtwoord, HerhaalWachtwoord, OudWachtwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wachtwoord_instellen);
        Gebruikersnaam = findViewById(R.id.gebruikersnaamwachtwoordinstellen);
        OudWachtwoord = findViewById(R.id.oudWachtwoord);
        Wachtwoord = findViewById(R.id.etwachtwoordinstellen);
        HerhaalWachtwoord = findViewById(R.id.etherhaalwachtwoordinstellen);
        Button stelWachtwoordIn = findViewById(R.id.btnwachtwoordinstellen);

        stelWachtwoordIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gebruikersnaam = Gebruikersnaam.getText().toString();
                String wachtwoord = Wachtwoord.getText().toString();
                String herhaalWachtwoord = HerhaalWachtwoord.getText().toString();
                String oudWachtwoord = OudWachtwoord.getText().toString();
                if(wachtwoord.equals(herhaalWachtwoord)){
                    StelWachtwoordIn stelWachtwoordIn1 = new StelWachtwoordIn(WachtwoordInstellen.this);
                    stelWachtwoordIn1.execute(type, gebruikersnaam, oudWachtwoord, wachtwoord);
                }
                else{
                    Toast.makeText(WachtwoordInstellen.this, "Wachtwoorden komen niet overeen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
