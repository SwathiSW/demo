package com.example.gpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

import java.util.UUID;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "names.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table Names(id TEXT primary key, name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists Names");
    }

    public boolean insert(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("id", UUID.randomUUID().toString());
        c.put("name", name);
        long res = db.insert("Names", null, c);

        if(res == -1){
            return  false;
        }
        else{
            return  true;
        }

    }

    public Boolean delete(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cur = DB.rawQuery("Select * from Names  where name=?", new String[] {name});

        if(cur.getCount() >0){

            long res = DB.delete("Names", "name=?", new String[] {name});

            if(res == -1){
                return false;
            }
            else{
                return true;
            }

        }
        else{
            return false;
        }

    }
    public Cursor view(){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cur = db.rawQuery("select * from Names", null);

        return  cur;

    }
}

