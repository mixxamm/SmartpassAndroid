package com.mixxamm.smartpassalpha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeerlingenKaartActivity extends AppCompatActivity {

    ImageView imageView;
    TextView naamLeerling;
    CircleImageView profielFoto;//Variabele profielFoto maken
    Thread thread;
    public final static int QRcodeWidth = 500;
    Bitmap bitmap;
    public static String id;
    public static String naam;
    public static String fotoURL;
    public static String buiten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leerlingen_kaart);

        imageView = (ImageView)findViewById(R.id.imageView);
        ImageView imageViewBuiten = (ImageView) findViewById(R.id.imageViewBuiten);
        if(buiten.equals("1")){
            imageViewBuiten.setImageResource(R.drawable.ic_check_circle_black_48dp);
        }
        else if(buiten.equals("0")){
            imageViewBuiten.setImageResource(R.drawable.ic_cancel_black_48dp);
        }
        else if(buiten.equals("3")){
            imageViewBuiten.setImageResource(R.drawable.alert_circle);
        }

        naamLeerling = (TextView)findViewById(R.id.leerlingNaam);
        naamLeerling.setText(naam);
        profielFoto = (CircleImageView)findViewById(R.id.profielFoto);
        Picasso.with(this).load(fotoURL).into(profielFoto);


        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(String.valueOf(id), BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for(int x = 0; x<width;x++){
                for(int y = 0;y < height; y++){
                    bitmap.setPixel(x, y, bitMatrix.get(x,y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);

        } catch(WriterException e) {
        e.printStackTrace();
        }
    }



}
