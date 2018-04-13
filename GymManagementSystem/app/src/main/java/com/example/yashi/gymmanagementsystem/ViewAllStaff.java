package com.example.yashi.gymmanagementsystem;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewAllStaff extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private String userId;

    private TextView staffDetailsText;

    private String staffDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_staff);

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

//        String query = "SELECT * FROM customers INNER JOIN users ON customers.user_id = users.id WHERE customers.user_id = " + userId;
        String query = "SELECT * FROM users INNER JOIN staff ON users.id = staff.user_id WHERE users.user_type = 'Staff'";

        staffDetailsText = (TextView) findViewById(R.id.staff_details);

        Cursor resultSet = db.rawQuery(query,null);
        if(resultSet.getCount() > 0) {
//            resultSet.moveToFirst();
//            userId = resultSet.getString(helper.users_id);
            staffDetailsText.setText(DatabaseUtils.dumpCursorToString(resultSet));
        }
    }
}
