package com.example.todolist_app


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "testdb", null ,1) {
   override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "create table TODO_TB(_id integer primary key autoincrement," +
                    "todo not null," +
                    "todo2 not null," +
                    "data_time text DEFAULT (datetime('now', 'localtime')))"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}