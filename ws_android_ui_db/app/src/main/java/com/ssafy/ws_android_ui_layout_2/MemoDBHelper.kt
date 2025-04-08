package com.ssafy.ws_android_ui_layout_2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val TAG = "DBHelper_싸피"
const val TABLE = "mytable"
class MemoDBHelper (
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private lateinit var db: SQLiteDatabase


    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
        CREATE TABLE IF NOT EXISTS $TABLE (
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT,
            content TEXT,
            regDate TEXT,
            tenMinAlarm INTEGER,
            thirtyMinAlarm INTEGER
        );
    """.trimIndent()

        db?.execSQL(query)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE if exists $TABLE"
        if (db != null) {
            db.execSQL(sql)
        }
        onCreate(db)
    }

    //열기
    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        this.db = db
        Log.d(TAG, "onOpen: database 준비 완료")
    }

}