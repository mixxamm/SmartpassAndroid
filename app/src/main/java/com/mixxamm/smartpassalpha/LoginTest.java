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
import android.widget.Toast;

import static com.mixxamm.smartpassalpha.R.*;


public class LoginTest extends AppCompatActivity {

    ProgressBar progressBar;
    EditText Gebruikersnaam, Wachtwoord;

    //Voorkeuren
    public static final String PREFS_NAME  = "NaamGebruiker";
    public static final String PREFS_WACHTWOORD = "WachtwoordGebruiker";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login_test);
        Gebruikersnaam = (EditText)findViewById(id.gebruikersnaamtest);
        Wachtwoord = (EditText)findViewById(id.wachtwoordtest);
        Button loginButton = (Button) findViewById(id.loginButton);
        TextView wachtwoordInstellen = (TextView) findViewById(id.wachtwoordInstellen);


        //Zorgt ervoor dat de gebruiker automatisch inlogt
        SharedPreferences naamGebruiker = getSharedPreferences(PREFS_NAME, 0);
        String naam = naamGebruiker.getString("naamGebruiker", "");
        SharedPreferences wachtwoordGebruiker = getSharedPreferences(PREFS_WACHTWOORD, 0);
        String wachtwoordGebruiker1 = wachtwoordGebruiker.getString("wachtwoordGebruiker", "");

        checkAccount(naam, wachtwoordGebruiker1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar progressBar = (ProgressBar) findViewById(id.login_laden);
                progressBar.setVisibility(View.VISIBLE);
                String gebruikersnaam = Gebruikersnaam.getText().toString();
                String wachtwoord = Wachtwoord.getText().toString();
                String type = "login";//Zorgt ervoor dat de klasse login weet dat we willen inloggen. (In de toekomst kunnen we nog andere functies toevoegen)

                SharedPreferences naamGebruiker = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = naamGebruiker.edit();
                editor.putString("naamGebruiker", gebruikersnaam);
                editor.commit();
                SharedPreferences wachtwoordGebruiker = getSharedPreferences(PREFS_WACHTWOORD, 0);
                SharedPreferences.Editor editor1 = wachtwoordGebruiker.edit();
                editor1.putString("wachtwoordGebruiker", wachtwoord);
                editor1.commit();

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

    public void checkAccount(String naam, String wachtwoord){
        if(naam != ""){
            ProgressBar progressBar = (ProgressBar) findViewById(id.login_laden);
            progressBar.setVisibility(View.VISIBLE);
            Login login = new Login(LoginTest.this);
            login.execute("login", naam, wachtwoord);
        }
    }

}
