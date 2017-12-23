package com.mixxamm.smartpassalpha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by maxim on 20/12/2017.
 */

public class LeerlingInfo extends AsyncTask<String, Void, String>{
    static String leerlingNaam, naarBuiten, id;
    Context context;
    LeerlingInfo(Context context1){
        context = context1;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String scan_url = "https://smartpass.000webhostapp.com/connect/scan.php";
        if(type.equals("infoOphalen")){
            try{
                id = params[1];
                URL url = new URL(scan_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                String leerlingID = id;
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
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    public void onPostExecute(String naam){
        /*ToonLeerlingInfo.naam = leerlingNaam;
        ToonLeerlingInfo.fotoURL = "https://smartpass.000webhostapp.com/foto/"+id+".png";
        ToonLeerlingInfo.buiten = naarBuiten;*/


        Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
        /*Intent leerkrachtenActivity = new Intent(context, LeerkrachtenActivity.class);
        context.startActivity(leerkrachtenActivity);*/
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

}

