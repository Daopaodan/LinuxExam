package com.example.Student.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.Student.bean.StudentBean;
import com.example.Student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;


public class MyHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    //创建数据库
    public MyHelper(@Nullable Context context) {
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERION);
        sqLiteDatabase = this.getWritableDatabase();
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DBUtils.DATABASE_TABLE + "("+DBUtils.Student_ID+" integer primary key ,"+ DBUtils.Student_NAME + " VARCHAR(200)," + DBUtils.Student_Age+ " VARCHAR(200) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //添加数据
    public boolean insertData(String name, String age,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.Student_NAME, name);
        contentValues.put(DBUtils.Student_Age, age);
        return db.insert(DBUtils.DATABASE_TABLE, null, contentValues) > 0;
    }

    //删除数据
    public boolean deleteData(String id,SQLiteDatabase db) {
        String sql = DBUtils.Student_ID + "=?";
        String[] contentValuesArray = new String[]{String.valueOf(id)};
        return db.delete(DBUtils.DATABASE_TABLE, sql, contentValuesArray) > 0;
    }

    //修改数据
    public boolean updateData(String id, String name, String age,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.Student_NAME, name);
        contentValues.put(DBUtils.Student_Age, age);
        String sql = DBUtils.Student_ID + "=?";
        String[] strings = new String[]{id};
        return db.update(DBUtils.DATABASE_TABLE, contentValues, sql, strings) > 0;
    }



    //查询数据
    public List<StudentBean> query(SQLiteDatabase db) {
        List<StudentBean> list = new ArrayList<StudentBean>();
        Cursor cursor = db.query(DBUtils.DATABASE_TABLE, null, null, null, null, null, DBUtils.Student_ID + " desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                StudentBean studentInfo = new StudentBean();
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBUtils.Student_ID)));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.Student_NAME));
                String age = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.Student_Age));
                studentInfo.setId(id);
                studentInfo.setName(name);
                studentInfo.setAge(age);
                list.add(studentInfo);
//                getHttp();
            }
            cursor.close();
        }
        return list;
    }
}
