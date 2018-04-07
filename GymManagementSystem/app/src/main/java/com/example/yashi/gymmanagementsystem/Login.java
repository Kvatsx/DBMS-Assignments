package com.example.yashi.gymmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private EditText emailEdittext;
    private EditText passwordEdittext;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = openOrCreateDatabase("dbFile",MODE_PRIVATE,null);

        emailEdittext = (EditText) findViewById(R.id.email_edit_text);
        passwordEdittext = (EditText) findViewById(R.id.password_edit_text);
    }

    public void login(View view) {
        email = emailEdittext.getText().toString();
        password = passwordEdittext.getText().toString();

        Cursor resultSet = db.rawQuery("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'",null);
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            String userType = resultSet.getString(helper.user_user_type);
            if(userType.equals("Customer")) {
                Intent customerIntent = new Intent(Login.this, HomeCustomer.class);
                startActivity(customerIntent);
            }
            else {
                Intent staffIntent = new Intent(Login.this, HomeStaff.class);
                startActivity(staffIntent);
            }
        }
        else {
            Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void redirectRegister(View view) {
        Intent registerPage = new Intent(Login.this, Register.class);
        startActivity(registerPage);
    }
}
