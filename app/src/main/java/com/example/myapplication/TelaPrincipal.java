package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TelaPrincipal extends AppCompatActivity {

    private Button buttonSettings;
    private Button buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        buttonSettings = findViewById(R.id.buttonSettings);
        buttonExit = findViewById(R.id.buttonExit);

        buttonSettings.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(this, TelaConfiguracoes.class);
                startActivity(intent);
            } catch (Exception e){
                Log.e("faiou",e.getMessage(),e);
            }

        });
        addEventbtn(buttonExit);
    }


    private void addEventbtn(Button bnt) {
        if(bnt==buttonExit) {
            bnt.setOnClickListener(v -> {
                finishAffinity();
            });
        }
    }

}
