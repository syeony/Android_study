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

    // DB 선언
    lateinit var db: SQLiteDatabase

    private val TABLE_NAME = "Stuff"
    private val STUFF_ID = "_id"
    private val STUFF_NAME = "name"
    private val STUFF_CNT = "count"
    private val STUFF_REGDATE = "regDate"
    private val STUFF_RATING = "rating"   // ⭐ 추가된 rating 컬럼

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE if not exists $TABLE_NAME (
                $STUFF_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $STUFF_NAME VARCHAR(50),
                $STUFF_CNT INTEGER,
                $STUFF_REGDATE VARCHAR(50),
                $STUFF_RATING INTEGER
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        this.db = db
        Log.d(TAG, "onOpen: database 준비 완료")
    }

    // 물품 등록
    fun add(stuff: Stuff): Long {
        val values = ContentValues().apply {
            put(STUFF_NAME, stuff.name)
            put(STUFF_CNT, stuff.count)
            put(STUFF_REGDATE, stuff.regDate)
            put(STUFF_RATING, stuff.rating)    // ⭐ rating 추가
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
            val regDate = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_REGDATE))
            val rating = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_RATING)) // ⭐ rating 가져오기
            Stuff(id, name, count, regDate, rating)
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
            val regDate = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_REGDATE))
            val rating = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_RATING)) // ⭐ rating 가져오기
            stuffList.add(Stuff(id, name, count, regDate, rating))
        }
        cursor.close()
        return stuffList
    }

    // 물품 정보 수정
    fun update(stuff: Stuff): Int {
        val values = ContentValues().apply {
            put(STUFF_NAME, stuff.name)
            put(STUFF_CNT, stuff.count)
            put(STUFF_REGDATE, stuff.regDate)
            put(STUFF_RATING, stuff.rating)    // ⭐ 수정할 때도 rating 포함
        }
        return db.update(TABLE_NAME, values, "$STUFF_ID=?", arrayOf(stuff.id.toString()))
    }

    // 물품 삭제
    fun remove(id: Int): Int {
        return db.delete(TABLE_NAME, "$STUFF_ID=?", arrayOf(id.toString()))
    }

    // 이름 일부로 검색하는 함수
    fun searchByName(namePart: String): List<Stuff> {
        val stuffList = mutableListOf<Stuff>()
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$STUFF_NAME LIKE ?",
            arrayOf("%$namePart%"),
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_NAME))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_CNT))
            val regDate = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_REGDATE))
            val rating = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_RATING))
            stuffList.add(Stuff(id, name, count, regDate, rating))
        }
        cursor.close()
        return stuffList
    }

    // count가 높은 순으로 정렬하여 리턴하는 함수
    fun getListSortedByCount(): List<Stuff> {
        val stuffList = mutableListOf<Stuff>()
        val cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$STUFF_CNT DESC"
        )
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_NAME))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_CNT))
            val regDate = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_REGDATE))
            val rating = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_RATING))
            stuffList.add(Stuff(id, name, count, regDate, rating))
        }
        cursor.close()
        return stuffList
    }
}
