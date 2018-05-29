package one.smartpass.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;

import static com.mixxamm.smartpassalpha.R.id;
import static com.mixxamm.smartpassalpha.R.layout;

public class LoginActivity extends AppCompatActivity {

    public static String type;
    private EditText Gebruikersnaam, Wachtwoord;
    private CheckBox onthoudGegevens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);
        Gebruikersnaam = findViewById(id.gebruikersnaamtest);
        Wachtwoord = findViewById(id.wachtwoordtest);
        Button loginButton = findViewById(id.loginButton);
        TextView wachtwoordInstellen = findViewById(id.wachtwoordInstellen);
        onthoudGegevens = findViewById(id.onthoudGegevens);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean slaagGegevensOp = onthoudGegevens.isChecked();
                String slaagGegevensOpString = Boolean.toString(slaagGegevensOp);
                if("login".equals(type)){
                    ProgressBar progressBar = findViewById(id.login_laden);
                    progressBar.setVisibility(View.VISIBLE);
                    String gebruikersnaam = Gebruikersnaam.getText().toString();
                    String wachtwoord = Wachtwoord.getText().toString();
                    hideKeyboard(LoginActivity.this);

                    if(slaagGegevensOp){
                        SharedPreferences account = getSharedPreferences(MainActivity.ACCOUNT, 0);
                        SharedPreferences.Editor editor = account.edit();
                        editor.putString("naamGebruiker", gebruikersnaam);
                        editor.commit();
                    }


                    Login login = new Login(LoginActivity.this);
                    login.execute(type, gebruikersnaam, wachtwoord, slaagGegevensOpString);
                }
                else if("loginLeerkracht".equals(type)){
                    ProgressBar progressBar = findViewById(id.login_laden);
                    progressBar.setVisibility(View.VISIBLE);
                    String leerkrachtnaam = Gebruikersnaam.getText().toString();
                    String wachtwoord = Wachtwoord.getText().toString();
                    hideKeyboard(LoginActivity.this);


                    if(slaagGegevensOp){
                        SharedPreferences account = getSharedPreferences(MainActivity.ACCOUNT, 0);
                        SharedPreferences.Editor editor = account.edit();
                        editor.putString("naamLeerkracht", leerkrachtnaam);
                        editor.commit();
                    }

                    Login login = new Login(LoginActivity.this);

                    login.execute(type, leerkrachtnaam, wachtwoord, slaagGegevensOpString);
                }

            }
        });


        wachtwoordInstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WachtwoordInstellen.type = type;
                Intent wachtwoordInstellen = new Intent(LoginActivity.this, WachtwoordInstellen.class);
                startActivity(wachtwoordInstellen);
            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
