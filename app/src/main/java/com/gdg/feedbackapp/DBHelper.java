package com.gdg.feedbackapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    static String DB_Name = "gdgfeedback.db";
    static int DB_Version = 1;

    public static String Table_Name = "gdgfeedback";
    public static String ID_COL = "ID";
    public static String NAME_COL = "Name";
    public static String AGE_COL = "Age";
    public static String RAT_COL = "Rating";
    public static String QUALIFICATION_COL = "QUALIFICATION";
    public static String SUGGESTION_COL = "SUGGESTION";
    public static String OCCUPATION_COL = "OCCUPATION";

    public static  String query = "create table "+Table_Name+"("+ID_COL+" integer primary key autoincrement, "+NAME_COL+" text, " +
            ""+AGE_COL+" integer, "+RAT_COL+" integer" +
            ", "+QUALIFICATION_COL+" text, "+OCCUPATION_COL+" text, "+SUGGESTION_COL+" text)";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_Name, null, DB_Version);
    }

    public DBHelper(@Nullable Context context){
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertFeedback(GDGFeedback gf){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(NAME_COL, gf.name);
            cv.put(AGE_COL, gf.age);
            cv.put(RAT_COL, gf.rating);
            cv.put(QUALIFICATION_COL, gf.qualification);
            cv.put(OCCUPATION_COL, gf.occupation);
            cv.put(SUGGESTION_COL, gf.suggestion);
            db.insert(Table_Name, null, cv);
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public ArrayList<GDGFeedback> getAllFeedback(){
        ArrayList<GDGFeedback> gfList = new ArrayList<GDGFeedback>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("Select * from "+Table_Name, null);
            while(c.moveToNext()) {
                String name = c.getString(c.getColumnIndex(NAME_COL));
                int age = c.getInt(c.getColumnIndex(AGE_COL));
                String qual = c.getString(c.getColumnIndex(QUALIFICATION_COL));
                String occ = c.getString(c.getColumnIndex(OCCUPATION_COL));
                int rat = c.getInt(c.getColumnIndex(RAT_COL));
                String sug = c.getString(c.getColumnIndex(SUGGESTION_COL));
                GDGFeedback fg = new GDGFeedback(name, occ, rat, qual, sug, age, false);
                gfList.add(fg);
            }
        }catch (Exception ex){
            Log.e("ERROR", ex.toString());
        }
        return gfList;


    }



}
