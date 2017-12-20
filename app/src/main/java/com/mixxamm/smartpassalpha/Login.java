package com.mixxamm.smartpassalpha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by maxim on 19/12/2017.
 * Deze klasse zorgt ervoor dat inloggen mogelijk is. Werkt in de achtergrond. De klasse LoginActivity en LoginTest bevatten de lay-out.
 * LoginTest heeft een simpelere lay-out, omdat dat makkelijker is om mee te werken. LoginActivity heeft een ingewikkeldere lay-out
 * die ook gaat worden gebruikt wanneer we werken aan het uiterlijk van de app, wat op dit moment de laagste prioriteit is.
 */

public class Login extends AsyncTask<String, Void, String> {
    static String leerlingID, leerlingNaam, naarBuiten;
    Context context;
    AlertDialog alertDialog;
    Login(Context context1){
        context = context1;
    }
    @Override
    public String doInBackground(String... params) {
        String type = params[0];
        String login_url = "https://smartpass.000webhostapp.com/connect/login.php";
        if(type.equals("login")){
            try {
                String gebruikersnaam = params[1];
                String wachtwoord = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("gebruikersnaam", "UTF-8")+"="+URLEncoder.encode(gebruikersnaam, "UTF-8")+"&"
                        +URLEncoder.encode("wachtwoord", "UTF-8")+"="+URLEncoder.encode(wachtwoord, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result+= line;
                }


                JSONObject jsonobj = new JSONObject(result);
                leerlingID = jsonobj.getString("leerlingID");
                leerlingNaam = jsonobj.getString("naam");
                naarBuiten = jsonobj.getString("buiten");

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
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
        /*alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");*/
    }

    @Override
    public void onPostExecute(String naam) {
        LeerlingenKaartActivity.id = leerlingID;
        LeerlingenKaartActivity.naam = leerlingNaam;
        LeerlingenKaartActivity.fotoURL = "https://smartpass.000webhostapp.com/foto/"+leerlingID+".png";
        LeerlingenKaartActivity.buiten = naarBuiten;
        Intent leerlingenkaart = new Intent(context, LeerlingenKaartActivity.class);
        context.startActivity(leerlingenkaart);
        ((Activity)context).finish();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
