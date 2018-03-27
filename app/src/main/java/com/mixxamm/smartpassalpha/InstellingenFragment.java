package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

/**
 * Created by janssensm on 27-3-2018.
 */

public class InstellingenFragment extends Fragment implements View.OnClickListener {

    //Voorkeuren
    public static final String PREFS_ALGEMEEN = "Algemeen";
    public static View v;
    static String naamGebruiker;
    Switch donkereModus;
    ImageView uitloggen, donkereModusImageView, bugReportImageView, ondersteuningImageView, nieuwWachtwoordImageView;
    TextView bugReport, ondersteuning, nieuwWachtwoordTextView;
    LinearLayout nieuwWachtwoord, testVersie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_instellingen, container, false);
        SharedPreferences account = getActivity().getSharedPreferences(ACCOUNT, 0);
        naamGebruiker = account.getString("naamGebruiker", "");

        bugReport = v.findViewById(R.id.bugReport);
        bugReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/mixxamm/SmartpassAndroid/issues";
                Intent link = new Intent(Intent.ACTION_VIEW);
                link.setData(Uri.parse(url));
                startActivity(link);
            }
        });

        ondersteuning = v.findViewById(R.id.ondersteuning);
        ondersteuning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {composeEmail();
            }
        });

        nieuwWachtwoord = v.findViewById(R.id.nieuwWachtwoordLinearLayout);
        nieuwWachtwoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WachtwoordInstellen wachtwoordInstellen = new WachtwoordInstellen();
                wachtwoordInstellen.type = "login";
                Intent intent = new Intent(getContext(), WachtwoordInstellen.class);
                intent.putExtra("gebruikersnaam", naamGebruiker);
                startActivity(intent);
            }
        });

        TextView uitLogKnop = v.findViewById(R.id.uitLogKnop);
        uitLogKnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLeerlingNaam();
                Intent main = new Intent(getContext(), MainActivity.class);
                startActivity(main);
                getActivity().finish();
            }
        });

        uitloggen = v.findViewById(R.id.uitloggen);
        donkereModus = v.findViewById(R.id.donkereModus);
        donkereModusImageView = v.findViewById(R.id.donkereModusImageView);
        bugReportImageView = v.findViewById(R.id.bugReportImageView);
        ondersteuningImageView = v.findViewById(R.id.ondersteuningImageView);
        nieuwWachtwoordImageView = v.findViewById(R.id.nieuwWachtwoordImageView);
        nieuwWachtwoordTextView = v.findViewById(R.id.nieuwWachtwoordTextView);

        //Voorkeuren
        SharedPreferences algemeen = getActivity().getSharedPreferences(PREFS_ALGEMEEN, 0);
        boolean donker = algemeen.getBoolean("donkereModus", false);

        if(donker){
            donkereModus.setChecked(true);
            int color = Color.parseColor("#000000");
            int color2 = Color.parseColor("#000000");
            setActivityBackgroundColor(color, color2);
            donkereModus.setTextColor(Color.WHITE);
            uitLogKnop.setTextColor(Color.WHITE);
            uitloggen.setColorFilter(Color.WHITE);
            donkereModusImageView.setColorFilter(Color.WHITE);
            bugReport.setTextColor(Color.WHITE);
            bugReportImageView.setColorFilter(Color.WHITE);
            ondersteuning.setTextColor(Color.WHITE);
            ondersteuningImageView.setColorFilter(Color.WHITE);
            nieuwWachtwoordImageView.setColorFilter(Color.WHITE);
            nieuwWachtwoordTextView.setTextColor(Color.WHITE);
        }
        donkereModus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean donker = donkereModus.isChecked();
                if(donker) {
                    SharedPreferences algemeen = getActivity().getSharedPreferences(PREFS_ALGEMEEN, 0);
                    SharedPreferences.Editor editor = algemeen.edit();
                    editor.putBoolean("donkereModus", true);
                    editor.commit();
                }
            else{
                SharedPreferences algemeen = getActivity().getSharedPreferences(PREFS_ALGEMEEN, 0);
                SharedPreferences.Editor editor = algemeen.edit();
                editor.putBoolean("donkereModus", false);
                editor.commit();
            }
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(InstellingenFragment.this).attach(InstellingenFragment.this).commit();
        }
        });

        return v;
    }


    @Override
    public void onClick(View view) {

    }

    public void resetLeerlingNaam(){
        SharedPreferences account = getActivity().getSharedPreferences(ACCOUNT, 0);
        SharedPreferences.Editor editor = account.edit();
        editor.putString("naamGebruiker", "");//Aangezien bij het inloggen enkel op de naam wordt gecontroleerd, hoeven we het wachtwoord niet te resetten
        editor.commit();//Dit voert de wijzigingen door
    }
    public void setActivityBackgroundColor(int color, int color2) {
        RelativeLayout relativeLayout = v.findViewById(R.id.instellingenfragment);
        relativeLayout.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color2);
        }
    }
    public void composeEmail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "support.android@smartpass.one" });
        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(intent);
        }
}}
