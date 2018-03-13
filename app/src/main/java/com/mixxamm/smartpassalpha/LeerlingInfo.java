package com.mixxamm.smartpassalpha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import static java.security.AccessController.getContext;

/**
 * Created by maxim on 20/12/2017.
 */

public class LeerlingInfo extends AsyncTask<String, Void, String> {
    static String leerlingNaam, naarBuiten, id, type;
    Context context;


    LeerlingInfo(Context context1) {
        context = context1;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        String scan_url = "https://smartpass.one/connect/scan.php";
        if (type.equals("infoOphalen")) {
            try {
                id = params[1];
                URL url = new URL(scan_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                JSONObject jsonObject = new JSONObject(result);
                leerlingNaam = jsonObject.getString("naam");
                naarBuiten = jsonObject.getString("buiten");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("infoOphalen2")) {
            try {
                id = params[1];
                URL url = new URL(scan_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                JSONObject jsonObject = new JSONObject(result);
                leerlingNaam = jsonObject.getString("naam");
                naarBuiten = jsonObject.getString("buiten");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public void onPostExecute(String naam) {
        if(type.equals("infoOphalen")){
            ToonLeerlingInfo.naam = leerlingNaam;
            ToonLeerlingInfo.fotoURL = "https://smartpass.one/foto/" + id + ".png";
            ToonLeerlingInfo.buiten = naarBuiten;
            ToonLeerlingInfo.id = id;

            Intent toonLeerlingInfo = new Intent(context, ToonLeerlingInfo.class);
            context.startActivity(toonLeerlingInfo);
            ((Activity)context).finish();
        }

        if(type.equals("infoOphalen2")){
            ScanActivity2.naam = leerlingNaam;
            ScanActivity2.fotoURL = "https://smartpass.one/foto/" + id + ".png";
            ScanActivity2.buiten = naarBuiten;
            ScanActivity2.id = id;
            ScanActivity2.teLaat.setVisibility(View.VISIBLE);

            Intent scanActivity2 = new Intent(context, ScanActivity2.class);
            scanActivity2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(scanActivity2);
        }

        /*Intent leerkrachtenActivity = new Intent(context, LeerkrachtenActivity.class);
        context.startActivity(leerkrachtenActivity);*/
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }


}

