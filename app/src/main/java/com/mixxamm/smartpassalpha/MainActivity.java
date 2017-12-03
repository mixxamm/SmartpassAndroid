package com.mixxamm.smartpassalpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import static com.mixxamm.smartpassalpha.R.id.smartschool_login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView smartschoolLogin = (ImageView) findViewById(smartschool_login);
        smartschoolLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent login = new Intent(view.getContext(), LoginActivity.class);
                startActivity(login);
            }
        });
    }
}
