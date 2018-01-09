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
import android.widget.Toast;

import static com.mixxamm.smartpassalpha.R.id.smartschool_login;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_INTRODUCTIE = "Introductie";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences account = getSharedPreferences("NaamGebruiker", 0);
        String naamGebruiker = account.getString("naamGebruiker", "");
        SharedPreferences wachtwoordGebruiker = getSharedPreferences("WachtwoordGebruiker", 0);
        String wachtwoordGebruiker1 = wachtwoordGebruiker.getString("wachtwoordGebruiker", "");
        if(isNetworkAvailable() && naamGebruiker != ""){
            laden();
            Login login = new Login(MainActivity.this);
            login.execute("login", naamGebruiker, wachtwoordGebruiker1);
        }
        else if (!isNetworkAvailable() && naamGebruiker != "") {
            laden();
            Intent leerlingenKaart = new Intent(MainActivity.this, LeerlingenKaartActivity.class);
            SharedPreferences id3 = getSharedPreferences("id", 0);
            id = id3.getString("id", "");
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
        }


        ImageView smartschoolLogin = (ImageView) findViewById(smartschool_login);
        smartschoolLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    laden();
                    Intent login = new Intent(view.getContext(), LoginTest.class);
                    startActivity(login);
                    finish();}
        });
        Button leerkrachtLogin = (Button) findViewById(R.id.leerkrachtLogin);
        leerkrachtLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent leerkrachtenActivity = new Intent(view.getContext(), LeerkrachtenActivity.class);
                startActivity(leerkrachtenActivity);
            }

            ;
        });
    }
    private void laden(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.ProgressBarMainActivity);
        progressBar.setVisibility(View.VISIBLE);
        ImageView smartschoolLogin = (ImageView) findViewById(R.id.smartschool_login);
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
