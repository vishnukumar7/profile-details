package com.novatium.android.profiledetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.novatium.android.profiledetails.model.User;
import com.novatium.android.profiledetails.model.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String AGE = "age";
    public static final String GENDER = "gender";
    public static final String DOB = "date_of_birth";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String JOIN_DATE = "join_date";
    public static final String SUPERVISOR = "supervisor";
    public static final String MOBILE_NUMBER = "mobile_number";
    public static final String EMAIL_ID = "email_id";
    public static final String STATUS = "status";
    public static final String DESTINATION = "destination";
    public static final String LOCATION = "location";
    public static final String EMPLOYEE_PROFILE_TABLE_NAME = "employee_profile_table";
    public static final String EMPLOYEE_PROFILE_TABLE_DETAILS = "create table if not exists " + EMPLOYEE_PROFILE_TABLE_NAME + " ("
            + EMPLOYEE_ID + " text ,"
            + FIRST_NAME + " text ,"
            + LAST_NAME + " text ,"
            + AGE + " int ,"
            + DOB + " text ,"
            + GENDER + " text ,"
            + JOIN_DATE + " text ,"
            + SUPERVISOR + " text ,"
            + MOBILE_NUMBER + " int ,"
            + EMAIL_ID + " text ,"
            + STATUS + " text ,"
            + LOCATION + " text ,"
            + DESTINATION + " text "
            + ")";
    private Context context;

    public DBHandler(@Nullable Context context) {
        super(context, "profileDetails.db", null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EMPLOYEE_PROFILE_TABLE_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_PROFILE_TABLE_NAME);

        onCreate(db);
    }

    private boolean getCount(SQLiteDatabase db, String id) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + EMPLOYEE_PROFILE_TABLE_NAME + " WHERE " + EMPLOYEE_ID + "='" + id + "'", null);
        System.out.println("//count : " + cursor.getCount());

        return cursor.getCount() == 0;
    }

    public List<String> getAllSupervisor() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<String> list = new ArrayList<>();
        list.add("Admin");
        Cursor cursor = database.rawQuery("SELECT " + FIRST_NAME + "," + LAST_NAME + " FROM " + EMPLOYEE_PROFILE_TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(cursor.getColumnIndex(FIRST_NAME)) + " " + cursor.getString(cursor.getColumnIndex(LAST_NAME)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean getCheckId(String id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROFILE_TABLE_NAME + " WHERE " + EMPLOYEE_ID + " ='" + id + "'", null);
        return cursor.getCount() == 0;
    }

    public boolean insertPage(UserDetails userDetails) {
        long result = 0;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_ID, userDetails.getId());
        contentValues.put(FIRST_NAME, userDetails.getFirstName());
        contentValues.put(LAST_NAME, userDetails.getLastName());
        contentValues.put(AGE, userDetails.getAge());
        contentValues.put(GENDER, String.valueOf(userDetails.getGender()));
        contentValues.put(DOB, userDetails.getDOB());
        contentValues.put(JOIN_DATE, userDetails.getJoinDate());
        contentValues.put(DESTINATION, userDetails.getDestination());
        contentValues.put(LOCATION, userDetails.getLocation());
        contentValues.put(SUPERVISOR, userDetails.getSupervisorName());
        contentValues.put(MOBILE_NUMBER, userDetails.getMobileNumber());
        contentValues.put(EMAIL_ID, userDetails.getEmailId());
        contentValues.put(STATUS, userDetails.getStatus());
        if (getCount(database, userDetails.getId()))
            result = database.insert(EMPLOYEE_PROFILE_TABLE_NAME, null, contentValues);
        else
            Toast.makeText(context, "Employee Id Already Registered", Toast.LENGTH_LONG).show();
        database.close();
        return result != 0;
    }

    public List<String> getAllID() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + EMPLOYEE_ID + " FROM " + EMPLOYEE_PROFILE_TABLE_NAME, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(cursor.getColumnIndex(EMPLOYEE_ID)));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return list;
    }

    public UserDetails getUserDetails(String id) {
        UserDetails userDetails = new UserDetails();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROFILE_TABLE_NAME + " WHERE " + EMPLOYEE_ID + "='" + id + "'", null);
        cursor.moveToFirst();
        userDetails.setAge(cursor.getInt(cursor.getColumnIndex(AGE)));
        userDetails.setDestination(cursor.getString(cursor.getColumnIndex(DESTINATION)));
        userDetails.setDOB(cursor.getString(cursor.getColumnIndex(DOB)));
        userDetails.setEmailId(cursor.getString(cursor.getColumnIndex(EMAIL_ID)));
        userDetails.setFirstName(cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
        userDetails.setGender(cursor.getString(cursor.getColumnIndex(GENDER)));
        userDetails.setId(cursor.getString(cursor.getColumnIndex(EMPLOYEE_ID)));
        userDetails.setJoinDate(cursor.getString(cursor.getColumnIndex(JOIN_DATE)));
        userDetails.setLastName(cursor.getString(cursor.getColumnIndex(LAST_NAME)));
        userDetails.setLocation(cursor.getString(cursor.getColumnIndex(LOCATION)));
        userDetails.setMobileNumber(cursor.getLong(cursor.getColumnIndex(MOBILE_NUMBER)));
        userDetails.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
        userDetails.setSupervisorName(cursor.getString(cursor.getColumnIndex(SUPERVISOR)));
        cursor.close();
        database.close();
        return userDetails;
    }

    public ArrayList<UserDetails> getAllUser(String supervisor) {
        ArrayList<UserDetails> userDetailsArrayList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        if (supervisor == null)
            cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROFILE_TABLE_NAME, null);
        else
            cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROFILE_TABLE_NAME + " WHERE " + SUPERVISOR + "='" + supervisor + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserDetails userDetails = new UserDetails();
            String name = cursor.getString(cursor.getColumnIndex(FIRST_NAME)) + " " + cursor.getString(cursor.getColumnIndex(LAST_NAME));
            userDetails.setFirstName(name);
            userDetails.setId(cursor.getString(cursor.getColumnIndex(EMPLOYEE_ID)));
            userDetailsArrayList.add(userDetails);
            cursor.moveToNext();
        }
        database.close();
        cursor.close();
        return userDetailsArrayList;
    }

    public ArrayList<User> getUser(String supervisor) {
        ArrayList<User> userDetailsArrayList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        if (supervisor == null)
            cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROFILE_TABLE_NAME, null);
        else
            cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROFILE_TABLE_NAME + " WHERE " + SUPERVISOR + "='" + supervisor + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(FIRST_NAME)) + " "
                    + cursor.getString(cursor.getColumnIndex(LAST_NAME)) + ","
                    + cursor.getString(cursor.getColumnIndex(EMPLOYEE_ID));
            User userDetails = new User(name, Arrays.asList("admin"), cursor.getString(cursor.getColumnIndex(EMPLOYEE_ID)));
            userDetailsArrayList.add(userDetails);
            cursor.moveToNext();
        }
        database.close();
        cursor.close();
        return userDetailsArrayList;
    }

    public ArrayList<User> getUserChild(String supervisor) {
        ArrayList<User> userDetailsArrayList = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROFILE_TABLE_NAME + " WHERE " + SUPERVISOR + "='" + supervisor + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            userDetailsArrayList = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(FIRST_NAME)) + " " + cursor.getString(cursor.getColumnIndex(LAST_NAME));

            }
        }
        database.close();
        cursor.close();
        return userDetailsArrayList;
    }

    public void updateUser(UserDetails userDetails) {
        long result = 0;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_ID, userDetails.getId());
        contentValues.put(FIRST_NAME, userDetails.getFirstName());
        contentValues.put(LAST_NAME, userDetails.getLastName());
        contentValues.put(AGE, userDetails.getAge());
        contentValues.put(GENDER, String.valueOf(userDetails.getGender()));
        contentValues.put(DOB, userDetails.getDOB());
        contentValues.put(JOIN_DATE, userDetails.getJoinDate());
        contentValues.put(DESTINATION, userDetails.getDestination());
        contentValues.put(LOCATION, userDetails.getLocation());
        contentValues.put(SUPERVISOR, userDetails.getSupervisorName());
        contentValues.put(MOBILE_NUMBER, userDetails.getMobileNumber());
        contentValues.put(EMAIL_ID, userDetails.getEmailId());
        contentValues.put(STATUS, userDetails.getStatus());
        result = database.update(EMPLOYEE_PROFILE_TABLE_NAME, contentValues, EMPLOYEE_ID + "='" + userDetails.getId() + "'", null);
        if (result != 0)
            Toast.makeText(context, "Employee Id not Registered", Toast.LENGTH_LONG).show();
        database.close();
    }

    public void deleteUser(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        int result = database.delete(EMPLOYEE_PROFILE_TABLE_NAME, EMPLOYEE_ID + "='" + id + "'", null);
        if (result != 0)
            Toast.makeText(context, "User is not delete", Toast.LENGTH_LONG).show();

    }


}
