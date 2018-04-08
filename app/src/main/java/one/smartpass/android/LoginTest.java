package one.smartpass.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;

import static com.mixxamm.smartpassalpha.R.id;
import static com.mixxamm.smartpassalpha.R.layout;

public class LoginTest extends AppCompatActivity {

    public static String type;
    /*public ProgressBar progressBar = findViewById(id.progressBar);*/
    EditText Gebruikersnaam, Wachtwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login_test);
        Gebruikersnaam = (EditText) findViewById(id.gebruikersnaamtest);
        Wachtwoord = (EditText) findViewById(id.wachtwoordtest);
        Button loginButton = (Button) findViewById(id.loginButton);
        TextView wachtwoordInstellen = (TextView) findViewById(id.wachtwoordInstellen);




        /*checkAccount(naam, wachtwoordGebruiker1);*/

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("login")){
                    ProgressBar progressBar = (ProgressBar) findViewById(id.login_laden);
                    progressBar.setVisibility(View.VISIBLE);
                    String gebruikersnaam = Gebruikersnaam.getText().toString();
                    String wachtwoord = Wachtwoord.getText().toString();


                    SharedPreferences account = getSharedPreferences(MainActivity.ACCOUNT, 0);
                    SharedPreferences.Editor editor = account.edit();
                    editor.putString("naamGebruiker", gebruikersnaam);
                    editor.commit();

                    Login login = new Login(LoginTest.this);
                    login.execute(type, gebruikersnaam, wachtwoord);
                }
                else if(type.equals("loginLeerkracht")){
                    ProgressBar progressBar = (ProgressBar) findViewById(id.login_laden);
                    progressBar.setVisibility(View.VISIBLE);
                    String leerkrachtnaam = Gebruikersnaam.getText().toString();
                    String wachtwoord = Wachtwoord.getText().toString();
                    SharedPreferences account = getSharedPreferences(MainActivity.ACCOUNT, 0);
                    SharedPreferences.Editor editor = account.edit();
                    editor.putString("naamLeerkracht", leerkrachtnaam);
                    editor.commit();

                    Login login = new Login(LoginTest.this);
                    login.execute(type, leerkrachtnaam, wachtwoord);
                }

            }
        });


        wachtwoordInstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WachtwoordInstellen.type = type;
                Intent wachtwoordInstellen = new Intent(LoginTest.this, WachtwoordInstellen.class);
                startActivity(wachtwoordInstellen);
            }
        });
    }

    /*public void checkAccount(String naam, String wachtwoord) {
         else if (naam != "") {
            laden();//Geeft een progressbar weer en laat alle andere velden verdwijnen
            Login login = new Login(LoginTest.this);
            login.execute("login", naam, wachtwoord);
        }
    }*/



    private void laden(){
        ProgressBar progressBar = (ProgressBar) findViewById(id.login_laden);
        progressBar.setVisibility(View.VISIBLE);
        TextView textViewGebruikersnaam = (TextView) findViewById(id.gebruikersnaamtest);
        textViewGebruikersnaam.setVisibility(View.INVISIBLE);
        TextView textViewWachtwoord = (TextView) findViewById(id.wachtwoordtest);
        textViewWachtwoord.setVisibility(View.INVISIBLE);
        Button inlogKnop = (Button) findViewById(id.loginButton);
        inlogKnop.setVisibility(View.INVISIBLE);
        TextView textViewWachtwoordInstellen = (TextView) findViewById(id.wachtwoordInstellen);
        textViewWachtwoordInstellen.setVisibility(View.INVISIBLE);
        MaterialTextField materialTextFieldGebruikersnaam = (MaterialTextField) findViewById(id.materialtextfieldgebruikersnaam);
        materialTextFieldGebruikersnaam.setVisibility(View.INVISIBLE);
        MaterialTextField materialTextFieldWachtwoord = (MaterialTextField) findViewById(id.materialtextfieldwachtwoord);
        materialTextFieldWachtwoord.setVisibility(View.INVISIBLE);
    }
    /*public void progressOnzichtbaar(){
        ProgressBar progressBar = (ProgressBar) findViewById(id.login_laden);
        progressBar.setVisibility(View.INVISIBLE);
    }*/
}
