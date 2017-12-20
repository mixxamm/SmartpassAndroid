package com.mixxamm.smartpassalpha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

public class LoginTest extends AppCompatActivity {

    ProgressBar progressBar;
    EditText Gebruikersnaam, Wachtwoord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        Gebruikersnaam = (EditText)findViewById(R.id.gebruikersnaamtest);
        Wachtwoord = (EditText)findViewById(R.id.wachtwoordtest);

    }
    public void OnLogin(View view){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_laden);
        progressBar.setVisibility(View.VISIBLE);
        String gebruikersnaam = Gebruikersnaam.getText().toString();
        String wachtwoord = Wachtwoord.getText().toString();
        String type = "login";//Zorgt ervoor dat de klasse login weet dat we willen inloggen. (In de toekomst kunnen we nog andere functies toevoegen)
        Login login = new Login(this);
        login.execute(type, gebruikersnaam, wachtwoord);
    }

}
