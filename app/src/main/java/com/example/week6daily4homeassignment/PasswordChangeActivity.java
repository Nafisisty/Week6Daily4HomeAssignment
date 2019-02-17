package com.example.week6daily4homeassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordChangeActivity extends AppCompatActivity {

    EditText newPasswordEditText;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        newPasswordEditText = findViewById(R.id.newPasswordEditTextId);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            user = bundle.getParcelable("user");
            newPasswordEditText.setText(user.getPassword());
        }

    }

    public void onClick(View view) {

        String userName = user.getUserName();
        user = new User(userName, newPasswordEditText.getText().toString());

        int updateSuccess = MainActivity.mySQLDatabaseHelper.updateUser(user);

        if(updateSuccess != 0) {

            Toast.makeText(this, "Successfully Changed", Toast.LENGTH_SHORT).show();

        }

    }
}
