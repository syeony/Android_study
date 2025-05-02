package com.ssafy.exercise.service.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ssafy.exercise.dto.Memo

class MemoDao(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    lateinit var db: SQLiteDatabase

    private val TABLE_NAME = "Memo"
    private val STUFF_ID = "_id"
    private val STUFF_ExerciseType = "exerciseType"
    private val STUFF_duration = "duration"
    private val STUFF_date = "date"
    private val STUFF_intensity = "intensity"
    private val STUFF_memo = "memo"
    private val STUFF_createAt = "createdAt"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                $STUFF_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $STUFF_ExerciseType TEXT NOT NULL,
                $STUFF_duration INTEGER NOT NULL,
                $STUFF_date LONG NOT NULL,
                $STUFF_intensity REAL NOT NULL,
                $STUFF_memo TEXT,
                $STUFF_createAt LONG NOT NULL
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
    }

    fun add(memo: Memo): Long {
        val values = ContentValues().apply {
            put(STUFF_ExerciseType, memo.exerciseType)
            put(STUFF_duration, memo.duration)
            put(STUFF_date, memo.date) // String 타입 넣음
            put(STUFF_intensity, memo.intensity)
            put(STUFF_memo, memo.memo)
            put(STUFF_createAt, memo.createdAt)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun search(id: Int): Memo? {
        val cursor = db.query(
            TABLE_NAME, null, "$STUFF_ID=?",
            arrayOf(id.toString()), null, null, null
        )
        return if (cursor.moveToFirst()) {
            val exerciseType = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_ExerciseType))
            val duration = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_duration))
            val date = cursor.getLong(cursor.getColumnIndexOrThrow(STUFF_date)) // 여기 수정
            val intensity = cursor.getFloat(cursor.getColumnIndexOrThrow(STUFF_intensity))
            val memoText = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_memo))
            val createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(STUFF_createAt))
            Memo(id, exerciseType, duration, date, intensity, memoText, createdAt)
        } else null.also {
            cursor.close()
        }
    }

    fun getList(): List<Memo> {
        val memoList = mutableListOf<Memo>()
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_ID))
            val exerciseType = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_ExerciseType))
            val duration = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_duration))
            val date = cursor.getLong(cursor.getColumnIndexOrThrow(STUFF_date))
            val intensity = cursor.getFloat(cursor.getColumnIndexOrThrow(STUFF_intensity))
            val memoText = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_memo))
            val createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(STUFF_createAt))
            memoList.add(Memo(id, exerciseType, duration, date, intensity, memoText, createdAt))
        }
        cursor.close()
        return memoList
    }

    fun update(memo: Memo): Int {
        val values = ContentValues().apply {
            put(STUFF_ExerciseType, memo.exerciseType)
            put(STUFF_duration, memo.duration)
            put(STUFF_date, memo.date)
            put(STUFF_intensity, memo.intensity)
            put(STUFF_memo, memo.memo)
            put(STUFF_createAt, memo.createdAt)
        }
        return db.update(TABLE_NAME, values, "$STUFF_ID=?", arrayOf(memo.id.toString()))
    }

    fun remove(id: Int): Int {
        return db.delete(TABLE_NAME, "$STUFF_ID=?", arrayOf(id.toString()))
    }

    //공통정렬함수 만든거임 밑에 1,2,3,4할려고
    private fun getSortedList(orderBy: String): List<Memo> {
        val memoList = mutableListOf<Memo>()
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, orderBy)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_ID))
            val exerciseType = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_ExerciseType))
            val duration = cursor.getInt(cursor.getColumnIndexOrThrow(STUFF_duration))
            val date = cursor.getLong(cursor.getColumnIndexOrThrow(STUFF_date))
            val intensity = cursor.getFloat(cursor.getColumnIndexOrThrow(STUFF_intensity))
            val memoText = cursor.getString(cursor.getColumnIndexOrThrow(STUFF_memo))
            val createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(STUFF_createAt))
            memoList.add(Memo(id, exerciseType, duration, date, intensity, memoText, createdAt))
        }
        cursor.close()
        return memoList
    }

    // 1. 운동종류 오름차순
    fun getListSortedByExerciseTypeAsc(): List<Memo> {
        return getSortedList("$STUFF_ExerciseType ASC")
    }

    // 2. 운동종류 내림차순
    fun getListSortedByExerciseTypeDesc(): List<Memo> {
        return getSortedList("$STUFF_ExerciseType DESC")
    }

    // 3. 운동일자 오름차순
    fun getListSortedByDateAsc(): List<Memo> {
        return getSortedList("$STUFF_date ASC")
    }

    // 4. 운동일자 내림차순
    fun getListSortedByDateDesc(): List<Memo> {
        return getSortedList("$STUFF_date DESC")
    }
}
