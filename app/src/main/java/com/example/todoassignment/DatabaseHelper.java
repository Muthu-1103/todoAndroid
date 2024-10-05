package com.example.todoassignment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="login.db";
    private static final String COL_1="ID";
    private static final String COL_2="USERNAME";
    private static final String COL_3="PASSWORD";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }
    public boolean insertData(String username, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(COL_2,username.trim());
        c.put(COL_3,password.trim());
        long res=db.insert("Users",null,c);
        return res!=-1;
    }
    public String checkLogin(String username, String Password){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns={COL_2};
        String selection="USERNAME=? AND PASSWORD=?";
        String[] selectionArgs={username.trim(),Password.trim()};
        String res=null;
        Cursor c=db.query("Users",columns,selection,selectionArgs,null,null,null);
        if(c!=null && c.moveToFirst()){
            res=c.getString(c.getColumnIndexOrThrow(COL_2));
            c.close();
        }
        return res;
    }
}
