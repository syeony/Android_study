package com.ssafy.ui6.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val TAG = "DBHelper_싸피"
const val TABLE = "mytable"
class DBHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private lateinit var db: SQLiteDatabase

    //생성
    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 쿼리
        val query: String =
            "CREATE TABLE if not exists $TABLE ( _id integer primary key autoincrement, txt text);"
        db.execSQL(query)
    }

    //업그레이드
    //  upgrade 가 필요한 경우 기존 테이블 drop 후 onCreate로 새롭게 생성
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE if exists $TABLE"
        db.execSQL(sql)
        onCreate(db)
    }

    //열기
    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        this.db = db
        Log.d(TAG, "onOpen: database 준비 완료")
    }
}