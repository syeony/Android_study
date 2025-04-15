package com.ssafy.contentprovider.note

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi

private const val TAG = "NotesDbHelper_싸피"
@RequiresApi(Build.VERSION_CODES.R)
class NotesDbHelper : ContentProvider() {

    lateinit var mDbHelper: DatabaseHelper
    lateinit var mDb: SQLiteDatabase

    companion object {
        //  getType에서 사용함. 일반적으로 vnd+authoroty+dir or item
        //  Mime type을 고려하지 않는다면 사용하지 않아도 ok.
        val CONTENT_TYPE = "vnd.com.ssafy.contentprovider.note.dir/" + Notes.AUTHORITY
        val CONTENT_ITEM_TYPE = "vnd.com.ssafy.contentprovider.note.item/" + Notes.AUTHORITY

        private val NOTES = 1
        private val NOTE_ID = 2

        private const val DATABASE_CREATE =
            ("create table notes (_id integer primary key autoincrement, title text not null, body text not null);")

        private const val DATABASE_NAME = "data"
        private const val DATABASE_TABLE = "notes"
        private const val DATABASE_VERSION = 1
    }

    private var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply{
        addURI(Notes.AUTHORITY, "notes", NOTES)
        addURI(Notes.AUTHORITY, "notes/#", NOTE_ID)
    }

    class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            Log.d(TAG, "NotesDbHelper.DatabaseHelper.onCreate")
            db.execSQL(DATABASE_CREATE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w( TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data")
            db.execSQL("DROP TABLE IF EXISTS notes")
            onCreate(db)
        }
    }

    fun close() {
        mDbHelper.close()
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "NotesDbHelper.onCreate..")
        mDbHelper = DatabaseHelper(requireContext())
        return true
    }

    /* 현재 애플리케이션의 Mime type을 비교하는 용도로 사용된다.
    * Mime Type을 전혀 고려하지 않는다면 null을 리턴해도 무관함. */
    override fun getType(uri: Uri): String {
        return when (sUriMatcher.match(uri)) {
            NOTES -> CONTENT_TYPE
            NOTE_ID -> CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    override fun query( uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String? ): Cursor? {
        // where절에 selection, selectionArg 이외에,
        // _id = ? 을 추가하기 위해서 querybuilder를 사용함.
        val builder = SQLiteQueryBuilder()

        when (sUriMatcher.match(uri)) {
            NOTES -> { builder.tables = DATABASE_TABLE
            }
            NOTE_ID -> { builder.tables = DATABASE_TABLE
                builder.appendWhere(Notes.ID + "=" + uri.pathSegments[1])   // 이 문장 append를 위해서 query builder 사용.
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI $uri")
        }

        val orderBy: String = if (TextUtils.isEmpty(sortOrder)) {
            Notes.DEFAULT_SORT_ORDER
        } else {
            sortOrder!!
        }

        // builder를 통해서 쿼리 수행.
        val cursor = builder.query(mDbHelper.readableDatabase, projection, selection, selectionArgs, null, null, orderBy)
        cursor.setNotificationUri(requireContext().contentResolver, uri)

        return cursor
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri {
        // insert는 id 가 있으면 exception.
        when (sUriMatcher.match(uri)) {
            NOTE_ID -> {
                throw java.lang.IllegalArgumentException("Invalid URI, cannot update without ID: $uri")
            }
            NOTES -> {
                val db: SQLiteDatabase = mDbHelper.writableDatabase
                val rowId = db.insert(DATABASE_TABLE, null, values)

                val insertedUri = ContentUris.withAppendedId(uri, rowId)
                requireContext().contentResolver.notifyChange(insertedUri, null)

                if (rowId > 0) {
                    return uri
                }else{
                    throw SQLException("Failed to insert row into $uri")
                }
            }
            else -> {
                throw java.lang.IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        Log.d(TAG, "Update called...${uri}")
        return when (sUriMatcher.match(uri)) {
            NOTES -> {
                throw java.lang.IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            }
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                // uri에 들어온 데이터를 update한다. 여기서는..selection, selectionArgs은 무시된다.
                val count = mDbHelper.writableDatabase
                    .update(DATABASE_TABLE, values, "${Notes.ID} = ?", arrayOf(id.toString()))
                requireContext().contentResolver.notifyChange(uri, null)
                count
            }
            else -> {
                throw java.lang.IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (sUriMatcher.match(uri)) {
            NOTES -> {
                throw java.lang.IllegalArgumentException("Invalid URI, cannot update without ID: $uri")
            }
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                // uri에 들어온 데이터를 delete 한다. 여기서는..selection, selectionArgs은 무시된다.
                val count = mDbHelper.writableDatabase
                    .delete(DATABASE_TABLE,"${Notes.ID} = ?", arrayOf(id.toString()))
                requireContext().contentResolver.notifyChange(uri, null)
                count
            }
            else -> {
                throw java.lang.IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

}
