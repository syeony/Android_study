package com.ssafy.ws_android_ui_layout_2

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log

private const val TAG = "MemoDBActions_싸피"
class MemoDBActions(private val db: SQLiteDatabase) {

    //getCount함수 때문에 만듦
    private var memos = 3

    fun insertMemo(dto: MemoDto): Long {
        val contentValues = ContentValues().apply {

            put("title", dto.title)
            put("content", dto.content)
            put("regDate", dto.regDate)
            put("tenMinAlarm", if (dto.tenMinAlarm) 1 else 0)
            put("thirtyMinAlarm", if (dto.thirtyMinAlarm) 1 else 0)
        }

        db.beginTransaction()
        val insertedId = db.insert(TABLE, null, contentValues)

        return if (insertedId != -1L) {
            db.setTransactionSuccessful()
            insertedId  // 삽입된 행의 ID 반환
        } else {
            -1L  // 삽입 실패 시 -1 반환
        }.also {
            db.endTransaction()
        }
    }

    fun selectAllMemos(): MutableList<MemoDto> {
        val columns = arrayOf("_id", "title", "content", "regDate", "tenMinAlarm", "thirtyMinAlarm") // _id 포함
        val result = mutableListOf<MemoDto>()

        db.query(TABLE, columns, null, null, null, null, null).use {
            while (it.moveToNext()) {
                val id = it.getInt(0)  // _id 컬럼
                val title = it.getString(1)
                val content = it.getString(2)
                val regDate = it.getString(3)
                val tenMinAlarm = it.getInt(4) == 1
                val thirtyMinAlarm = it.getInt(5) == 1

                result.add(MemoDto(id, title, content, regDate, tenMinAlarm, thirtyMinAlarm)) // ✅ 올바르게 MemoDto 생성
            }
        }
        return result
    }

    fun getCount():Int{
        return memos
    }

    fun selectMemo(id: Int): MemoDto? {
        val columns = arrayOf("_id", "title", "content", "regDate", "tenMinAlarm", "thirtyMinAlarm")
        var memo: MemoDto? = null
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE", null)
        if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            Log.d(TAG, "테이블 내 데이터 개수: $count")
        }
        cursor.close()
        db.query(TABLE, columns, "_id = ?", arrayOf(id.toString()), null, null, null).use {
            cursor ->
            if (cursor.moveToNext()) {
                val _id = cursor.getInt(0)
                val title = cursor.getString(1)
                val content = cursor.getString(2)
                val regDate = cursor.getString(3)
                val tenMinAlarm = cursor.getInt(4) > 0
                val thirtyMinAlarm = cursor.getInt(5) > 0

                Log.d(TAG, "selectMemo: ${id} ${title} ${content}")
                memo = MemoDto(_id, title, content, regDate, tenMinAlarm, thirtyMinAlarm)
            }
        }
        Log.d(TAG, "selectMemo: ${memo}")
        return memo
    }

    fun updateMemo(dto: MemoDto): Int {
        val contentValues = ContentValues().apply {
            put("title", dto.title)
            put("content", dto.content)
            put("tenMinAlarm", if (dto.tenMinAlarm) 1 else 0)
            put("thirtyMinAlarm", if (dto.thirtyMinAlarm) 1 else 0)
        }

        db.beginTransaction()
        val rowsAffected = db.update(TABLE, contentValues, "_id = ?", arrayOf(dto._id.toString()))

        return if (rowsAffected > 0) {
            db.setTransactionSuccessful()
            dto._id  // 업데이트 성공 시 id 반환
        } else {
            -1  // 업데이트 실패 시 -1 반환
        }.also {
            db.endTransaction()
        }
    }

    fun deleteMemo(id: Int): Int {
        db.beginTransaction()
        val rowsAffected = db.delete(TABLE, "_id = ?", arrayOf(id.toString()))

        memos--

        return if (rowsAffected > 0) {
            db.setTransactionSuccessful()
            id  // 삭제 성공 시 id 반환
        } else {
            -1  // 삭제 실패 시 -1 반환
        }.also {
            db.endTransaction()
        }
    }


}