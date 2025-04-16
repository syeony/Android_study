package com.ssafy.cleanstore.service.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ssafy.cleanstore.dto.Stuff

private const val TAG = "StuffDao_싸피"
class StuffDao(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    // 테이블 생성
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""CREATE TABLE if not exists $TABLE_NAME (
            $STUFF_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $STUFF_NAME VARCHAR(50),
            $STUFF_CNT INTEGER
        )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) { //테이블 삭제 후 생성
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        this.db = db
        Log.d(TAG, "onOpen: database 준비 완료")
    }

    //DB 선언부
    lateinit var db: SQLiteDatabase

    private val TABLE_NAME = "Stuff"
    private val STUFF_ID = "_id"
    private val STUFF_NAME = "name"
    private val STUFF_CNT = "count"

    // 물품 CRUD 구현 !!!

    // 물품 등록
    fun add(stuff: Stuff): Long {
        val values = ContentValues().apply {
            put(STUFF_NAME, stuff.name)
            put(STUFF_CNT, stuff.count)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    // 특정 물품(id 기준) 조회
    fun search(id: Int): Stuff? {
        val cursor = db.query(
            TABLE_NAME, null, "$STUFF_ID=?",
            arrayOf(id.toString()), null, null, null
        )
        return if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_NAME))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_CNT))
            Stuff(id, name, count)
        } else null.also {
            cursor.close()
        }
    }

    // 전체 물품 조회
    fun getList(): List<Stuff> {
        val stuffList = mutableListOf<Stuff>()
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_NAME))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_CNT))
            stuffList.add(Stuff(id, name, count))
        }
        cursor.close()
        return stuffList
    }

    // 물품 정보 수정
    fun update(stuff: Stuff): Int {
        val values = ContentValues().apply {
            put(STUFF_NAME, stuff.name)
            put(STUFF_CNT, stuff.count)
        }
        return db.update(TABLE_NAME, values, "$STUFF_ID=?", arrayOf(stuff.id.toString()))
    }

    // 물품 삭제
    fun remove(id: Int): Int {
        return db.delete(TABLE_NAME, "$STUFF_ID=?", arrayOf(id.toString()))
    }
}
