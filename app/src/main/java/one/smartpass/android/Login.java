package one.smartpass.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mixxamm.smartpassalpha.R;

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

import static one.smartpass.android.MainActivity.ACCOUNT;

/**
 * Created by maxim on 19/12/2017.
 * Deze klasse zorgt ervoor dat inloggen mogelijk is. Werkt in de achtergrond. De klasse LoginActivity bevat de lay-out.
 */


public class Login extends AsyncTask<String, Void, String> {
    protected static String leerlingID, leerlingNaam, naarBuiten, tekst, type, leerkrachtNaam, login, type2, klas, datum, logintoken, gebruikersnaam, wachtwoord, tabel1, fout = "Leerling niet te laat gezet";
    private static int aantalTotaal, aantalTrimester, aantalTotNablijven;
    private static boolean slaagGegevensOp;
    private Context context;

    Login(Context context1) {
        context = context1;
    }

    @Override
    public String doInBackground(String... params) {
        try {
            ProviderInstaller.installIfNeeded(context);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        type = params[0];
        String login_url;
        if ("login".equals(type)) {
            try {
                login_url = "https://smartpass.one/connect/login.php";
                String tabel = "tblleerlingen";
                String loginType = params[1];
                if(loginType.equals("token")){
                    gebruikersnaam = params[2];
                    logintoken = params[3];
                    wachtwoord = "";
                }
                else{
                    gebruikersnaam = params[1];
                    wachtwoord = params[2];
                    slaagGegevensOp = Boolean.parseBoolean(params[3]);
                    logintoken = "";
                }
                URL url = new URL(login_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String token = FirebaseInstanceId.getInstance().getToken();
                String post_data = URLEncoder.encode("tabel", "UTF-8") + "=" + URLEncoder.encode(tabel, "UTF-8") + "&"
                        + URLEncoder.encode("gebruikersnaam", "UTF-8") + "=" + URLEncoder.encode(gebruikersnaam, "UTF-8") + "&"
                        + URLEncoder.encode("wachtwoord", "UTF-8") + "=" + URLEncoder.encode(wachtwoord, "UTF-8") + "&"
                        + URLEncoder.encode("androidtoken", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8") + "&"
                        + URLEncoder.encode("logintoken", "UTF-8") + "=" + URLEncoder.encode(logintoken, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }


                JSONObject jsonobj = new JSONObject(result);
                leerlingID = jsonobj.getString("leerlingID");
                leerlingNaam = jsonobj.getString("naam");
                naarBuiten = jsonobj.getString("buiten");
                klas = jsonobj.getString("klas");
                logintoken = jsonobj.getString("logintoken");
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

        } else if ("loginLeerkracht".equals(type)) {
            try {
                login_url = "https://smartpass.one/connect/login.php";
                tabel1 = "tblleerkrachten";
                String loginType = params[1];
                if(loginType.equals("token")){
                    gebruikersnaam = params[2];
                    logintoken = params[3];
                    wachtwoord = "";
                }
                else{
                    gebruikersnaam = params[1];
                    wachtwoord = params[2];
                    slaagGegevensOp = Boolean.parseBoolean(params[3]);
                    logintoken = "";
                }
                URL url = new URL(login_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("tabel", "UTF-8") + "=" + URLEncoder.encode(tabel1, "UTF-8") + "&"
                        + URLEncoder.encode("gebruikersnaam", "UTF-8") + "=" + URLEncoder.encode(gebruikersnaam, "UTF-8") + "&"
                        + URLEncoder.encode("wachtwoord", "UTF-8") + "=" + URLEncoder.encode(wachtwoord, "UTF-8") + "&"
                        + URLEncoder.encode("logintoken", "UTF-8") + "=" + URLEncoder.encode(logintoken, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) !=null){
                    result += line;
                }
                JSONObject jsonObject = new JSONObject(result);
                leerkrachtNaam = jsonObject.getString("naamLeerkracht");
                login = jsonObject.getString("login");
                logintoken = jsonObject.getString("logintoken");

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


        } else if("dashboard".equals(type)){
            login_url = "https://smartpass.one/connect/dashboard.php";
            String id = params[1];
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
                aantalTotaal = jsonobj.getInt("totaal");
                aantalTrimester = jsonobj.getInt("trimester");
                aantalTotNablijven = jsonobj.getInt("totnablijven");
                datum = jsonobj.getString("datum");

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





        else if ("zetTeLaat".equals(type)) {
            String id = params[1];
            String logintoken = params[2];
            String leerkrachtNaam = params[3];
            type2 = params[4];
            login_url = "https://smartpass.one/connect/telaat.php";
            try {
                URL url = new URL(login_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")
                        + "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(logintoken, "UTF-8")
                        + "&" + URLEncoder.encode("naam", "UTF-8") + "=" + URLEncoder.encode(leerkrachtNaam, "UTF-8");
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
        else if("meldMisbruik".equals(type)){
            String id = params[1];
            String logintoken = params[2];
            String leerkrachtNaam = params[3];

            login_url = "https://smartpass.one/connect/meldmisbruik.php";
            try {
                URL url = new URL(login_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")
                        + "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(logintoken, "UTF-8")
                        + "&" + URLEncoder.encode("naam", "UTF-8") + "=" + URLEncoder.encode(leerkrachtNaam, "UTF-8");
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

        if("login".equals(type)){
            LeerlingenKaartFragment.naam = leerlingNaam;
            LeerlingenKaartFragment.fotoURL = "https://smartpass.one/foto/" + leerlingID + ".png";
            LeerlingenKaartFragment.buiten = naarBuiten;
            LeerlingenKaartFragment.klas = klas;
            LeerlingenKaartFragment.id = leerlingID;

            if("Leerling niet gevonden".equals(leerlingNaam) || leerlingNaam.isEmpty()){
                /*Toast.makeText(context, "Naam of wachtwoord fout", Toast.LENGTH_LONG).show();*/
                resetLeerling();
                LinearLayout loginLayout = ((Activity)context).findViewById(R.id.loginLayout);
                Snackbar snackbar = Snackbar.make(loginLayout, "Naam of wachtwoord fout (Leerling)", Snackbar.LENGTH_LONG);
                snackbar.show();
                ProgressBar progressBar = ((Activity)context).findViewById(R.id.login_laden);
                progressBar.setVisibility(View.INVISIBLE);
                /*LoginActivity loginActivity = new LoginActivity();
                LoginActivity.progressBar.setVisibility(View.INVISIBLE);*/
            }
            else{
                Intent leerlingenkaart = new Intent(context, LeerlingActivity.class);
                context.startActivity(leerlingenkaart);
                ((Activity) context).finish();
            }
        }

        else if ("zetTeLaat".equals(type) && "tli".equals(type2)) {
            if(tekst.contains(fout) || "Er is iets fout gegaan".equals(tekst)){
                Toast.makeText(context, tekst, Toast.LENGTH_SHORT).show();
            }

        }
        else if("zetTeLaat".equals(type) && "sa".equals(type2)){
            if(tekst.contains(fout) || "Er is iets fout gegaan".equals(tekst)){
                RelativeLayout scanLayout = ((Activity)context).findViewById(R.id.scanActivityLayout);
                Snackbar snackbar = Snackbar.make(scanLayout, tekst, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else{
                ScanFragment.buiten = "0";
                ScanFragment scanFragment = new ScanFragment();
                scanFragment.laden();
                RelativeLayout scanLayout = ((Activity)context).findViewById(R.id.scanActivityLayout);
                Snackbar snackbar = Snackbar.make(scanLayout, tekst, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        else if("loginLeerkracht".equals(type)){
                if("1".equals(login) && !leerkrachtNaam.isEmpty()){
                    Intent leerkrachtenActivity = new Intent(context, LeerkrachtenActivity.class);
                    leerkrachtenActivity.putExtra("type", "normal");
                    if(slaagGegevensOp){
                        SharedPreferences account = context.getSharedPreferences(ACCOUNT, 0);
                        SharedPreferences.Editor editor = account.edit();
                        editor.putString("token", logintoken);
                        editor.commit();
                    }
                    context.startActivity(leerkrachtenActivity);
                    ((Activity) context).finish();
                }
                else{
                    resetLeerkracht();
                    LinearLayout loginLayout = ((Activity)context).findViewById(R.id.loginLayout);
                    Snackbar snackbar = Snackbar.make(loginLayout, "Naam of wachtwoord fout (Leerkracht)", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    ProgressBar progressBar = ((Activity)context).findViewById(R.id.login_laden);
                    progressBar.setVisibility(View.INVISIBLE);
                    /*LoginActivity loginActivity = new LoginActivity();
                    loginActivity.progressOnzichtbaar();*/
                }

        }
        else if("dashboard".equals(type)){
            DashboardFragment.aantalTeLaat = aantalTotaal;
            DashboardFragment.aantalTeLaatTrimester = aantalTrimester;
            DashboardFragment.intAantalTotNablijven = aantalTotNablijven;
            DashboardFragment.datum = datum;
            DashboardFragment dashboardFragment = new DashboardFragment();
            dashboardFragment.laden();
        }
        else if("meldMisbruik".equals(type)){
            RelativeLayout scanActivityLayout = ((Activity)context).findViewById(R.id.scanActivityLayout);
            Snackbar snackbar = Snackbar.make(scanActivityLayout, "Misbruik gemeld.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void stelLeerlingIdIn(Context c) {
        if(slaagGegevensOp){
            SharedPreferences account = c.getSharedPreferences(ACCOUNT, 0);
            SharedPreferences.Editor editor = account.edit();
            editor.putString("id", leerlingID);
            editor.putString("token", logintoken);
            editor.putString("klas", klas);
            editor.commit();
        }
    }

    public void resetLeerkracht(){
        SharedPreferences account = context.getSharedPreferences(ACCOUNT, 0);
        SharedPreferences.Editor editor = account.edit();
        editor.putString("naamLeerkracht", "");
        editor.commit();
    }

    public void resetLeerling(){
        SharedPreferences account = context.getSharedPreferences(MainActivity.ACCOUNT, 0);
        SharedPreferences.Editor editor = account.edit();
        editor.putString("naamGebruiker", "");
        editor.commit();
    }


}


