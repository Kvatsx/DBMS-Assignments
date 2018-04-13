package com.example.yashi.gymmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCustomerProfile extends AppCompatActivity {

    private Helper helper = new Helper();

    private SQLiteDatabase db;
    private String userId;

    private TextView nameText;
    private TextView emailText;
    private TextView userTypeText;
    private TextView addressText;
    private TextView ageText;
    private TextView weightText;
    private TextView membershipPlanText;

    private String name;
    private String email;
    private String userType;
    private String address;
    private String age;
    private String weight;
    private String membershipPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_profile);

        this.setTitle("View Profile");

        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        userId = this.getIntent().getStringExtra("userId");

        nameText = (TextView) findViewById(R.id.name_text);
        emailText = (TextView) findViewById(R.id.email_text);
        userTypeText = (TextView) findViewById(R.id.user_type_text);
        addressText = (TextView) findViewById(R.id.address_text);
        ageText = (TextView) findViewById(R.id.age_text);
        weightText = (TextView) findViewById(R.id.weight_text);
        membershipPlanText = (TextView) findViewById(R.id.membershipPlan_text);

        String query = "SELECT * FROM customers INNER JOIN users ON customers.user_id = users.id WHERE customers.user_id = " + userId;
        Cursor resultSet = db.rawQuery(query,null);

        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
//            addressText.setText(DatabaseUtils.dumpCursorToString(resultSet));
            name = resultSet.getString(6 + helper.users_name);
            email = resultSet.getString(6 + helper.users_email);
            userType = resultSet.getString(6 + helper.users_user_type);
            address = resultSet.getString(helper.customers_address);
            age = resultSet.getString(helper.customers_age);
            weight = resultSet.getString(helper.customers_weight);
            membershipPlan = resultSet.getString(helper.customers_membership_plan_id);


            addressText.setText("Address: " + address);
            nameText.setText("Name: " + name);
            emailText.setText("Email: " + email);
            userTypeText.setText("User Type: " + userType);
            ageText.setText("Age: " + age);
            weightText.setText("Weight: " + weight);
            membershipPlanText.setText("Membership Plan: " + membershipPlan);
        }
        else {
            Toast.makeText(this, "Please Update Profile.", Toast.LENGTH_SHORT).show();
        }

    }
}
