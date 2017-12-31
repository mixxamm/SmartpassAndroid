package com.mixxamm.smartpassalpha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToonLeerlingInfo extends AppCompatActivity {

    public static String fotoURL, naam, buiten;
    CircleImageView leerlingFoto;
    TextView leerlingNaam;
    ImageView naarBuiten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toon_leerling_info);
        leerlingFoto = (CircleImageView) findViewById(R.id.profielFotoScan);
        Picasso.with(ToonLeerlingInfo.this).load(fotoURL).into(leerlingFoto);
        leerlingNaam = (TextView) findViewById(R.id.info);
        leerlingNaam.setText(naam);
        ImageView magBuiten = (ImageView) findViewById(R.id.magBuiten);
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
