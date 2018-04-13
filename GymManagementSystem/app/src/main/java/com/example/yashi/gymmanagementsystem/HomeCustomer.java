package com.example.yashi.gymmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeCustomer extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_customer);
        this.setTitle("Welcome, Customer");

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        String query = this.getIntent().getStringExtra("query");

        Cursor resultSet = db.rawQuery(query,null);
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            userId = resultSet.getString(helper.users_id);
        }

    }

    public void updateProfileButton(View view) {
        Intent updateProfileIntent = new Intent(HomeCustomer.this, UpdateCustomerProfile.class);
        updateProfileIntent.putExtra("userId",userId);
        startActivity(updateProfileIntent);
    }

    public void viewProfileButton(View view) {
        Intent viewProfileIntent = new Intent(HomeCustomer.this, ViewCustomerProfile.class);
        viewProfileIntent.putExtra("userId",userId);
        startActivity(viewProfileIntent);
    }
}
