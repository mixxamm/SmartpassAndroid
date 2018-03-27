package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

/**
 * Created by janssensm on 27-3-2018.
 */

public class InstellingenFragment extends Fragment implements View.OnClickListener {

    //Voorkeuren
    public static final String PREFS_ALGEMEEN = "Algemeen";
    static String naamGebruiker;
    Switch donkereModus;
    ImageView uitloggen, donkereModusImageView, bugReportImageView, ondersteuningImageView, nieuwWachtwoordImageView;
    TextView bugReport, ondersteuning, nieuwWachtwoordTextView;
    LinearLayout nieuwWachtwoord, testVersie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_instellingen, container, false);
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



        return v;
    }


    @Override
    public void onClick(View view) {

    }
}
