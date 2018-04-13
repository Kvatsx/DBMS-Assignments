package com.example.yashi.gymmanagementsystem;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AddEquipment extends AppCompatActivity {

    private Helper helper = new Helper();
    private SQLiteDatabase db;

    private EditText nameEdittext;
    private EditText priceEdittext;
    private EditText dateEdittext;
    private EditText typeEdittext;

    private String name;
    private String price;
    private String date;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        nameEdittext = (EditText) findViewById(R.id.name_edit_text);
        priceEdittext = (EditText) findViewById(R.id.price_edit_text);
        dateEdittext = (EditText) findViewById(R.id.date_edit_text);
        typeEdittext = (EditText) findViewById(R.id.type_edit_text);
    }

    public void addItem(View view) {

        name = nameEdittext.getText().toString();
        price = priceEdittext.getText().toString();
        date = dateEdittext.getText().toString();
        type = typeEdittext.getText().toString();

        db.execSQL("CREATE TABLE IF NOT EXISTS equipment(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, date VARCHAR, type VARCHAR);");
        db.execSQL("INSERT INTO equipment VALUES(NULL, '" + name + "','" + price + "','" + date + "','" + type + "');");
    }

}
