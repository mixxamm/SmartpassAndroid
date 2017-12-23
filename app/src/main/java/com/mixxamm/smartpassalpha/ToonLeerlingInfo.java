package com.mixxamm.smartpassalpha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToonLeerlingInfo extends AppCompatActivity {

    CircleImageView leerlingFoto;
    TextView leerlingNaam;
    ImageView naarBuiten;
    public static String fotoURL, naam, buiten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toon_leerling_info);
        leerlingFoto = (CircleImageView) findViewById(R.id.profielFotoScan);
        Picasso.with(ToonLeerlingInfo.this).load(fotoURL).into(leerlingFoto);
        leerlingNaam = (TextView) findViewById(R.id.info);
        leerlingNaam.setText(naam);


    }
}
