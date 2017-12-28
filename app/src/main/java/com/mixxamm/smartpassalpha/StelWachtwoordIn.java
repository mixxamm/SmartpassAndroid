package com.mixxamm.smartpassalpha;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by maxim on 26/12/2017.
 */

public class StelWachtwoordIn extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;

    StelWachtwoordIn(Context context1) {
        context = context1;
    }

    @Override
    public String doInBackground(String... params) {
        String type = params[0];
        String stelWachtwoordIn_url = "https://smartpass.one/connect/stelwachtwoordin.php";
        if (type.equals("stelWachtwoordIn")) {
            try {
                String gebruikersnaam = params[1];
                String wachtwoord = params[2];
                URL url = new URL(stelWachtwoordIn_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("gebruikersnaam", "UTF-8") + "=" + URLEncoder.encode(gebruikersnaam, "UTF-8") + "&"
                        + URLEncoder.encode("wachtwoord", "UTF-8") + "=" + URLEncoder.encode(wachtwoord, "UTF-8");
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

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(String test) {

    }
}
