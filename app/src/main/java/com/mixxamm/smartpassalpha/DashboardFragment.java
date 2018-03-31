package com.mixxamm.smartpassalpha;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

import static com.mixxamm.smartpassalpha.InstellingenFragment.PREFS_ALGEMEEN;
import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

public class DashboardFragment extends Fragment {

    public static int aantalTeLaat, aantalTeLaatTrimester, intAantalTotNablijven;
    public static View v;
    public static TextView aantalKerenTeLaat, aantalKerenTeLaatTrimester, aantalTotNablijven, aantalKerenTeLaatTekst, aantalKerenTeLaatTrimesterTekst;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_dashboard, container, false);


        //Voorkeuren
        SharedPreferences algemeen = getActivity().getSharedPreferences(PREFS_ALGEMEEN, 0);
        boolean donker = algemeen.getBoolean("donkereModus", false);
        SharedPreferences account = getActivity().getSharedPreferences(ACCOUNT, 0);
        String id = account.getString("id", "");

        aantalKerenTeLaat = v.findViewById(R.id.aantal_keren_te_laat);
        aantalKerenTeLaatTrimester = v.findViewById(R.id.aantal_keren_te_laat_trimester);
        aantalTotNablijven = v.findViewById(R.id.aantal_tot_nablijven);
        aantalKerenTeLaatTekst = v.findViewById(R.id.aantal_keren_te_laat_tekst);
        aantalKerenTeLaatTrimesterTekst = v.findViewById(R.id.aantal_keren_te_laat_trimester_tekst);

        if(donker){
            int color = Color.parseColor("#000000");
            int color2 = Color.parseColor("#000000");
            setActivityBackgroundColor(color, color2);
            aantalKerenTeLaat.setTextColor(Color.WHITE);
            aantalKerenTeLaatTrimester.setTextColor(Color.WHITE);
            aantalTotNablijven.setTextColor(Color.WHITE);
            aantalKerenTeLaatTekst.setTextColor(Color.WHITE);
            aantalKerenTeLaatTrimesterTekst.setTextColor(Color.WHITE);
            BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
            navigation.setBackgroundColor(Color.parseColor("#000000"));

            int[][] states = new int[][] {
                    new int[] {-android.R.attr.state_checked}, // unchecked
                    new int[] { android.R.attr.state_checked}, // checked

            };

            int[] colors = new int[] {
                    Color.WHITE,
                    Color.parseColor("#2196F3")
            };

            ColorStateList donkerLijst = new ColorStateList(states, colors);
            navigation.setItemTextColor(donkerLijst);
            navigation.setItemIconTintList(donkerLijst);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#000000"));
            }
        }
        else {
            BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
            navigation.setBackgroundColor(Color.parseColor("#FAFAFA"));

            int[][] states = new int[][] {
                    new int[] {-android.R.attr.state_checked}, // unchecked
                    new int[] { android.R.attr.state_checked}, // checked

            };

            int[] colors = new int[] {
                    Color.parseColor("#757575"),
                    Color.parseColor("#455A64")
            };

            ColorStateList lichtLijst = new ColorStateList(states, colors);
            navigation.setItemTextColor(lichtLijst);
            navigation.setItemIconTintList(lichtLijst);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#455A64"));
            }
        }
        Login login = new Login(getContext());
        login.execute("dashboard", id);

        return v;
    }

    public void laden(){
        if(aantalTeLaat == 0 && aantalTeLaatTrimester == 0 && intAantalTotNablijven == 0){
            aantalKerenTeLaat.setText("?");
            aantalKerenTeLaatTrimester.setText("?");
        }
        else{
            aantalKerenTeLaat.setText(String.valueOf(aantalTeLaat));
            aantalKerenTeLaatTrimester.setText(String.valueOf(aantalTeLaatTrimester));
            aantalTotNablijven.setText("Nog " + String.valueOf(intAantalTotNablijven) + " keer te laat tot nablijven");
        }
    }

    public void setActivityBackgroundColor(int color, int color2) {
        LinearLayout linearLayout = v.findViewById(R.id.dashboard_fragment);
        linearLayout.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color2);
        }
    }
}
