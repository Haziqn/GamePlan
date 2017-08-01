package com.example.a15017523.gameplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15017523 on 1/8/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "object.db";
    public static final Integer DATABASE_VERSION = 9;
    public static final String TABLE_OBJECTS = "objects";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String ID = "ID";
    public static final String CAT_ID = "CAT_ID";
    public static final String CATEGORIES = "categories";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DATETIME = "datetime";
    public static final String REMINDER = "reminder";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_OBJECTS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CAT_ID INTEGER," +
                "TITLE TEXT," +
                "DESCRIPTION TEXT," +
                "DATETIME TEXT," +
                "REMINDER TEXT)");
        sqLiteDatabase.execSQL("create table " + TABLE_CATEGORIES + " (CAT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CATEGORIES TEXT)");
        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(CAT_ID, i);
            values.put(CATEGORIES, "Category " + i);
            sqLiteDatabase.insert(TABLE_CATEGORIES, null, values);
        }
        Log.i("info", "dummy records inserted");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(sqLiteDatabase);
    }

    public Cursor getAllData() {
        ArrayList<String> notes = new ArrayList<String>();

        String selectQuery = "SELECT * FROM " + TABLE_OBJECTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getAllCat() {
        ArrayList<String> categories = new ArrayList<String>();

        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public ArrayList<CATEGORIES> getCatById(String catId){
        ArrayList<CATEGORIES> al = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String [] cols = {CAT_ID, CATEGORIES};
        String condition = "CAT_ID= ?";
        String [] args = {catId};

        Cursor cursor = db.query(TABLE_CATEGORIES, cols, condition, args, null, null, null);
        if (cursor.moveToFirst()){
            do{
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                CATEGORIES categories = new CATEGORIES(id, name);
                al.add(categories);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return al;
    }

    public ArrayList<OBJECT> getObjByCat(String catId){
        ArrayList<OBJECT> al = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String [] cols = {ID, CAT_ID, TITLE, DESCRIPTION, DATETIME, REMINDER};
        String condition = "CAT_ID= ?";
        String [] args = {catId};

        Cursor cursor = db.query(TABLE_CATEGORIES, cols, condition, args, null, null, null);
        if (cursor.moveToFirst()){
            do{
                String id = cursor.getString(0);
                String cat_id = cursor.getString(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                String datetime = cursor.getString(4);
                String reminder = cursor.getString(5);
                OBJECT object = new OBJECT(id, cat_id, title, description, datetime, reminder);
                al.add(object);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return al;
    }

    public String[] getAllSpinnerContent(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select * from " + TABLE_CATEGORIES;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("CATEGORIES"));
                spinnerContent.add(word);
            }while(cursor.moveToNext());
        }
        cursor.close();

        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        return allSpinner;
    }

    public boolean insertData(int category, String title, String description, String datetime, String reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_ID, category);
        contentValues.put(TITLE, title);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(DATETIME, datetime);
        contentValues.put(REMINDER, reminder);
        long result = db.insert(TABLE_OBJECTS, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORIES, category);
        long result = db.insert(TABLE_CATEGORIES, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Integer deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_OBJECTS, condition, args);
        db.close();
        return result;
    }

    public Integer deleteCat(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = CAT_ID+ "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_CATEGORIES, condition, args);
        db.close();
        return result;
    }

    public int updateData(OBJECT object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_ID, object.getCategories());
        contentValues.put(TITLE, object.getTitle());
        contentValues.put(DESCRIPTION, object.getDescription());
        contentValues.put(DATETIME, object.getDatetime());
        contentValues.put(REMINDER, object.getReminder());
        String condition = ID + "= ?";
        String[] args = {String.valueOf(object.getId())};
        int result = db.update(TABLE_OBJECTS, contentValues, condition, args);
        db.close();
        return result;
    }

    public int updateCat(CATEGORIES categories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_ID, categories.getCAT_ID());
        contentValues.put(CATEGORIES, categories.getCATEGORIES());
        String condition = CAT_ID + "= ?";
        String[] args = {String.valueOf(categories.getCAT_ID())};
        int result = db.update(TABLE_CATEGORIES, contentValues, condition, args);
        db.close();
        return result;
    }
}
