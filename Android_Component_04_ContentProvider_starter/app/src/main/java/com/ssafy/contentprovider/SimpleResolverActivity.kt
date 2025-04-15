package com.ssafy.contentprovider

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cursoradapter.widget.CursorAdapter
import com.ssafy.contentprovider.util.PermissionChecker

private const val TAG = "SimpleResolverAct_싸피"
class SimpleResolverActivity : AppCompatActivity() {

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.READ_CONTACTS)
    /** permission check **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_resolver)

        checkPermission()
    }

    private fun checkPermission(){
//        init()
        /** permission check **/
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener { //퍼미션 획득 성공일때
                init()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            init()
        }
        /** permission check **/
    }

    private fun init() {
        val textview = findViewById<TextView>(R.id.textview)

        val contactsURI = ContactsContract.Contacts.CONTENT_URI
        // --> content://com.android.contacts/contacts

        // ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // --> content://com.android.contacts/data/phones

        Log.d(TAG, "init: ${contactsURI}")

        val URI = contactsURI
//        val URI = ContentUris.withAppendedId(contactsURI, "1".toLong())
        //        val URI = Uri.parse(ContactsContract.Contacts.CONTENT_URI.toString()+"/1")

        contentResolver.query(URI, null, null, null, null)?.use{
            Log.d(TAG, "init: ${it.count}, ${it.columnCount}")
            while(it.moveToNext()){
                for(i in 0..it.columnCount-1){
                    Log.d(TAG, "column.$i: ${it.getColumnName(i)}, value: ${it.getString(i)}")
                }
            }
        }




    }
}