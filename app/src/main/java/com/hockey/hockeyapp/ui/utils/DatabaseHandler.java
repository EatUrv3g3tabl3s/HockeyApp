package com.hockey.hockeyapp.ui.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hockey.hockeyapp.model.ShotLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 7/15/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //ALL STATIC VARIABLES
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "HockeyAppManager";

    //Table Name
    private static final String TABLE_SHOTLOG = "ShotLog";

    //Shot Log Column names
    private static final String KEY_SHOTTYPE = "ShotType";
    private static final String KEY_SHOTCOUNT = "ShotCount";

    public DatabaseHandler(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_SHOTLOG_TABLE = "CREATE TABLE " + TABLE_SHOTLOG + "(" + KEY_SHOTTYPE + " TEXT PRIMARY KEY," + KEY_SHOTCOUNT + " INTEGER" +")" ;

       db.execSQL(CREATE_SHOTLOG_TABLE);

    }

    //Upgrading Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_SHOTLOG);

        //create tables again
        onCreate(db);
    }

    //Adding shot typs
    public void addShotType(ShotLog shotLog)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SHOTCOUNT, shotLog.get_shotCount());
        values.put(KEY_SHOTTYPE, shotLog.get_shotType());


        //Inserting Row

        db.insert(TABLE_SHOTLOG, null, values);
        db.close();
    }

    //Getting single shot type
    public ShotLog getShotLog(String shotType)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHOTLOG, new String[] { KEY_SHOTTYPE, KEY_SHOTCOUNT},
                KEY_SHOTTYPE + "=?", new String[]{String.valueOf(shotType) }, null, null, null,null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        ShotLog shotLog = new ShotLog(cursor.getString(0), Integer.parseInt(cursor.getString(1)));
        return shotLog;
    }

    //getting all shot types
    public List<ShotLog> getAllShotLogs()
    {
        List<ShotLog> shotLogList = new ArrayList<ShotLog>();

        //select all query
        String selectQuery = "SELECT * FROM " + TABLE_SHOTLOG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping thru all rows adding to list
        if(cursor.moveToFirst())
        {
            do{
                ShotLog shotLog = new ShotLog();
                shotLog.set_shotType(cursor.getString(0));
                shotLog.set_shotCount(Integer.parseInt(cursor.getString(1)));

                //Adding to List
                shotLogList.add(shotLog);

            }while (cursor.moveToNext());
        }

        return shotLogList;
    }

    public void updateShotLog(ShotLog shotLog)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SHOTCOUNT, shotLog.get_shotCount());

        db.update(TABLE_SHOTLOG, values, KEY_SHOTTYPE + " =?", new String[]{shotLog.get_shotType()});

    }

}
