package com.mixxamm.smartpassalpha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by maxim on 19/12/2017.
 * Deze klasse zorgt ervoor dat inloggen mogelijk is. Werkt in de achtergrond. De klasse LoginActivity en LoginTest bevatten de lay-out.
 * LoginTest heeft een simpelere lay-out, omdat dat makkelijker is om mee te werken. LoginActivity heeft een ingewikkeldere lay-out
 * die ook gaat worden gebruikt wanneer we werken aan het uiterlijk van de app, wat op dit moment de laagste prioriteit is.
 */


public class Login extends AsyncTask<String, Void, String> {
    static String leerlingID, leerlingNaam, naarBuiten, tekst, type1;
    Context context;
    AlertDialog alertDialog;

    Login(Context context1) {
        context = context1;
    }

    @Override
    public String doInBackground(String... params) {
        String type = params[0];
        type1 = type;
        String login_url;
        if (type.equals("login")) {
            try {
                login_url = "https://smartpass.one/connect/login.php";
                String tabel = "tblleerlingen";
                String gebruikersnaam = params[1];
                String wachtwoord = params[2];
                URL url = new URL(login_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("tabel", "UTF-8") + "=" + URLEncoder.encode(tabel, "UTF-8") + "&" + URLEncoder.encode("gebruikersnaam", "UTF-8") + "=" + URLEncoder.encode(gebruikersnaam, "UTF-8") + "&"
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


                JSONObject jsonobj = new JSONObject(result);
                leerlingID = jsonobj.getString("leerlingID");
                leerlingNaam = jsonobj.getString("naam");
                naarBuiten = jsonobj.getString("buiten");
                stelLeerlingIdIn(context);

                bufferedReader.close();
                inputStream.close();
                httpsURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(type.equals("leerkrachtLogin")){

        }
        else if(type.equals("zetTeLaat")){
            String id = params[1];
            type1 = type;
            login_url = "https://smartpass.one/connect/telaat.php";
            try{
                URL url = new URL(login_url);
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
                JSONObject jsonobj = new JSONObject(result);
                tekst = jsonobj.getString("tekst");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
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
        if(type1.equals("login")){
            LeerlingenKaartActivity.id = leerlingID;
            LeerlingenKaartActivity.naam = leerlingNaam;
            LeerlingenKaartActivity.fotoURL = "https://smartpass.one/foto/" + leerlingID + ".png";
            LeerlingenKaartActivity.buiten = naarBuiten;

            Intent leerlingenkaart = new Intent(context, LeerlingenKaartActivity.class);
            context.startActivity(leerlingenkaart);
            ((Activity) context).finish();
        }
        else if(type1.equals("zetTeLaat")){
            Toast.makeText(context, tekst, Toast.LENGTH_SHORT).show();
            ToonLeerlingInfo.magBuiten.setImageResource(R.drawable.ic_cancel_black_48dp);
        }

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void stelLeerlingIdIn(Context c){
        SharedPreferences id2 = c.getSharedPreferences("id", 0);
        SharedPreferences.Editor editor = id2.edit();
        editor.putString("id", leerlingID);
        editor.commit();
    }
}


