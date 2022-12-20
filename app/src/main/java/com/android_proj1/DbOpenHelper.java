package com.android_proj1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;


public class DbOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "android05-2.db";
    public static SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;


    public class DBHelper extends SQLiteOpenHelper {


        public DBHelper(@Nullable Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void setCSV() {
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");

            Log.d("Tag", "exportdir 하기 전");

            if(!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "csvname_2.csv");

            try {
                file.createNewFile();


                CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(file), "MS949"));
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor curCSV = db.rawQuery("SELECT title, summary FROM " + TableInfo.TABLE_NAME, null);
                csvWriter.writeNext(curCSV.getColumnNames());
                while(curCSV.moveToNext()) {

                    String arrStr[] = {curCSV.getString(0), curCSV.getString(1)};
                    csvWriter.writeNext(arrStr);
                }
                csvWriter.close();
                curCSV.close();

            } catch (Exception sqlEx) {
                Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
            }

        }




        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TableInfo.TABLE_NAME + " ("
                        + TableInfo.COLUMN_TITLE + " TEXT PRIMARY KEY,"
                        + TableInfo.COLUMN_LINK + " TEXT,"
                        + TableInfo.COLUMN_SUMMARY + " TEXT,"
                        + TableInfo.COLUMN_SCORE + " TEXT,"
                        + TableInfo.COLUMN_AUTHOR + " TEXT,"
                        + TableInfo.COLUMN_IMG + " TEXT,"
                        + TableInfo.COLUMN_RANK + " INTEGER,"
                        + TableInfo.COLUMN_CATEGORY + " TEXT,"
                        + TableInfo.COLUMN_TOTALEPISODE + " TEXT,"
                        + TableInfo.COLUMN_COMMENTTOTALCOUNT + " TEXT,"
                        + TableInfo.COLUMN_PUBLISHER + " TEXT);";


        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TableInfo.TABLE_NAME;


        @Override
        public void onCreate(SQLiteDatabase db) {

            if (db != null) {
                try {
                    db.execSQL(SQL_CREATE_TABLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(SQL_DELETE_TABLE);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context) {
        this.context = context;
    }


    public DbOpenHelper open() throws SQLException {
        dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void create() {
        dbHelper.onCreate(db);
    }

    public void close() {
        db.close();
    }


    public void runCSV() {
        dbHelper.setCSV();
    }


    // Insert DB
    public long insertTop100Search(ContentValues values) {

        return db.insert(TableInfo.TABLE_NAME, null, values);
    }

/*    // Insert DB
    public long insertTop100Search(String title, String link, String summary, String score, String author, String img, String rank, String category) {

        ContentValues values = new ContentValues();
        values.put(TableInfo.COLUMN_TITLE, title);
        values.put(TableInfo.COLUMN_LINK, link);
        values.put(TableInfo.COLUMN_SUMMARY, summary);
        values.put(TableInfo.COLUMN_SCORE, score);
        values.put(TableInfo.COLUMN_AUTHOR, author);
        values.put(TableInfo.COLUMN_IMG, img);
        values.put(TableInfo.COLUMN_RANK, rank);
        values.put(TableInfo.COLUMN_CATEGORY, category);


        return db.insert(TableInfo.TABLE_NAME, null, values);
    }*/

/*    public long insertNovelINfo(String title, String totalEpisode, String commentTotalCount, String publisher, String category) {

        ContentValues values = new ContentValues();
        values.put(TableInfo.COLUMN_TITLE, title);
        values.put(TableInfo.COLUMN_TOTALEPISODE, totalEpisode);
        values.put(TableInfo.COLUMN_COMMENTTOTALCOUNT, commentTotalCount);
        values.put(TableInfo.COLUMN_PUBLISHER, publisher);
        values.put(TableInfo.COLUMN_CATEGORY, category);

        return db.insert(TableInfo.TABLE_NAME, null, values);
    }*/


    //Replace DB
    // 데이터가 없으면 insert, 데이터가 있으면 update
    public boolean replaceTop100(String title, String link, String summary, String score, String author, String img, Integer rank, String category) {
        ContentValues values = new ContentValues();
        values.put(TableInfo.COLUMN_TITLE, title);
        values.put(TableInfo.COLUMN_LINK, link);
        values.put(TableInfo.COLUMN_SUMMARY, summary);
        values.put(TableInfo.COLUMN_SCORE, score);
        values.put(TableInfo.COLUMN_AUTHOR, author);
        values.put(TableInfo.COLUMN_IMG, img);
        values.put(TableInfo.COLUMN_RANK, rank);
        values.put(TableInfo.COLUMN_CATEGORY, category);
        return db.replace(TableInfo.TABLE_NAME, null, values) > 0;
    }


    // Update DB
    public boolean updateTop100(ContentValues values, String title) {

        String[] params = {title};
        return db.update(TableInfo.TABLE_NAME, values, "title=?", params) > 0;
    }


    // Update (init) rank to 0 DB
    public boolean updateRankColum() {


        ContentValues contentValues = new ContentValues();

        try {
            Cursor cursor = selectColumns();

            if (cursor != null) {

                while (cursor.moveToNext()) {
                    contentValues.put(TableInfo.COLUMN_RANK, -1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return db.update(TableInfo.TABLE_NAME, contentValues, null, null) > 0;
    }


    public boolean updateNovelINfo(String title, String totalEpisode, String commentTotalCount, String publisher, String category) {

        ContentValues values = new ContentValues();
        values.put(TableInfo.COLUMN_TITLE, title);
        values.put(TableInfo.COLUMN_TOTALEPISODE, totalEpisode);
        values.put(TableInfo.COLUMN_COMMENTTOTALCOUNT, commentTotalCount);
        values.put(TableInfo.COLUMN_PUBLISHER, publisher);
        values.put(TableInfo.COLUMN_CATEGORY, category);

        return db.update(TableInfo.TABLE_NAME, values, TableInfo.COLUMN_TITLE, null) > 0;
    }


    // 0으로 초기화 하기 위한 Select Title DB
    public Cursor selectColumns() {
        String[] titleArr = {TableInfo.COLUMN_TITLE};
        return db.query(TableInfo.TABLE_NAME, titleArr, null, null, null, null, null);
    }


    // Select Title DB
    public Cursor selectTitle(String title) {


        Cursor cursor = db.rawQuery("SELECT " + TableInfo.COLUMN_TITLE + " FROM " + TableInfo.TABLE_NAME + " WHERE title LIKE '%" + title + "' ORDER BY " + TableInfo.COLUMN_SCORE + " DESC;", null);

        return cursor;


    }

    // Select Top100 data
    public Cursor selectTop100(String category) {


        Cursor cursor = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE category = '" + category + "' and rank > '0' ORDER BY " + TableInfo.COLUMN_CATEGORY + " DESC, " + TableInfo.COLUMN_RANK + " ASC;", null);
        return cursor;
    }

    // Select Search data
    public Cursor selectSearch(String title) {

        Cursor cursor = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE title LIKE '%" + title + "%' ORDER BY " + TableInfo.COLUMN_SCORE + " DESC;", null);
        return cursor;
    }


    // Select Search data
    public Cursor selectPublisher(String title) {

        Cursor cursor = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE title LIKE '%" + title + "%' ORDER BY " + TableInfo.COLUMN_SCORE + " DESC;", null);
        return cursor;
    }



    // sort by column
    public Cursor sortColumn(String sort) {
        Cursor c = db.rawQuery("SELECT * FROM usertable ORDER BY " + sort + ";", null);
        return c;
    }


    // Delete All
    public void deleteAllColumns() {
        db.delete(TableInfo.TABLE_NAME, null, null);
    }

    // Delete DB
    public boolean deleteColumn(String title) {
        return db.delete(TableInfo.TABLE_NAME, TableInfo.COLUMN_TITLE, null) > 0;
    }


}

