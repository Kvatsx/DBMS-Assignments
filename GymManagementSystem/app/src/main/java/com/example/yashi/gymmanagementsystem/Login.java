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

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS membership_plan(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, duration VARCHAR, price VARCHAR, description VARCHAR);");
        db.execSQL("INSERT INTO membership_plan(title, duration, price, description) VALUES('One Year Membership' ,'1 Year', '1000/-','One year membership Extendable');");
        db.execSQL("INSERT INTO membership_plan(title, duration, price, description) VALUES('Two Year Membership','2 Year','1800/-','Two year membership Extendable');");
        db.execSQL("INSERT INTO membership_plan(title, duration, price, description) VALUES('Three Year Membership','3 Year','2500/-','Three year membership Extendable');");
        db.execSQL("INSERT INTO membership_plan(title, duration, price, description) VALUES('Five Year Membership','5 Year','4000/-','Five year membership Extendable');");

        emailEdittext = (EditText) findViewById(R.id.email_edit_text);
        passwordEdittext = (EditText) findViewById(R.id.password_edit_text);
    }

    public void login(View view) {
        email = emailEdittext.getText().toString();
        password = passwordEdittext.getText().toString();
        String query = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
        Cursor resultSet = db.rawQuery(query,null);
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            String userType = resultSet.getString(helper.users_user_type);
            if(userType.equals("Customer")) {
                Intent customerIntent = new Intent(Login.this, HomeCustomer.class);
                customerIntent.putExtra("query",query);
                startActivity(customerIntent);
            }
            else {
                Intent staffIntent = new Intent(Login.this, HomeStaff.class);
                staffIntent.putExtra("query",query);
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
