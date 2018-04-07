package com.example.yashi.gymmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity {

    private SQLiteDatabase db;

    private EditText nameEdittext;
    private EditText emailEdittext;
    private EditText passwordEdittext;
    private Spinner userTypeSpinner;

    private String name;
    private String email;
    private String password;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = openOrCreateDatabase("dbFile",MODE_PRIVATE,null);

        nameEdittext = (EditText) findViewById(R.id.name_edit_text);
        emailEdittext = (EditText) findViewById(R.id.email_edit_text);
        passwordEdittext = (EditText) findViewById(R.id.password_edit_text);

        userTypeSpinner = findViewById(R.id.userType);
        String[] items = new String[]{"Customer", "Staff"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        userTypeSpinner.setAdapter(adapter);

    }

    public void register(View view) {
        name = nameEdittext.getText().toString();
        email = emailEdittext.getText().toString();
        password = passwordEdittext.getText().toString();
        userType = userTypeSpinner.getSelectedItem().toString();

        @SuppressLint("SimpleDateFormat") String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        db.execSQL("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, email VARCHAR, password VARCHAR, join_date VARCHAR, user_type VARCHAR);");
        db.execSQL("INSERT INTO users VALUES(NULL, '" + name + "','" + email + "','" + password + "','" + currentDate + "','" + userType + "');");
    }

    public void redirectLogin(View view) {
        Intent registerPage = new Intent(Register.this, Login.class);
        startActivity(registerPage);
    }
}
