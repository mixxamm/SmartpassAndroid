package com.mixxamm.smartpassalpha;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import static com.mixxamm.smartpassalpha.MainActivity.ACCOUNT;
public class ScanActivity2 extends AppCompatActivity {//TODO: ook hier loaders gebruiken, zo moet de activiteit niet herstarten bij elke scan

    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;
    public static String fotoURL, naam, buiten = "2", id, vorigID = "eersteKeer";
    CircleImageView leerlingFoto;
    TextView leerlingNaam;
    public static ImageView magBuiten;
    public static Button teLaat;


    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.scanFragment, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan2);






        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Window w = getWindow();
                    w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                }
                cameraView = (SurfaceView) findViewById(R.id.cameraView2);
                cameraView.setZOrderMediaOverlay(true);
                holder = cameraView.getHolder();
                barcode = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
                if (!barcode.isOperational()) {
                    Toast.makeText(getApplicationContext(), "Er is een fout opgetreden. Foutcode: A1", Toast.LENGTH_LONG).show();
                    this.finish();
                }
                cameraSource = new CameraSource.Builder(this, barcode).setFacing(CameraSource.CAMERA_FACING_BACK)
                        .setRequestedFps(60).setAutoFocusEnabled(true).setRequestedPreviewSize(1920, 1080)
                        .build();
                cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        try {
                            if (ContextCompat.checkSelfPermission(ScanActivity2.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                cameraSource.start(cameraView.getHolder());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                        cameraSource.stop();
                        vorigID = "eersteKeer";
                        //Stopt de camera nadat de scanactiviteit is gesloten (bespaart enorm veel batterij)
                    }
                });
                barcode.setProcessor(new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<Barcode> detections) {
                        final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                        if (barcodes.size() > 0) {
                            Barcode thisCode = barcodes.valueAt(0);
                            String id = thisCode.rawValue;

                            if(vorigID.equals("eersteKeer") || !vorigID.equals(id)){
                                vorigID = id;
                                String type = "infoOphalen2";
                                LeerlingInfo infoLeerling = new LeerlingInfo(ScanActivity2.this);
                                infoLeerling.execute(type, id);
                                Fragment fragment = new ScanFragment();
                                loadFragment(fragment);
                            }
                        }

            }
        });












        /*if (buiten.equals("1")) {
            magBuiten.setImageResource(R.drawable.ic_check_circle_black_48dp);
        } else if (buiten.equals("0")) {
            magBuiten.setImageResource(R.drawable.ic_cancel_black_48dp);
        } else if (buiten.equals("3")) {
            magBuiten.setImageResource(R.drawable.alert_circle);
        } else {
            magBuiten.setImageResource(R.drawable.sync_alert);
        }*/

    }




    @Override
    public void onDestroy() {
        super.onDestroy();

        /*LeerkrachtenActivity.progressBar.setVisibility(View.INVISIBLE);*///TODO: BUG: crasht
    }

}


