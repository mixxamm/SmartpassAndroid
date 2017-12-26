package com.mixxamm.smartpassalpha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;



public class LoginTest extends AppCompatActivity {

    ProgressBar progressBar;
    EditText Gebruikersnaam, Wachtwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        Gebruikersnaam = (EditText)findViewById(R.id.gebruikersnaamtest);
        Wachtwoord = (EditText)findViewById(R.id.wachtwoordtest);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        TextView wachtwoordInstellen = (TextView) findViewById(R.id.wachtwoordInstellen);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_laden);
                progressBar.setVisibility(View.VISIBLE);
                String gebruikersnaam = Gebruikersnaam.getText().toString();
                String wachtwoord = Wachtwoord.getText().toString();
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


}
