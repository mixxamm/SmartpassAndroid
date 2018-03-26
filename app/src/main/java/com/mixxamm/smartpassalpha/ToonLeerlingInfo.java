package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mixxamm.smartpassalpha.LeerkrachtenActivity.REQUEST_CODE;
import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

public class ToonLeerlingInfo extends AppCompatActivity {

    public static String fotoURL, naam, buiten, id;
    CircleImageView leerlingFoto;
    TextView leerlingNaam;
    public static ImageView magBuiten;
    Button teLaat, stuurBericht, stopScannen, nieuweScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toon_leerling_info);
        leerlingFoto = (CircleImageView) findViewById(R.id.profielFotoScan);
        Picasso.with(ToonLeerlingInfo.this).load(fotoURL).into(leerlingFoto);
        leerlingNaam = (TextView) findViewById(R.id.info);
        leerlingNaam.setText(naam);
        teLaat = (Button) findViewById(R.id.telaatknop);
        stuurBericht = (Button) findViewById(R.id.stuurBericht);
        magBuiten = (ImageView) findViewById(R.id.magBuiten);
        stopScannen = findViewById(R.id.stopScannen);
        nieuweScan = findViewById(R.id.nieuweScan);

        SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
        final String wachtwoordGebruiker1 = account.getString("wachtwoordGebruiker", "");
        final String naamLeerkracht = account.getString("naamLeerkracht", "");

        teLaat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String type = "zetTeLaat";
                    Login login = new Login(ToonLeerlingInfo.this);
                    login.execute(type, id, wachtwoordGebruiker1, naamLeerkracht, "tli");

            }
        });

        stuurBericht.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent mail = new Intent(ToonLeerlingInfo.this, Mail.class);
                startActivity(mail);
            }
        });

        stopScannen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leerkrachtActivity = new Intent(ToonLeerlingInfo.this, LeerkrachtenActivity.class);
                startActivity(leerkrachtActivity);
                finish();
            }
        });

        nieuweScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leerkrachtenActivity = new Intent(ToonLeerlingInfo.this, LeerkrachtenActivity.class);
                leerkrachtenActivity.putExtra("type", "scan");
                startActivity(leerkrachtenActivity);
                finish();
            }
        });

        if (buiten.equals("1")) {
            magBuiten.setImageResource(R.drawable.ic_check_circle_black_48dp);
        } else if (buiten.equals("0")) {
            magBuiten.setImageResource(R.drawable.ic_cancel_black_48dp);
        } else if (buiten.equals("3")) {
            magBuiten.setImageResource(R.drawable.alert_circle);
        } else {
            magBuiten.setImageResource(R.drawable.sync_alert);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LeerkrachtenActivity.progressBar.setVisibility(View.INVISIBLE);//TODO: BUG: op sommige smartphones gaat de progressbar niet weg
    }
}
