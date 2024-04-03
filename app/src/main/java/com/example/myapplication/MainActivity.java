package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int trys;
    private SharedPreferences sharedPrefs;
    private SharedPreferences tryPrefs;
    private Button buttonLogar;
    private Button buttonCancelar;
    private EditText usuarioInput;
    private EditText senhaInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogar = findViewById(R.id.ButtonLogar);
        buttonCancelar = findViewById(R.id.ButtonCancelar);
        usuarioInput = findViewById(R.id.UsuarioInput);
        senhaInput = findViewById(R.id.SenhaInput);

        tryPrefs=getSharedPreferences("TrysPreferences", Context.MODE_PRIVATE);
        sharedPrefs=getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        firstResetPreference();

        SharedPreferences.Editor editor = tryPrefs.edit();
        editor.putInt("try", 3);
        editor.apply();

        // Recuperar o valor de tentativas ou criando se não existir.
        trys = tryPrefs.getInt("try", 3);

        addEventbtn(buttonLogar);
        addEventbtn(buttonCancelar);
    }

    private void firstResetPreference() {
        boolean isExecuted = sharedPrefs.getBoolean("executado", false);
        if(!isExecuted){
            SharedPreferences.Editor editorUser = sharedPrefs.edit();
            editorUser.putString("user","admin");
            editorUser.putString("password","admin");
            editorUser.putBoolean("executado", true);
            editorUser.apply();
        }
    }

    private void addEventbtn(Button bnt) {
        if(bnt==buttonLogar) {
            bnt.setOnClickListener(v -> {

                // Obter os valores digitados pelo usuário
                String userInput = usuarioInput.getText().toString();
                String passwordInput = senhaInput.getText().toString();

                if(trys>0){
                    String user = sharedPrefs.getString("user","");
                    String password = sharedPrefs.getString("password","");
                    // Verificar se os valores correspondem aos valores esperados
                    if (userInput.equals(user) && passwordInput.equals(password)) {
                        SharedPreferences.Editor editor = tryPrefs.edit();
                        editor.putInt("try", 3);
                        editor.apply();

                        Toast.makeText(MainActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, TelaPrincipal.class);
                        startActivity(intent);

                    } else {
                        SharedPreferences.Editor editor = tryPrefs.edit();
                        editor.putInt("try", trys--);
                        editor.apply();
                        usuarioInput.setText("");
                        senhaInput.setText("");
                        Toast.makeText(MainActivity.this, "Usuario ou senhas incorretas restam " +trys + " tentativas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Tentativas Esgotadas")
                            .setMessage("Você excedeu o número máximo de tentativas.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                }
                            })
                            .setCancelable(false);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        if(bnt==buttonCancelar) {
            bnt.setOnClickListener(v -> {
                usuarioInput.setText("");
                senhaInput.setText("");
                Toast.makeText(MainActivity.this, "Campos limpos!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}