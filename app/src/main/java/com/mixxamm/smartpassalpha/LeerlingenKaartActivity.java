package com.mixxamm.smartpassalpha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
import static android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

public class LeerlingenKaartActivity extends AppCompatActivity {//TODO: heel belangrijk: loaders gebruiken, zo blijft data behouden bij draaien scherm

    public final static int QRcodeWidth = 500;
    int color1;//Wordt momenteel enkel gebruikt om achtergrondkleur QR-code te veranderen. TODO: alle kleuren die tegelijk hiermee moeten veranderen hieraan koppelen zodat er minder code is, en dat het makkelijker aan te passen is als de kleuren ooit veranderen
    public static String id, naam, fotoURL, buiten;
    ImageView imageView;
    TextView naamLeerling;
    CircleImageView profielFoto;//Variabele profielFoto maken
    Thread thread;
    Bitmap bitmap;


    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leerlingen_kaart);
        setScreenBrightnessTo(BRIGHTNESS_OVERRIDE_FULL);


        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_leerlingenkaart);

        // Maak items
        AHBottomNavigationItem betalen = new AHBottomNavigationItem("Betalen", R.drawable.betalen, R.color.colorAccent);
        AHBottomNavigationItem leerlingenkaart = new AHBottomNavigationItem("Kaart", R.drawable.account1, R.color.colorAccent);
        AHBottomNavigationItem instellingen = new AHBottomNavigationItem("Instellingen", R.drawable.instellingen, R.color.colorAccent);

        // Items toevoegen
        bottomNavigation.addItem(betalen);
        bottomNavigation.addItem(leerlingenkaart);
        bottomNavigation.addItem(instellingen);

        bottomNavigation.setCurrentItem(1);

        //Listeners maken
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position){
                    case 0:

                        Intent betalen = new Intent(LeerlingenKaartActivity.this, Betalen.class);
                        startActivity(betalen);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case 2:
                        Intent instellingen = new Intent(LeerlingenKaartActivity.this, Instellingen.class);
                        startActivity(instellingen);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return true;
            }
        });

        if (!isNetworkAvailable()) {
            SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
            naam = account.getString("naamGebruiker", "");
            buiten = "4";
        }

        if(naam.equals("Leerling niet gevonden")){//log automatisch uit als account1 niet bestaat
            resetLeerlingNaam();
        }

        imageView = (ImageView) findViewById(R.id.qrcode);
        CircleImageView profielFotoView = (CircleImageView) findViewById(R.id.profielFoto);
        if (buiten.equals("1")) {
            setActivityBackgroundColor(Color.parseColor("#8BC34A"), Color.parseColor("#689F38"));//parseColor gebruiken aangezien kleuren van colors.xml pakken niet werkt om een vage reden

            color1 = Color.parseColor("#8BC34A");
            int color2 = Color.parseColor("#689F38");
            bottomNavigation.setDefaultBackgroundColor(color1);
            bottomNavigation.setAccentColor(Color.parseColor("#009688"));
            bottomNavigation.setInactiveColor(Color.parseColor("#455A64"));
            if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                getWindow().setNavigationBarColor(Color.parseColor("#689F38"));
            }
        } else if (buiten.equals("0")) {
            setActivityBackgroundColor(Color.parseColor("#F44336"), Color.parseColor("#D32F2F"));
            color1 = Color.parseColor("#F44336");
            bottomNavigation.setDefaultBackgroundColor(color1);
            bottomNavigation.setAccentColor(Color.parseColor("#212121"));
            bottomNavigation.setInactiveColor(Color.parseColor("#424242"));
            if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                getWindow().setNavigationBarColor(Color.parseColor("#D32F2F"));
            }
        } else if (buiten.equals("3")) {
            profielFotoView.setImageResource(R.drawable.alert_circle);
            profielFotoView.setVisibility(View.VISIBLE);
            profielFotoView.setVisibility(View.INVISIBLE);
            color1 = Color.WHITE;
        } else if(buiten.equals("4")){
            profielFotoView.setImageResource(R.drawable.sync_alert);
            profielFotoView.setVisibility(View.VISIBLE);
            color1 = Color.WHITE;
        }


        naamLeerling = (TextView) findViewById(R.id.leerlingNaam);
        naamLeerling.setText(naam);
        if(buiten.equals("1") || buiten.equals("0")){
            Picasso.with(this).load(fotoURL).into(profielFotoView);
        }



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
            ((ImageView) findViewById(R.id.qrcode)).setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        //TODO:de versie hieronder werkt een pak sneller, zorgen dat achtergrondkleur in orde geraakt
        /*try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.QR_CODE,600,600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);



            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }*/
    }

    public void setActivityBackgroundColor(int color, int color2) {
        View layout;
        layout = findViewById(R.id.leerlingenKaartLayout);
        layout.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color2);
        }
    }

    public void resetLeerlingNaam(){
        SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
        SharedPreferences.Editor editor = account.edit();
        editor.putString("naamGebruiker", "");//Aangezien bij het inloggen enkel op de naam wordt gecontroleerd, hoeven we het wachtwoord niet te resetten
        editor.commit();//Dit voert de wijzigingen door
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void setScreenBrightnessTo(float brightness) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        if (lp.screenBrightness == brightness) {
            return;
        }

        lp.screenBrightness = brightness;
        getActivity().getWindow().setAttributes(lp);
    }


        protected void onDestroy(){
        super.onDestroy();
            setScreenBrightnessTo(BRIGHTNESS_OVERRIDE_NONE);


    }

    public Activity getActivity() {
        return LeerlingenKaartActivity.this;
    }
}



