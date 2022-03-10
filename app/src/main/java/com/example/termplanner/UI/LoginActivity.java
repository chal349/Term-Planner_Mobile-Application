package com.example.termplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.termplanner.R;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    EditText enterUsername;
    EditText enterPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enterUsername = findViewById(R.id.etUsername);
        enterPassword = findViewById(R.id.etPassword);
    }


    public void LoginButton(View view) {
        username = enterUsername.getText().toString();
        password = enterPassword.getText().toString();
        String validUser = "admin";
        String validPassword = "admin";

        if (username.trim().isEmpty() || password.trim().isEmpty()) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("All fields must be completed!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
        } else if (username.equals(validUser) || password.equals(validPassword)) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Incorrect Username or Password");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }
}