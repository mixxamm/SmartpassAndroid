package one.smartpass.android;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mixxamm.smartpassalpha.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScanFragment extends Fragment {

    public static String fotoURL, naam, buiten = "2", id, klas;
    CircleImageView leerlingFoto;
    TextView leerlingNaam;
    public static ImageView magBuiten;
    public static Button teLaat;
    public static View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_scan, container, false);




        teLaat = v.findViewById(R.id.telaatknop);
        SharedPreferences account = getActivity().getSharedPreferences(MainActivity.ACCOUNT, 0);
        final String wachtwoordGebruiker1 = account.getString("wachtwoordGebruiker", "");
        final String naamLeerkracht = account.getString("naamLeerkracht", "");
        teLaat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String type = "zetTeLaat";

                if(naam.equals("Leerling niet gevonden")){
                    Toast.makeText(getContext(), "Scan de QR-code van een leerling om deze te laat te zetten.", Toast.LENGTH_SHORT).show();
                }
                else if(id != null){//Voorkomt crash indien er geen leerling is gescant
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
    public void laden(){
        leerlingFoto = (CircleImageView) v.findViewById(R.id.profielFotoScan);
        Picasso.with(v.getContext()).load(fotoURL).into(leerlingFoto);
        leerlingNaam = (TextView) v.findViewById(R.id.info);
        leerlingNaam.setText(naam + " | " + klas);
        teLaat = (Button) v.findViewById(R.id.telaatknop);
        if(buiten.equals("1")){
            setActivityBackgroundColor(Color.parseColor("#8BC34A"), Color.parseColor("#689F38"));
        }
        else if(buiten.equals("0")){
            setActivityBackgroundColor(Color.parseColor("#F44336"), Color.parseColor("#D32F2F"));
        }
        else if(naam.equals("Leerling niet gevonden")){
            leerlingNaam.setText(naam);
        }
//        magBuiten = (ImageView) findViewById(R.id.magBuiten);
    }
}
