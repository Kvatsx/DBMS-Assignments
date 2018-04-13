package com.example.yashi.gymmanagementsystem;

/**
 * Created by yashi on 07-Apr-18.
 */

public class Helper {

    public String dbName = "dbFile";

//    User Table Indexes

    public int users_id = 0;
    public int users_name = 1;
    public int users_email = 2;
    public int users_password = 3;
    public int users_join_date = 4;
    public int users_user_type = 5;

    public int totat_columns_users = 6;

//    Customers Table Indexes

    public int customers_id = 0;
    public int customers_user_id = 1;
    public int customers_membership_plan_id = 2;
    public int customers_address = 3;
    public int customers_age = 4;
    public int customers_weight = 5;

    public int totat_columns_customers = 6;

//    Staff Table Indexes

    public int staff_id = 0;
    public int staff_user_id = 1;
    public int staff_address = 2;
    public int staff_salary = 3;

    public int totat_columns_staff = 4;

//    Equipment Table Indexes

    public int equipment_id = 0;
    public int equipment_name = 1;
    public int equipment_price = 2;
    public int equipment_date = 3;
    public int equipment_type = 4;

    public int totat_columns_equipment = 5;

}
