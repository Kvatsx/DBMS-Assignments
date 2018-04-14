package com.example.yashi.gymmanagementsystem;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewAllCustomers extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private String userId;

    private TextView customerDetailsText;

    private String staffDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_customers);

        this.setTitle("All Cusutomer Details");

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        String query = "SELECT * FROM users INNER JOIN customers ON users.id = customers.user_id WHERE users.user_type = 'Customer'";

        customerDetailsText = (TextView) findViewById(R.id.customer_details);

        Cursor resultSet = db.rawQuery(query,null);
        String tableString = "";

        if (resultSet.getCount() > 0 ){
            resultSet.moveToFirst();
            String[] columnNames2 = resultSet.getColumnNames();

            do {
                for (String name: columnNames2) {
                    if ( name.equals("name") )
                    {
                        tableString += String.format("%s: %s\n", "Name", resultSet.getString(resultSet.getColumnIndex(name)));
                    }
                    else if ( name.equals("age") )
                    {
                        tableString += String.format("%s: %s\n", "Age", resultSet.getString(resultSet.getColumnIndex(name)));
                    }
                    else if ( name.equals("weight") )
                    {
                        tableString += String.format("%s: %s\n", "Weight", resultSet.getString(resultSet.getColumnIndex(name)));
                    }
                    else if ( name.equals("email") )
                    {
                        tableString += String.format("%s: %s\n", "Email ID", resultSet.getString(resultSet.getColumnIndex(name)));
                    }
                    else if ( name.equals("join_date"))
                    {
                        tableString += String.format("%s: %s\n", "Joining Date", resultSet.getString(resultSet.getColumnIndex(name)));
                    }
                    else if ( name.equals("address"))
                    {
                        tableString += String.format("%s: %s\n", "Address", resultSet.getString(resultSet.getColumnIndex(name)));
                    }
                    else if ( name.equals("membership_plan_id"))
                    {
                        tableString += String.format("%s: %s\n", "Membership Plan", resultSet.getString(resultSet.getColumnIndex(name)));
                    }
                }
                tableString += "\n\n";

            } while (resultSet.moveToNext());
            customerDetailsText.setText(tableString);
        }

    }
}
