package com.example.yashi.gymmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewCustomerExtention extends AppCompatActivity {
    public static final String TAG = "ViewCustomerExtension";
    private Helper helper = new Helper();

    private SQLiteDatabase db;

    private Spinner membershipPlanSpinner;

//    private String ageLimit;
    private String membershipPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_extension);
        Log.d(TAG, "View Customer Extension Created.");
        System.out.println("OnCreate Done");
        db = openOrCreateDatabase(helper.dbName,MODE_PRIVATE,null);

        membershipPlanSpinner = (Spinner) findViewById(R.id.membership_plan);

        String[] membershipRange = {"All", "1 Year", "2 Year", "3 Year", "5 Year"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, membershipRange);
        membershipPlanSpinner.setAdapter(adapter2);
        membershipPlanSpinner.setSelection(0);

    }

    public void View_Customer(View view)
    {
        membershipPlan = membershipPlanSpinner.getSelectedItem().toString();

        String query = "SELECT * FROM users INNER JOIN customers ON users.id = customers.user_id INNER JOIN membership_plan ON customers.membership_plan_id = membership_plan.id WHERE users.user_type = 'Customer' AND customers.membership_plan_id";

        String num;
        if ( membershipPlan.equals("All"))
        {
            query = "SELECT * FROM users INNER JOIN customers ON users.id = customers.user_id INNER JOIN membership_plan ON customers.membership_plan_id = membership_plan.id";
        }
        else if ( membershipPlan.equals("1 Year") )
        {
            num = " = 1";
            query += num;
        }
        else if ( membershipPlan.equals("2 Year") )
        {
            num = " = 2";
            query += num;
        }
        else if ( membershipPlan.equals("3 Year") )
        {
            num = " = 3";
            query += num;
        }
        else
        {
            num = " = 4";
            query += num;
        }


        Intent allcustomerIntent = new Intent(ViewCustomerExtention.this, ViewAllCustomers.class);
        allcustomerIntent.putExtra("query",query);
        startActivity(allcustomerIntent);
    }
}
