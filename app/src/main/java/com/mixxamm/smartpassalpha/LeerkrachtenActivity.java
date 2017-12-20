package com.mixxamm.smartpassalpha;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class LeerkrachtenActivity extends AppCompatActivity {

    public static String naam, fotoURL, buiten;
    Button scanButton;//Knop object aanmaken
    TextView info;//TextView met informatie aanmaken
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leerkrachten);
        scanButton = (Button) findViewById(R.id.scanButton);
        info = (TextView) findViewById(R.id.info);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LeerkrachtenActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                info.post(new Runnable() {
                    @Override
                    public void run() {

                        String id = barcode.rawValue;
                        String type = "infoOphalen";
                        LeerlingInfo infoLeerling = new LeerlingInfo(LeerkrachtenActivity.this);
                        infoLeerling.execute(type, id);
                        //TODO: naam en foto uit database halen alles is klaar, wordt al naar deze klasse doorgestuurd. Moet enkel nog ingesteld worden
                    }


                });

            }

        }


    }

/*    public void instellen()
    {
        info.setText("test");
    }*/
// all the listener stuff below
}


