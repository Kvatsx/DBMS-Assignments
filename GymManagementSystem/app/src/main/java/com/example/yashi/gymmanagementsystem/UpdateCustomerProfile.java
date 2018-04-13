package com.example.yashi.gymmanagementsystem;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateCustomerProfile extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private String userId;

    private EditText addressEditText;
    private EditText ageEditText;
    private EditText weightEditText;
    private Spinner membershipPlanSpinner;

    private String address;
    private String age;
    private String weight;
    private String membershipPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer_profile);

        this.setTitle("Update Profile");

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        userId = this.getIntent().getStringExtra("userId");

        addressEditText = (EditText) findViewById(R.id.address_edit_text);
        ageEditText = (EditText) findViewById(R.id.age_edit_text);
        weightEditText = (EditText) findViewById(R.id.weight_edit_text);
        membershipPlanSpinner = (Spinner) findViewById(R.id.membershipPlan);
    }

    public void updateProfile(View view) {
        address = addressEditText.getText().toString();
        age = ageEditText.getText().toString();
        weight = weightEditText.getText().toString();
//        membershipPlan = Integer.parseInt(membershipPlanSpinner.getSelectedItem().toString());
        membershipPlan = "1";

        db.execSQL("CREATE TABLE IF NOT EXISTS customers(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, membership_plan_id INTEGER, address VARCHAR, age INTEGER, weight INTEGER);");
        db.execSQL("INSERT INTO customers VALUES(NULL, " + userId + "," + membershipPlan + ",'" + address + "'," + age + "," + weight + ");");
    }
}
