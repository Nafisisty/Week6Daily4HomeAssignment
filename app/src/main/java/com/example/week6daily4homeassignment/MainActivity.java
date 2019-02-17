package com.example.week6daily4homeassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {

    EditText userNameEditText;
    EditText passwordEditText;
    Button logInButton;
    Button signUpButton;
    public static MySQLDatabaseHelper mySQLDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.userNameEditTextId);
        passwordEditText = findViewById(R.id.passwordEditTextId);
        logInButton = findViewById(R.id.logInButtonId);
        signUpButton = findViewById(R.id.signUpButtonId);

        mySQLDatabaseHelper = new MySQLDatabaseHelper(this);

    }


    public void onClick(View view) {

        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = new User(userName, password);

        switch (view.getId()) {
            case R.id.logInButtonId:
                User dbUser = mySQLDatabaseHelper.getUserByName(userName);
                if(dbUser == null){
                    Toast.makeText(this, "User don't Exist!!! Please Sign Up First.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (dbUser.getPassword().equals(userName)) {
                        Intent intent = new Intent(this, PasswordChangeActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        Toast.makeText(this, "Successfully Login", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Wrong Password!!!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.signUpButtonId:
                mySQLDatabaseHelper.addUser(user);
                Toast.makeText(this, "Successfully Created Account", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
