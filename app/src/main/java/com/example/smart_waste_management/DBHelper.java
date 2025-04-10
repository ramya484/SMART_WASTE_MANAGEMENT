package com.example.smart_waste_management;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartWasteManagement.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_WASTE_REPORTS = "waste_reports";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_REPORT_ID = "report_id";
    private static final String COLUMN_REPORT_DETAILS = "report_details";
    private static final String COLUMN_COLLECTED = "collected";
    private static final String COLUMN_RECYCLED = "recycled";
    private static final String COLUMN_COLLECTION_DATE = "collection_date";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_ROLE + " TEXT" + ")";

        String CREATE_WASTE_REPORTS_TABLE = "CREATE TABLE " + TABLE_WASTE_REPORTS + "("
                + COLUMN_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_REPORT_DETAILS + " TEXT,"
                + COLUMN_COLLECTED + " INTEGER,"
                + COLUMN_RECYCLED + " INTEGER,"
                + COLUMN_COLLECTION_DATE + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_WASTE_REPORTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WASTE_REPORTS);
        onCreate(db);
    }

    // Method to add a new user
    public void addUser(String username, String password, String name, String location, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_ROLE, role);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USERNAME + "=?" + " AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    @SuppressLint("Range")
    public String getUserRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ROLE};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        String role = "";
        if (cursor != null && cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));
        }
        cursor.close();
        db.close();
        return role;
    }
    public void addWasteReport(String username, String reportDetails, int collected, int recycled, String collectionDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_REPORT_DETAILS, reportDetails);
        values.put(COLUMN_COLLECTED, collected);
        values.put(COLUMN_RECYCLED, recycled);
        values.put(COLUMN_COLLECTION_DATE, collectionDate);
        db.insert(TABLE_WASTE_REPORTS, null, values);
        db.close();
    }
    public List<String> getAllWasteReports() {
        List<String> wasteReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WASTE_REPORTS, new String[] {COLUMN_REPORT_DETAILS, COLUMN_COLLECTED, COLUMN_RECYCLED, COLUMN_COLLECTION_DATE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String reportDetails = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_DETAILS));
            @SuppressLint("Range") int collected = cursor.getInt(cursor.getColumnIndex(COLUMN_COLLECTED));
            @SuppressLint("Range") int recycled = cursor.getInt(cursor.getColumnIndex(COLUMN_RECYCLED));
            @SuppressLint("Range") String collectionDate = cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DATE)) == null ? "Not collected" : cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DATE));
            String report = "Report Details: " + reportDetails +
                    ", Collected: " + (collected == 1 ? "Yes" : "No") +
                    ", Recycled: " + (recycled == 1 ? "Yes" : "No") +
                    ", Date: " + (collectionDate.equals("Not collected") ? "" : collectionDate);
            wasteReports.add(report);
        }
        cursor.close();
        db.close();
        return wasteReports;
    }
    public List<String> getAllRecycleReports() {
        List<String> recycleReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WASTE_REPORTS, new String[] {COLUMN_REPORT_DETAILS, COLUMN_COLLECTED, COLUMN_RECYCLED, COLUMN_COLLECTION_DATE},
                COLUMN_COLLECTED + "=?" + " AND " + COLUMN_RECYCLED + "=?", null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String reportDetails = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_DETAILS));
            @SuppressLint("Range") int collected = cursor.getInt(cursor.getColumnIndex(COLUMN_COLLECTED));
            @SuppressLint("Range") int recycled = cursor.getInt(cursor.getColumnIndex(COLUMN_RECYCLED));
            @SuppressLint("Range") String collectionDate = cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DATE)) == null ? "Not collected" : cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DATE));
            String report = "Report Details: " + reportDetails +
                    ", Collected: " + (collected == 1 ? "Yes" : "No") +
                    ", Recycled: " + (recycled == 1 ? "Yes" : "No") +
                    ", Date: " + (collectionDate.equals("Not collected") ? "" : collectionDate);
            recycleReports.add(report);
        }
        cursor.close();
        db.close();
        return recycleReports;
    }
    public List<WasteReport> getUncollectedReports() {
        List<WasteReport> uncollectedReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WASTE_REPORTS, new String[] {COLUMN_REPORT_ID, COLUMN_REPORT_DETAILS, COLUMN_COLLECTED, COLUMN_RECYCLED, COLUMN_COLLECTION_DATE},
                COLUMN_COLLECTED + "=0" , null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String reportId = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_ID));
            @SuppressLint("Range") String reportDetails = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_DETAILS));
            @SuppressLint("Range") int collected = cursor.getInt(cursor.getColumnIndex(COLUMN_COLLECTED));
            @SuppressLint("Range") int recycled = cursor.getInt(cursor.getColumnIndex(COLUMN_RECYCLED));
            @SuppressLint("Range") String collectionDate = cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DATE)) == null ? "Not collected" : cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DATE));

            WasteReport report = new WasteReport(reportId, reportDetails, collected, recycled, collectionDate);
            uncollectedReports.add(report);
        }
        cursor.close();
        db.close();
        return uncollectedReports;
    }
    public List<WasteReport> getUnrecycledReports() {
        List<WasteReport> unrecycledReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WASTE_REPORTS, new String[] {COLUMN_REPORT_ID, COLUMN_REPORT_DETAILS, COLUMN_COLLECTED, COLUMN_RECYCLED, COLUMN_COLLECTION_DATE},
                COLUMN_COLLECTED + "=1" + " AND " +COLUMN_RECYCLED + "=0" , null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String reportId = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_ID));
            @SuppressLint("Range") String reportDetails = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_DETAILS));
            @SuppressLint("Range") int collected = cursor.getInt(cursor.getColumnIndex(COLUMN_COLLECTED));
            @SuppressLint("Range") int recycled = cursor.getInt(cursor.getColumnIndex(COLUMN_RECYCLED));
            @SuppressLint("Range") String collectionDate = cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DATE)) == null ? "Not collected" : cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DATE));

            WasteReport report = new WasteReport(reportId, reportDetails, collected, recycled, collectionDate);
            unrecycledReports.add(report);
        }
        cursor.close();
        db.close();
        return unrecycledReports;
    }
    public void updateCollect(String username, String reportDetails, int collected, int recycled, String collectionDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLLECTED, collected);
        values.put(COLUMN_RECYCLED, recycled);
        values.put(COLUMN_COLLECTION_DATE, collectionDate);
        String whereClause = COLUMN_USERNAME + "=? AND " + COLUMN_REPORT_DETAILS + "=?";
        String[] whereArgs = new String[]{username, reportDetails};
        db.update(TABLE_WASTE_REPORTS, values, whereClause, whereArgs);
        db.close();
    }
    public List<String> getHotspots() {
        List<String> hotspots = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT location, COUNT(location) AS count FROM " + TABLE_WASTE_REPORTS +
                " GROUP BY location HAVING count > 3";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex("location"));
            hotspots.add(location);
        }
        cursor.close();
        db.close();
        return hotspots;
    }
}
