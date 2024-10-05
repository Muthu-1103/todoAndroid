package com.example.todoassignment.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todoassignment.Model.todoModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="TODO_DB";
    private static final String TB_NAME="TODO_TB";
    private static final String COL_1="ID";
    private static final String COL_2="TASK";
    private static final String COL_3="STATUS";
    private SQLiteDatabase db;
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TB_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
        onCreate(db);
    }
    public void insertTask(todoModel model){
        db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(COL_2,model.getTask());
        c.put(COL_3,0);
        db.insert(TB_NAME,null,c);
    }
    public void updateTask(int id,String task){
        db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(COL_2,task);
        db.update(TB_NAME,c,"ID=?",new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id,int st){
        db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(COL_2,st);
        db.update(TB_NAME,c,"ID=?",new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        db=this.getWritableDatabase();
        db.delete(TB_NAME,"ID=?",new String[]{String.valueOf(id)});
    }
    public List<todoModel> getAllTask(){
        db=this.getWritableDatabase();
        Cursor c=null;
        List<todoModel>modelList=new ArrayList<>();
        db.beginTransaction();
        try{
            c=db.query(TB_NAME,null,null,null,null,null,null);
            if(c!=null){
                if(c.moveToFirst()){
                    do{
                        todoModel task=new todoModel();
                        task.setId(c.getInt(c.getColumnIndexOrThrow(COL_1)));
                        task.setTask(c.getString(c.getColumnIndexOrThrow(COL_2)));
                        task.setStatus(c.getInt(c.getColumnIndexOrThrow(COL_3)));
                        modelList.add(task);
                    }while(c.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }
}
