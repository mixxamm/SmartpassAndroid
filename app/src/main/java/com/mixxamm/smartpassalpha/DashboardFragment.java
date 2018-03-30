package com.mixxamm.smartpassalpha;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;

public class DashboardFragment extends Fragment {

    public static int aantalTeLaat, aantalTeLaatTrimester, intAantalTotNablijven;
    public static View v;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_dashboard, container, false);


        SharedPreferences account = getActivity().getSharedPreferences(ACCOUNT, 0);
        String id = account.getString("id", "");
        Login login = new Login(getContext());
        login.execute("dashboard", id);

        BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        navigation.setBackgroundColor(Color.parseColor("#FAFAFA"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#455A64"));
        }
        return v;
    }

    public void laden(){
        TextView aantalKerenTeLaat = v.findViewById(R.id.aantal_keren_te_laat);
        TextView aantalKerenTeLaatTrimester = v.findViewById(R.id.aantal_keren_te_laat_trimester);
        TextView aantalTotNablijven = v.findViewById(R.id.aantal_tot_nablijven);
        aantalKerenTeLaat.setText(String.valueOf(aantalTeLaat));
        aantalKerenTeLaatTrimester.setText(String.valueOf(aantalTeLaatTrimester));
        aantalTotNablijven.setText("Nog " + String.valueOf(intAantalTotNablijven) + " keer te laat tot nablijven");

    }
}
