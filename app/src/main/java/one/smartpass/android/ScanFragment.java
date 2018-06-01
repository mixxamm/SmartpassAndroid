package one.smartpass.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mixxamm.smartpassalpha.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScanFragment extends Fragment {

    public static String fotoURL, naam, buiten = "2", id, klas;
    public CircleImageView leerlingFoto;
    private static TextView leerlingNaam;
    public static Button teLaat;
    public static View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_scan, container, false);


        teLaat = v.findViewById(R.id.telaatknop);
        SharedPreferences account = getActivity().getSharedPreferences(MainActivity.ACCOUNT, 0);
        final String naamLeerkracht = account.getString("naamLeerkracht", "");
        teLaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "zetTeLaat";

                if ("Leerling niet gevonden".equals(naam)) {
                    RelativeLayout scanActivity = getActivity().findViewById(R.id.scanActivityLayout);
                    Snackbar snackbar = Snackbar.make(scanActivity, naam, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (id != null) {//Voorkomt crash indien er geen leerling is gescant
                    SharedPreferences account = getContext().getSharedPreferences(MainActivity.ACCOUNT, 0);
                    String logintoken = account.getString("token", "");
                    Login login = new Login(getContext());
                    login.execute(type, id, logintoken, naamLeerkracht, "sa");
                }
            }
        });
        return v;
    }

    public void setActivityBackgroundColor(int color, int color2) {
        LinearLayout layout;
        layout = v.findViewById(R.id.scanLayout);
        layout.setBackgroundColor(color);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color2);
        }*/
    }

    public void laden() {
        leerlingNaam = v.findViewById(R.id.info);
        teLaat = v.findViewById(R.id.telaatknop);
        leerlingFoto = v.findViewById(R.id.profielFotoScan);
        if ("Leerling gebruikt outdated versie Smartpass".equals(naam)) {
            leerlingFoto.setImageResource(R.drawable.alert_circle);
            leerlingNaam.setText(naam);
            teLaat.setVisibility(View.INVISIBLE);
        } else {
            Picasso.with(v.getContext()).load(fotoURL).into(leerlingFoto);
            leerlingNaam.setText(naam + " | " + klas);//TODO: helemaal niet de juiste manier om dit te doen, maar ik was te moe dus moet nog eens gefixt worden.
            if ("1".equals(buiten)) {
                setActivityBackgroundColor(Color.parseColor("#8BC34A"), Color.parseColor("#689F38"));
                teLaat.setBackgroundColor(Color.parseColor("#8BC34A"));
            } else if ("0".equals(buiten)) {
                setActivityBackgroundColor(Color.parseColor("#F44336"), Color.parseColor("#D32F2F"));
                teLaat.setBackgroundColor(Color.parseColor("#F44336"));
            } else if ("Leerling niet gevonden".equals(naam)) {
                leerlingNaam.setText(naam);
                teLaat.setVisibility(View.INVISIBLE);
            }
        }
    }
}
