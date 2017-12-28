package com.mixxamm.smartpassalpha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.mixxamm.smartpassalpha.R.*;


public class LoginTest extends AppCompatActivity {

    ProgressBar progressBar;
    EditText Gebruikersnaam, Wachtwoord;

    //Voorkeuren
    private static final String PREFS= "preferences";
    private static final String PREF_GEBRUIKERSNAAM = "Gebruikersnaam";
    private static final String PREF_WACHTWOORD = "Wachtwoord";
    private final String DefaultGebruikersnaamValue = "";
    private String GebruikersnaamValue;

    private final String DefaultWachtwoordValue = "";
    private String WachtwoordValue;
    @Override
    public void onPause(){
        super.onPause();
        savePreferences();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login_test);
        Gebruikersnaam = (EditText)findViewById(id.gebruikersnaamtest);
        Wachtwoord = (EditText)findViewById(id.wachtwoordtest);
        Button loginButton = (Button) findViewById(id.loginButton);
        TextView wachtwoordInstellen = (TextView) findViewById(id.wachtwoordInstellen);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar progressBar = (ProgressBar) findViewById(id.login_laden);
                progressBar.setVisibility(View.VISIBLE);
                String gebruikersnaam = Gebruikersnaam.getText().toString();
                String wachtwoord = Wachtwoord.getText().toString();
                SharedPreferences sharedPreferences = LoginTest.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PREF_GEBRUIKERSNAAM, gebruikersnaam);
                String type = "login";//Zorgt ervoor dat de klasse login weet dat we willen inloggen. (In de toekomst kunnen we nog andere functies toevoegen)

                Login login = new Login(LoginTest.this);
                login.execute(type, gebruikersnaam, wachtwoord);
            }
        });


        wachtwoordInstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wachtwoordInstellen = new Intent(LoginTest.this, WachtwoordInstellen.class);
                startActivity(wachtwoordInstellen);
            }
        });
    }
    private void savePreferences(){

    }


}
