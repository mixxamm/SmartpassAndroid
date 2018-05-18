package one.smartpass.android;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.mixxamm.smartpassalpha.R;

public class LeerkrachtenActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    public ProgressBar progressBar;
    //CircleImageView profielFotoScan;
    public static String naam, fotoURL, buiten;
    private Button scanButton;//Knop object aanmaken
    private TextView info;//TextView met informatie aanmaken TODO: weghalen, is niet meer nodig
    private ImageView uitloggen;

    protected String type;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leerkrachten);
        scanButton = findViewById(R.id.scanButton);
        uitloggen = findViewById(R.id.loguitLeerkracht);
        info = findViewById(R.id.info);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            type = extras.getString("type");
        }
        else{
            type = "normal";
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }



        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(LeerkrachtenActivity.this, ScanActivity2.class);
                    startActivityForResult(intent, REQUEST_CODE);
            }
        });

        uitloggen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLeerkracht();
                Intent mainActivity = new Intent(LeerkrachtenActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                info.post(new Runnable() {
                    @Override
                    public void run() {
                            String id = barcode.rawValue;
                            String type = "infoOphalen2";
                            LeerlingInfo infoLeerling = new LeerlingInfo(LeerkrachtenActivity.this);
                            infoLeerling.execute(type, id);

                            progressBar = findViewById(R.id.infoLaden);
                            progressBar.setVisibility(View.VISIBLE);
                    }
                });
        }
    }

    public void resetLeerkracht(){
        SharedPreferences account = getSharedPreferences(MainActivity.ACCOUNT, 0);
        SharedPreferences.Editor editor = account.edit();
        editor.putString("naamLeerkracht", "");
        editor.apply();
    }

}


