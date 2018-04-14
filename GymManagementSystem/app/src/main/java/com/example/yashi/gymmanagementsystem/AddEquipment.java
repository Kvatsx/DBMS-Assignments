package com.example.yashi.gymmanagementsystem;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddEquipment extends AppCompatActivity {

    private Helper helper = new Helper();
    private SQLiteDatabase db;

    private EditText nameEdittext;
    private EditText priceEdittext;
    private EditText dateEdittext;
    private Spinner quantitySpinner;

    private String name;
    private String price;
    private String date;
    private String quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        nameEdittext = (EditText) findViewById(R.id.name_edit_text);
        priceEdittext = (EditText) findViewById(R.id.price_edit_text);
        dateEdittext = (EditText) findViewById(R.id.date_edit_text);
        quantitySpinner = (Spinner) findViewById(R.id.quantity_spinner);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        quantitySpinner.setAdapter(adapter);
        quantitySpinner.setSelection(0);
    }

    public void addItem(View view) {

        name = nameEdittext.getText().toString();
        price = priceEdittext.getText().toString();
        date = dateEdittext.getText().toString();
        quantity = quantitySpinner.getSelectedItem().toString();

        db.execSQL("CREATE TABLE IF NOT EXISTS equipment(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, date VARCHAR, quantity VARCHAR);");
        db.execSQL("INSERT INTO equipment VALUES(NULL, '" + name + "','" + price + "','" + date + "','" + quantity + "');");
    }

}
