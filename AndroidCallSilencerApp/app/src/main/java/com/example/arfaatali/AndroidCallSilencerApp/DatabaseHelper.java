package com.example.arfaatali.AndroidCallSilencerApp;

/**
 * Created by Arfaat Ali on 2/16/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="first1.DB";
    public static final String TABLE_NAME="myTable";
    public static final String ID="id";
    public static final String NAME="name";
    public static final String TIME="tme";
    public static final String NUMBER="data";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
}
@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE myTable(ID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,tme TEXT,data VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if it EXISTS" + TABLE_NAME);
        onCreate(db);
    }
    public Boolean insertData(String name,String time, String number){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(TIME,time);
        contentValues.put(NUMBER, number);
        long result=  db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;

        }else {
            return true;
        }
    }public Integer deletedata(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"name=?",new String[] {name});


    }

}
