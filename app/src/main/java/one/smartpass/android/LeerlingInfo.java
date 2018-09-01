package one.smartpass.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import static one.smartpass.android.MainActivity.ACCOUNT;

/**
 * Created by maxim on 20/12/2017.
 */

public class LeerlingInfo extends AsyncTask<String, Void, String> {
    private static String leerlingNaam, naarBuiten, naarBuitenOpLeerlingScherm, id, qr, type, klas;
    protected Context context;


    LeerlingInfo(Context context1) {
        context = context1;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        String scan_url = "https://smartpass.one/connect/scan.php";
        if ("infoOphalen".equals(type)) {
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
                String line;
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
        if ("infoOphalen2".equals(type)) {
            try {
                id = params[1];
                naarBuitenOpLeerlingScherm = params[2];
                qr = params[3];//De originele data van de QR-code, wordt hier gebruikt om te checken of er een "," in voorkomt.
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
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                JSONObject jsonObject = new JSONObject(result);
                leerlingNaam = jsonObject.getString("naam");
                naarBuiten = jsonObject.getString("buiten");
                klas = jsonObject.getString("klas");
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
        if(type.equals("infoOphalen2")){
            ScanFragment.naam = leerlingNaam;
            ScanFragment.fotoURL = "https://smartpass.one/foto/" + id + ".png";
            ScanFragment.buiten = naarBuiten;
            String delim = ",";
            if(!"4".equals(naarBuitenOpLeerlingScherm) && !naarBuiten.equals(naarBuitenOpLeerlingScherm) && qr.contains(delim)
                    && !"Leerling niet gevonden".equals(leerlingNaam)){
                RelativeLayout scanActivityLayout = ((Activity)context).findViewById(R.id.scanActivityLayout);
                Snackbar snackbar = Snackbar.make(scanActivityLayout, "Misbruik gedetecteerd.", Snackbar.LENGTH_LONG);
                snackbar.show();
                SharedPreferences account = context.getSharedPreferences(ACCOUNT, 0);
                String token = account.getString("token", "");
                String leerkrachtNaam = account.getString("naamLeerkracht", "");
                Login meldMisbruik = new Login(context);
                meldMisbruik.execute("meldMisbruik", id, token, leerkrachtNaam);
            }
            else if(!"Leerling niet gevonden".equals(leerlingNaam) && !qr.contains(delim)){
                ScanFragment.naam = "Leerling gebruikt outdated versie Smartpass";
            }
            ScanFragment.id = id;
            ScanFragment.klas = klas;
            ScanFragment.teLaat.setVisibility(View.VISIBLE);
            ScanFragment scanFragment = new ScanFragment();
            scanFragment.laden();
        }

        /*Intent leerkrachtenActivity = new Intent(context, LeerkrachtenActivity.class);
        context.startActivity(leerkrachtenActivity);*/
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }


}

