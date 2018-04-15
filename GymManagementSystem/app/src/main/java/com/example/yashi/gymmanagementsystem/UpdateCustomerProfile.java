package com.example.yashi.gymmanagementsystem;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateCustomerProfile extends AppCompatActivity {
    public static final String TAG = "UpdateCustomer";
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
    private int membershipPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer_profile);

        this.setTitle("Update Profile");


        userId = this.getIntent().getStringExtra("userId");

        addressEditText = (EditText) findViewById(R.id.address_edit_text);
        ageEditText = (EditText) findViewById(R.id.age_edit_text);
        weightEditText = (EditText) findViewById(R.id.weight_edit_text);
        membershipPlanSpinner = (Spinner) findViewById(R.id.membershipPlan);

        String[] membershipRange = {"1 Year", "2 Year", "3 Year", "5 Year"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, membershipRange);
        membershipPlanSpinner.setAdapter(adapter);
        membershipPlanSpinner.setSelection(0);
    }

    public void updateProfile(View view) {
        address = addressEditText.getText().toString();
        age = ageEditText.getText().toString();
        weight = weightEditText.getText().toString();
        membershipPlan = 0;

        if ( membershipPlanSpinner.getSelectedItem().toString().equals("1 Year") )
        {
            membershipPlan = 1;
        }
        else if ( membershipPlanSpinner.getSelectedItem().toString().equals("2 Year") )
        {
            membershipPlan = 2;
        }
        else if ( membershipPlanSpinner.getSelectedItem().toString().equals("3 Year") )
        {
            membershipPlan = 3;
        }
        else
        {
            membershipPlan = 4;
        }
        Log.d(TAG,"membership_plan "+membershipPlan);
        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS customers(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, membership_plan_id INTEGER, address VARCHAR, age INTEGER, weight INTEGER);");
        db.execSQL("INSERT INTO customers VALUES(NULL, " + userId + "," + membershipPlan + ",'" + address + "'," + age + "," + weight + ");");
    }
}
