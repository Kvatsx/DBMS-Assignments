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

//    Customer Table Indexes

    public int customers_id = 0;
    public int customers_user_id = 1;
    public int customers_membership_plan_id = 2;
    public int customers_address = 3;
    public int customers_age = 4;
    public int customers_weight = 5;


}
