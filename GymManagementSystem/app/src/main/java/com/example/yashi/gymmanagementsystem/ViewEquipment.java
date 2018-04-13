package com.example.yashi.gymmanagementsystem;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ViewEquipment extends AppCompatActivity {

    private Helper helper = new Helper();
    private SQLiteDatabase db;

    private TextView equipmentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_equipment);

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        String query = "SELECT * FROM equipment";

        equipmentText = (TextView) findViewById(R.id.equipment_details);

        Cursor resultSet = db.rawQuery(query,null);
        if(resultSet.getCount() > 0) {
//            resultSet.moveToFirst();
//            userId = resultSet.getString(helper.users_id);
            equipmentText.setText(DatabaseUtils.dumpCursorToString(resultSet));
        }
    }
}
