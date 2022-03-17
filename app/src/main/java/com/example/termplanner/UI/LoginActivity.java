package com.example.termplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.termplanner.Entities.User;
import com.example.termplanner.Entities.UserAdmin;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Repository repository;
    private String username;
    private String password;
    EditText enterUsername;
    EditText enterPassword;
    int userId;
    private List<User> allUsers;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        repository = new Repository(getApplication());

        enterUsername = findViewById(R.id.etUsername);
        enterPassword = findViewById(R.id.etPassword);
    }


    public void LoginButton(View view) {
        User currentUser;
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

        } else if (username.equals(validUser) && password.equals(validPassword)) {


            currentUser = new UserAdmin(username, password);
            repository.update(currentUser);
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