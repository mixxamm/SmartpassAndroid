package com.mixxamm.smartpassalpha;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

public class LeerlingenKaartFragment extends Fragment {

    public final static int QRcodeWidth = 500;
    int color1;
    public static String id, naam, fotoURL, buiten;
    public static View v;
    ImageView imageView;
    TextView naamLeerling;
    CircleImageView profielFoto;
    Thread thread;
    Bitmap bitmap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_leerlingenkaart, container, false);

        if(!isNetworkAvailable()){
            SharedPreferences account = getActivity().getSharedPreferences(ACCOUNT, 0);
            naam = account.getString("naamGebruiker", "");
            buiten = "4";
        }

        if(naam.equals("Leerling niet gevonden")){//log automatisch uit als account1 niet bestaat
            resetLeerlingNaam();
        }

        imageView = (ImageView) v.findViewById(R.id.qrcode1);
        CircleImageView profielFotoView = (CircleImageView) v.findViewById(R.id.profielFoto);
        if (buiten.equals("1")) {
            setActivityBackgroundColor(Color.parseColor("#8BC34A"), Color.parseColor("#689F38"));//parseColor gebruiken aangezien kleuren van colors.xml pakken niet werkt om een vage reden


            color1 = Color.parseColor("#8BC34A");
            int color2 = Color.parseColor("#689F38");
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setNavigationBarColor(Color.parseColor("#689F38"));
            }
        } else if (buiten.equals("0")) {
            setActivityBackgroundColor(Color.parseColor("#F44336"), Color.parseColor("#D32F2F"));
            color1 = Color.parseColor("#F44336");
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setNavigationBarColor(Color.parseColor("#D32F2F"));
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


        naamLeerling = (TextView) v.findViewById(R.id.leerlingNaam);
        naamLeerling.setText(naam);
        if(buiten.equals("1") || buiten.equals("0")){
            Picasso.with(getContext()).load(fotoURL).into(profielFotoView);
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
            ((ImageView) v.findViewById(R.id.qrcode1)).setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


        return v;
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void setActivityBackgroundColor(int color, int color2) {
        RelativeLayout relativeLayout = v.findViewById(R.id.fragmentleerlingenkaart);
        relativeLayout.setBackgroundColor(color);

        BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        navigation.setBackgroundColor(color);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color2);
        }
    }
    public void resetLeerlingNaam(){
        SharedPreferences account = getActivity().getSharedPreferences(ACCOUNT, 0);
        SharedPreferences.Editor editor = account.edit();
        editor.putString("naamGebruiker", "");//Aangezien bij het inloggen enkel op de naam wordt gecontroleerd, hoeven we het wachtwoord niet te resetten
        editor.commit();//Dit voert de wijzigingen door
    }
}
