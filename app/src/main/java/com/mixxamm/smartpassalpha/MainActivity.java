package com.mixxamm.smartpassalpha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.mixxamm.smartpassalpha.R.id.leerling_login;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_INTRODUCTIE = "Introductie";
    public static final String ACCOUNT = "Account";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
        String naamGebruiker = account.getString("naamGebruiker", "");
        String wachtwoordGebruiker1 = account.getString("wachtwoordGebruiker", "");
        String naamLeerkracht = account.getString("naamLeerkracht", "");
        if(isNetworkAvailable() && naamGebruiker != ""){
            laden();
            Login login = new Login(MainActivity.this);
            login.execute("login", naamGebruiker, wachtwoordGebruiker1);
        }
        else if(isNetworkAvailable() && naamLeerkracht != ""){
            laden();
            Login login = new Login(MainActivity.this);
            login.execute("loginLeerkracht", naamLeerkracht, wachtwoordGebruiker1);
        }
        else if(!isNetworkAvailable() && naamLeerkracht != ""){
            Toast.makeText(this, "Maak verbinding met internet om automatisch in te loggen.", Toast.LENGTH_SHORT).show();
        }
        else if (!isNetworkAvailable() && naamGebruiker != "") {
            laden();
            Intent leerlingenKaart = new Intent(MainActivity.this, LeerlingenKaartActivity.class);
            id = account.getString("id", "");
            LeerlingenKaartActivity.id = id;
            startActivity(leerlingenKaart);
        }
        /*VideoView videoView = (VideoView) findViewById(R.id.videoView1);
        Uri path = Uri.parse("android.recourse://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(path);
        videoView.start();*/

        SharedPreferences introductie = getSharedPreferences(PREFS_INTRODUCTIE, 0);
        boolean booleanIntroductie = introductie.getBoolean("introductie", false);

        if (!booleanIntroductie) {//Kijkt na of introductie al bekeken is
            SharedPreferences introductie1 = getSharedPreferences(PREFS_INTRODUCTIE, 0);
            SharedPreferences.Editor editor = introductie1.edit();
            editor.putBoolean("introductie", true);//Zo weet de app dat de gebruiker de introductie al heeft gezien
            editor.commit();
            Intent Introductie = new Intent(MainActivity.this, com.mixxamm.smartpassalpha.Introductie.class);
            startActivity(Introductie);
            finish();
        }


        TextView smartschoolLogin = findViewById(leerling_login);
        smartschoolLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    laden();
                    LoginTest.type = "login";//Zorgt ervoor dat de klasse login weet dat we willen inloggen. (In de toekomst kunnen we nog andere functies toevoegen)
                    Intent login = new Intent(view.getContext(), LoginTest.class);
                    startActivity(login);
                    finish();}
        });
        Button leerkrachtLogin = (Button) findViewById(R.id.leerkrachtLogin);
        leerkrachtLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginTest.type = "loginLeerkracht";
                Intent login = new Intent(view.getContext(), LoginTest.class);
                startActivity(login);
                finish();
            }

            ;
        });
    }
    private void laden(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.ProgressBarMainActivity);
        progressBar.setVisibility(View.VISIBLE);
        TextView smartschoolLogin = (TextView) findViewById(R.id.leerling_login);
        smartschoolLogin.setVisibility(View.INVISIBLE);
        Button leerkrachtLogin = (Button) findViewById(R.id.leerkrachtLogin);
        leerkrachtLogin.setVisibility(View.INVISIBLE);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
