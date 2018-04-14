package com.example.yashi.gymmanagementsystem;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewEquipment extends AppCompatActivity {

    private Helper helper = new Helper();
    private SQLiteDatabase db;

    private ScrollView allequipment;
    private TextView equipmentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_equipment);

        this.setTitle("All Equipments Details");

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        String query = "SELECT * FROM equipment WHERE quantity > 0";
        String tableString = "";
//        allequipment = (ScrollView) findViewById(R.id.allequipment);
        equipmentText = (TextView) findViewById(R.id.equipment_details);
        Cursor resultSet = db.rawQuery(query,null);
        if (resultSet.getCount() > 0 ){
            resultSet.moveToFirst();
            String[] columnNames = {"Name", "Price", "Date", "Quantity"};
            do {
                tableString += String.format("%s: %s\n", "Name", resultSet.getString(helper.equipment_name));
                tableString += String.format("%s: %s\n", "Price", resultSet.getString(helper.equipment_price));
                tableString += String.format("%s: %s\n", "Date", resultSet.getString(helper.equipment_date));
                tableString += String.format("%s: %s\n", "Quantity", resultSet.getString(helper.equipment_quantity));
                tableString += "\n\n";

            } while (resultSet.moveToNext());
        }
        equipmentText.setText(tableString);
    }
}
