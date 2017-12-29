package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;


import static com.mixxamm.smartpassalpha.R.id.smartschool_login;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_INTRODUCTIE = "Introductie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*VideoView videoView = (VideoView) findViewById(R.id.videoView1);
        Uri path = Uri.parse("android.recourse://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(path);
        videoView.start();*/

        SharedPreferences introductie = getSharedPreferences(PREFS_INTRODUCTIE, 0);
        boolean booleanIntroductie = introductie.getBoolean("introductie", false);

        if (!booleanIntroductie) {
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
                Intent login = new Intent(view.getContext(), LoginTest.class);
                startActivity(login);
            }
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
}
