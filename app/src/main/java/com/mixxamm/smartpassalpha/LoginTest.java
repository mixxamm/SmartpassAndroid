package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginTest extends AppCompatActivity {

    EditText Gebruikersnaam, Wachtwoord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        Gebruikersnaam = (EditText)findViewById(R.id.gebruikersnaamtest);
        Wachtwoord = (EditText)findViewById(R.id.wachtwoordtest);
    }
    public void OnLogin(View view){
        String gebruikersnaam = Gebruikersnaam.getText().toString();
        String wachtwoord = Wachtwoord.getText().toString();
        String type = "login";
        Login login = new Login(this);
        login.execute(type, gebruikersnaam, wachtwoord);
    }

}
