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
        if(resultSet.getCount() > 0) {
//            resultSet.moveToFirst();
//            userId = resultSet.getString(helper.users_id);
            customerDetailsText.setText(DatabaseUtils.dumpCursorToString(resultSet));
        }

    }
}
