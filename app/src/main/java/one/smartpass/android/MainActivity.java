package one.smartpass.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mixxamm.smartpassalpha.R;

import java.io.IOException;

import static com.mixxamm.smartpassalpha.R.id.leerling_login;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_INTRODUCTIE = "Introductie";
    public static final String ACCOUNT = "Account";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences account = getSharedPreferences(ACCOUNT, 0);
        String naamGebruiker = account.getString("naamGebruiker", "");
        String loginToken = account.getString("token", "");
        String naamLeerkracht = account.getString("naamLeerkracht", "");
        try {
            if(isNetworkAvailable() && naamGebruiker != "" && isConnected()){
                laden();
                Login login = new Login(MainActivity.this);
                login.execute("login", "token", naamGebruiker, loginToken);
            }
            else if(isNetworkAvailable() && naamLeerkracht != "" && isConnected()){
                laden();
                Login login = new Login(MainActivity.this);
                login.execute("loginLeerkracht", "token", naamLeerkracht, loginToken);
            }
            else if(!isNetworkAvailable() && naamLeerkracht != "" &&!isConnected()){
                Toast.makeText(this, "Maak verbinding met internet om automatisch in te loggen.", Toast.LENGTH_SHORT).show();
            }
            else if (!isConnected() && naamGebruiker != "") {
                laden();
                Intent leerlingActivity = new Intent(MainActivity.this, LeerlingActivity.class);
                leerlingActivity.putExtra("internet", "false");
                id = account.getString("id", "");
                LeerlingActivity.id = Integer.valueOf(id);
                startActivity(leerlingActivity);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*VideoView videoView = (VideoView) findViewById(R.id.videoView1);
        Uri path = Uri.parse("android.recourse://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(path);
        videoView.start();*/

        SharedPreferences introductie = getSharedPreferences(PREFS_INTRODUCTIE, 0);
        boolean booleanIntroductie = introductie.getBoolean("introductie", false);

        if (!booleanIntroductie) {//Kijkt na of introductie al bekeken is
            SharedPreferences introductie1 = getSharedPreferences(PREFS_INTRODUCTIE, 0);
            SharedPreferences.Editor editor = introductie1.edit();
            editor.putBoolean("introductie", true);//Zo weet de app dat de gebruiker de introductie al heeft gezien
            editor.commit();
            Intent Introductie = new Intent(MainActivity.this, one.smartpass.android.Introductie.class);
            startActivity(Introductie);
            finish();
        }


        TextView smartschoolLogin = findViewById(leerling_login);
        smartschoolLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    laden();
                    LoginActivity.type = "login";//Zorgt ervoor dat de klasse login weet dat we willen inloggen als gebruiker.
                    Intent login = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(login);
                    finish();}
        });
        Button leerkrachtLogin = (Button) findViewById(R.id.leerkrachtLogin);
        leerkrachtLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity.type = "loginLeerkracht";
                Intent login = new Intent(view.getContext(), LoginActivity.class);
                startActivity(login);
                finish();
            }

            ;
        });
    }
    private void laden(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.ProgressBarMainActivity);
        progressBar.setVisibility(View.VISIBLE);
        TextView smartschoolLogin = (TextView) findViewById(R.id.leerling_login);
        smartschoolLogin.setVisibility(View.INVISIBLE);
        Button leerkrachtLogin = (Button) findViewById(R.id.leerkrachtLogin);
        leerkrachtLogin.setVisibility(View.INVISIBLE);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public boolean isConnected() throws InterruptedException, IOException
    {
        String command = "ping -c 1 smartpass.one";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }


}