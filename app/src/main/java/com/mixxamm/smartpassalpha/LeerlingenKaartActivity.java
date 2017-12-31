package com.mixxamm.smartpassalpha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import java.net.InetAddress;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeerlingenKaartActivity extends AppCompatActivity {

    public final static int QRcodeWidth = 500;
    int color1;//Wordt momenteel enkel gebruikt om achtergrondkleur QR-code te veranderen. TODO: alle kleuren die tegelijk hiermee moeten veranderen hieraan koppelen zodat er minder code is, en dat het makkelijker aan te passen is als de kleuren ooit veranderen
    public static String id, naam, fotoURL, buiten;
    ImageView imageView;
    TextView naamLeerling;
    CircleImageView profielFoto;//Variabele profielFoto maken
    Thread thread;
    Bitmap bitmap;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leerlingen_kaart);


        if (!isNetworkAvailable()) {
            SharedPreferences id1 = getSharedPreferences("id", 0);
            id = id1.getString("id", "");
            SharedPreferences naamGebruiker = getSharedPreferences("NaamGebruiker", 0);
            naam = naamGebruiker.getString("naamGebruiker", "");
            buiten = "4";
        }

        if(naam.equals("Leerling niet gevonden")){//log automatisch uit als account niet bestaat
            resetLeerlingNaam();
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        ImageView imageViewBuiten = (ImageView) findViewById(R.id.imageViewBuiten);
        CircleImageView profielFotoView = (CircleImageView) findViewById(R.id.profielFoto);
        if (buiten.equals("1")) {
            setActivityBackgroundColor(Color.parseColor("#8BC34A"), Color.parseColor("#689F38"));//parseColor gebruiken aangezien kleuren van colors.xml pakken niet werkt om een vage reden
            color1 = Color.parseColor("#8BC34A");
        } else if (buiten.equals("0")) {
            setActivityBackgroundColor(Color.parseColor("#F44336"), Color.parseColor("#D32F2F"));
            color1 = Color.parseColor("#F44336");
        } else if (buiten.equals("3")) {
            imageViewBuiten.setImageResource(R.drawable.alert_circle);
            imageViewBuiten.setVisibility(View.VISIBLE);
            profielFotoView.setVisibility(View.INVISIBLE);
            color1 = Color.WHITE;
        } else if(buiten.equals("4")){
            imageViewBuiten.setImageResource(R.drawable.sync_alert);
            imageViewBuiten.setVisibility(View.VISIBLE);
            profielFotoView.setVisibility(View.INVISIBLE);
            color1 = Color.WHITE;
        }

        Button logUitKnop = (Button) findViewById(R.id.logUitKnop);
        logUitKnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLeerlingNaam();
                Intent main = new Intent(LeerlingenKaartActivity.this, MainActivity.class);
                startActivity(main);//Teruggaan naar hoofdactiviteit
                finish();
            }
        });

        naamLeerling = (TextView) findViewById(R.id.leerlingNaam);
        naamLeerling.setText(naam);
        profielFoto = (CircleImageView) findViewById(R.id.profielFoto);
        Picasso.with(this).load(fotoURL).into(profielFoto);


        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(id, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : color1);
                }
            }
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void setActivityBackgroundColor(int color, int color2) {
        View layout = new View(getBaseContext());
        layout = (View) findViewById(R.id.leerlingenKaartLayout);
        layout.setBackgroundColor(color);
        Button logUitKnop = (Button) findViewById(R.id.logUitKnop);
        logUitKnop.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color2);
        }
    }

    public void resetLeerlingNaam(){
        SharedPreferences naamGebruiker = getSharedPreferences("NaamGebruiker", 0);
        SharedPreferences.Editor editor = naamGebruiker.edit();
        editor.putString("naamGebruiker", "");//Aangezien bij het inloggen enkel op de naam wordt gecontroleerd, hoeven we het wachtwoord niet te resetten
        editor.commit();//Dit voert de wijzigingen door
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    }


