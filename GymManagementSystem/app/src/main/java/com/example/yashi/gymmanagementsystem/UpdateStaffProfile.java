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

public class UpdateStaffProfile extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private String userId;

    private EditText addressEditText;
    private EditText salaryEditText;

    private String address;
    private String salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff_profile);

        this.setTitle("Update Profile");

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        userId = this.getIntent().getStringExtra("userId");

        addressEditText = (EditText) findViewById(R.id.address_edit_text);
        salaryEditText = (EditText) findViewById(R.id.salary_edit_text);
    }

    public void updateProfileStaffFunc(View view) {
        address = addressEditText.getText().toString();
        salary = salaryEditText.getText().toString();

        db.execSQL("CREATE TABLE IF NOT EXISTS staff(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, address VARCHAR, salary INTEGER);");
        db.execSQL("INSERT INTO staff VALUES(NULL, " + userId + ",'" + address + "'," + salary + ");");
    }
}
