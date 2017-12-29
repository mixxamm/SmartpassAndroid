package com.mixxamm.smartpassalpha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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

        if(naam.equals("Leerling niet gevonden")){
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
        }

        Button logUitKnop = (Button) findViewById(R.id.logUitKnop);
        logUitKnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences naamGebruiker = getSharedPreferences("NaamGebruiker", 0);
                SharedPreferences.Editor editor = naamGebruiker.edit();
                editor.putString("naamGebruiker", "");
                editor.commit();
                Intent main = new Intent(LeerlingenKaartActivity.this, MainActivity.class);
                startActivity(main);
            }
        });

        naamLeerling = (TextView) findViewById(R.id.leerlingNaam);
        naamLeerling.setText(naam);
        profielFoto = (CircleImageView) findViewById(R.id.profielFoto);
        Picasso.with(this).load(fotoURL).into(profielFoto);


        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(String.valueOf(id), BarcodeFormat.QR_CODE, 512, 512);
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
        editor.putString("naamGebruiker", "");
        editor.commit();
    }
}

