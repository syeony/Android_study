package com.ssafy.contentprovider

import android.Manifest
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.cursoradapter.widget.CursorAdapter.FLAG_AUTO_REQUERY
import com.ssafy.contentprovider.util.PermissionChecker

private const val TAG = "SimpleResoAct2_싸피"
class SimpleCursorAdapterActivity : AppCompatActivity() {

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.READ_CONTACTS)
    /** permission check **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_resolver2)

        checkPermission()
    }

    private fun checkPermission(){

        /** permission check **/
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener{ //퍼미션 획득 성공일때
                init()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            init()
        }
        /** permission check **/
    }


    val URI = ContactsContract.Contacts.CONTENT_URI // 전체 주소록
    private fun init(){

        val listview = findViewById<ListView>(R.id.listview)
//        val URI = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, "1".toLong()) //1 번만 조회

        val cursor = contentResolver.query(URI, null, null, null, null)
        val from = arrayOf( "_id", "display_name")
        val to = intArrayOf( R.id.id_item, R.id.name_item)

        val adapter1 = SimpleCursorAdapter(
            this,
            R.layout.item_list,
            cursor!!,
            from,
            to,
            FLAG_AUTO_REQUERY // 변경시 자동 재쿼리. DEPRECATED 됨.
//            FLAG_REGISTER_CONTENT_OBSERVER
        )
        listview.adapter = adapter1
    }

    //상속해서 구현하면 주소록 변경시 onContentChanged 콜백됨.
    inner class MySimpleCursorAdapter(
        context: Context, layout:Int, cursor:Cursor, from:Array<String>, to:IntArray, flag:Int
    ) : SimpleCursorAdapter(context, layout, cursor,from, to, flag){

        override fun onContentChanged() {
            super.onContentChanged()
            Log.d(TAG, "onContentChanged: ")
//            cursor.requery()

            val newCursor = contentResolver.query(URI, null, null, null, null)
            changeCursor(newCursor)
        }
    }

}