package com.ssafy.ui6.sqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class MyTableDao(private val db:SQLiteDatabase){
    // 주의!!!
    // sqlite는 autoincrement 라도, 사용자가 입력한 값으로 insert를 시도한다.
    fun insert(content: String) {
        // ContentValues를 이용한 저장
        val contentValues = ContentValues()
        contentValues.put("txt", content)
        db.beginTransaction()
        val result = db.insert(TABLE, null, contentValues)

        // sql을 이용한 저장
//         val query = "INSERT INTO mytable('txt') values('sql 문장 이용용');";
//         var result:Int = -1
//         runCatching {
//             db.execSQL(query)
//         }.onSuccess {
//             result = 1
//         }

        if (result > 0) {
            db.setTransactionSuccessful()
        }
        db.endTransaction()
    }

    fun list(): ArrayList<MyTableDto> {
        val columns = arrayOf("_id", "txt")
        val result = arrayListOf<MyTableDto>()
        db.query(TABLE, columns, null, null, null, null, null).use {
//        readableDatabase.rawQuery("select * from $TABLE", null).use{
            while (it.moveToNext()) {
                result.add(MyTableDto(it.getInt(0), it.getString(1)))
            }
        }
        return result
    }

    fun update(id: String, content: String) {
        // ContentValues를 이용한 수정
        val contentValues = ContentValues()
        contentValues.put("txt", content)
        db.beginTransaction()
        val result = db.update(TABLE, contentValues, "_id=?", arrayOf(id))
        // sql을 이용한 수정
        // val query = "update $TABLE set txt=$content where _id=$id";
        // db.execSQL(query)
        if (result > 0) {
            db.setTransactionSuccessful()
        }
        db.endTransaction()
    }

    fun delete(id: String) {
        db.beginTransaction()
        val result = db.delete(TABLE, "_id=?", arrayOf(id))
        // sql을 이용한 삭제
        //val query = "delete from $TABLE where _id=$id";
        // db.execSQL(query)
        if (result > 0) {
            db.setTransactionSuccessful()
        }
        db.endTransaction()
    }

    fun select(id: String): MyTableDto {
        val columns = arrayOf("_id", "txt")
        var result = MyTableDto(-1, "")

        db.query(TABLE, columns, "_id=?", arrayOf(id), null, null, null).use{ cursor ->
            if (cursor.moveToNext()) {
                result = MyTableDto(cursor.getInt(0), cursor.getString(1))
            }
        }

        return result
    }

}