package com.example.yashi.gymmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeStaff extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_staff);

        this.setTitle("Welcome, Staff!");

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        String query = this.getIntent().getStringExtra("query");

        Cursor resultSet = db.rawQuery(query,null);
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            userId = resultSet.getString(helper.users_id);
        }
    }

    public void updateProfileStaff(View view) {
        Intent updateProfileIntent = new Intent(HomeStaff.this, UpdateStaffProfile.class);
        updateProfileIntent.putExtra("userId",userId);
        startActivity(updateProfileIntent);
    }

    public void viewProfileStaff(View view) {
        Intent viewProfileIntent = new Intent(HomeStaff.this, ViewStaffProfile.class);
        viewProfileIntent.putExtra("userId",userId);
        startActivity(viewProfileIntent);
    }
}
