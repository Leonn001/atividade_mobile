package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TelaConfiguracoes extends AppCompatActivity {

    private String user;
    private String password;
    private Button confirm;
    private Button cancel;
    private EditText userInput;
    private EditText passwordIpunt;
    private EditText confirmPasswordInput;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracoes);

        cancel = findViewById(R.id.CancelButtton);
        confirm = findViewById(R.id.ConfirmButton);
        userInput = findViewById(R.id.editTextText);
        passwordIpunt = findViewById(R.id.editTextText2);
        confirmPasswordInput = findViewById(R.id.editTextText3);


        sharedPrefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        user = sharedPrefs.getString("user", "admin");
        password = sharedPrefs.getString("password", "admin");


        confirm.setOnClickListener(v -> {
            try {
                String userInput1 = userInput.getText().toString();
                String passwordInput = passwordIpunt.getText().toString();
                String ConfirmPasswordInput = confirmPasswordInput.getText().toString();

                if (userInput1.isBlank() || passwordInput.isBlank()) {throw new Exception("Usuario nao pode ser vazio");}
                if (passwordInput.equals(ConfirmPasswordInput)){
                    SharedPreferences.Editor editorUser = sharedPrefs.edit();
                    editorUser.putString("user",userInput1);
                    editorUser.putString("password",passwordInput);
                    editorUser.apply();
                    Toast.makeText(TelaConfiguracoes.this,"Usuário editado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TelaConfiguracoes.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TelaConfiguracoes.this,"As senhas não correspondem", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e){
                Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Faiou",e.getMessage(),e);
            }

        });

        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(TelaConfiguracoes.this, MainActivity.class);
            startActivity(intent);
        });

    }
}