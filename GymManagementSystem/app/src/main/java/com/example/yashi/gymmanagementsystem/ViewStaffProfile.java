package com.example.yashi.gymmanagementsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewStaffProfile extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private String userId;

    private TextView nameText;
    private TextView emailText;
    private TextView userTypeText;
    private TextView addressText;
    private TextView salaryText;

    private String name;
    private String email;
    private String userType;
    private String address;
    private String salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_staff_profile);

        this.setTitle("View Profile");

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        userId = this.getIntent().getStringExtra("userId");

        nameText = (TextView) findViewById(R.id.name_text);
        emailText = (TextView) findViewById(R.id.email_text);
        userTypeText = (TextView) findViewById(R.id.user_type_text);
        addressText = (TextView) findViewById(R.id.address_text);
        salaryText = (TextView) findViewById(R.id.salary_text);

        String query = "SELECT * FROM staff INNER JOIN users ON staff.user_id = users.id WHERE staff.user_id = " + userId;
        Cursor resultSet = db.rawQuery(query,null);

        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
//            addressText.setText(DatabaseUtils.dumpCursorToString(resultSet));
            name = resultSet.getString(helper.totat_columns_staff + helper.users_name);
            email = resultSet.getString(helper.totat_columns_staff + helper.users_email);
            userType = resultSet.getString(helper.totat_columns_staff + helper.users_user_type);
            address = resultSet.getString(helper.staff_address);
            salary = resultSet.getString(helper.staff_salary);

            nameText.setText("Name: " + name);
            emailText.setText("Email: " + email);
            userTypeText.setText("User Type: " + userType);
            addressText.setText("Address: " + address);
            salaryText.setText("Salary: " + salary);
        }
        else {
            Toast.makeText(this, "Please Update Profile.", Toast.LENGTH_SHORT).show();
        }
    }
}
