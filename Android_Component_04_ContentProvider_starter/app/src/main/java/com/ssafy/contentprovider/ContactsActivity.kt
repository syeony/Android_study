package com.ssafy.contentprovider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.contentprovider.util.PermissionChecker

private const val TAG = "ContactActivity_싸피"
class ContactsActivity : AppCompatActivity() {

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.READ_CONTACTS)
    /** permission check **/

    val contactsList = mutableListOf<ContactsDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        title = "Contacts"

        checkPermission()

    }

    private fun checkPermission(){
        /** permission check **/
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener { //퍼미션 획득 성공일때
                initData()
                initView()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            initData()
            initView()
        }
        /** permission check **/
    }

    private fun initView(){
        val contactsAdapter = ContactsAdapter(contactsList)

        findViewById<RecyclerView>(R.id.recyle_view).apply{
            adapter = contactsAdapter
        }
    }

    @SuppressLint("Range")
    private fun initData(){
        getContracts().use {
            if(it.moveToFirst()){
                Log.d(TAG, "initData: getContracts...")
                do{
                    val id = it.getLong(it.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                    Log.d(TAG, "id:$id, name:$name \n")

                    contactsList.add(ContactsDto(id, name))

                }while(it.moveToNext())
            }
        }
    }

    private fun getContracts(): Cursor {
        val queryUri = ContactsContract.Contacts.CONTENT_URI

        //정렬
        val sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";

        //가져올 컬럼명
        val what = arrayOf(
            ContactsContract.Contacts._ID ,
            ContactsContract.Contacts.DISPLAY_NAME
        )
        return contentResolver.query(queryUri, what, null, null, sortOrder)!!
    }

    inner class ContactsAdapter(val contactsList:MutableList<ContactsDto>): RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

        inner class ContactsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var id = itemView.findViewById<TextView>(R.id.id)
            var name = itemView.findViewById<TextView>(R.id.name)

            fun bind(data:ContactsDto){
                id.text = data.id.toString()
                name.text = data.name
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)

            return ContactsViewHolder(view).apply {
                itemView.setOnClickListener{
                    Toast.makeText(parent.context, "contact_item.name:${contactsList[layoutPosition].name}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onBindViewHolder(holder: ContactsAdapter.ContactsViewHolder, position: Int) {
            holder.bind(contactsList.get(position))
        }

        override fun getItemCount(): Int {
            return contactsList.size
        }
    }

}