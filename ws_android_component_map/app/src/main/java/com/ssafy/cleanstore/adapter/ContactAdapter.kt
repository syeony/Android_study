package com.ssafy.cleanstore.adapter

import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.cleanstore.R

class ContactAdapter(private val cursor: Cursor) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 선언
        val img: ImageView = itemView.findViewById(R.id.img)
        val name: TextView = itemView.findViewById(R.id.name)
        val tel: TextView = itemView.findViewById(R.id.tel)
        val email: TextView = itemView.findViewById(R.id.email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store_recyclerview, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        if (cursor.moveToPosition(position)) {
            val nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val idIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID)

            val name = cursor.getString(nameIdx)
            val contactId = cursor.getString(idIdx)

            //이름은 간단하게 가져올 수 있는데 왜 전화번호, 이메일, 사진은 별도로 쿼리해야할까?

            //ContactsContract의 구조 때문이다.
            //이름은 ContactsContract.Contacts 테이블에 포함되어 있지만
            //전화번호는 ContactsContract.CommonDataKinds.Phone 테이블에 따로 저장되어있다.

            holder.name.text = name

            // 전화번호 가져오기
            // 연락처 DB에서 전화번호 정보를 담고 있는 커서 객체
            val phoneCursor = holder.itemView.context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                //연락처 테이블에서 해당 contactId를 가진 사람의 전화번호를 가져옴
                arrayOf(contactId),
                null
            )

            // 커서에서 실제 전화번호 문자열을 추출한 결과 값
            val phone = phoneCursor?.use {
                if (it.moveToFirst()) {
                    val numberIdx = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    it.getString(numberIdx)
                } else null
            }
            holder.tel.text = phone

            // 이메일 가져오기
            val emailCursor = holder.itemView.context.contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
                arrayOf(contactId),
                null
            )

            var email = emailCursor?.use {
                if (it.moveToFirst()) {
                    val emailIdx = it.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    it.getString(emailIdx)
                } else null
            }
            holder.email.text = email

            // 이미지 가져오기
            // 1. 연락처 이미지 URI 생성
            val contactUri = Uri.parse("content://com.android.contacts/contacts/"+contactId)

            // 2. InputStream으로 사진 가져오기
            val inputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                holder.itemView.context.contentResolver,
                contactUri
            )

            val bitmap = BitmapFactory.decodeStream(inputStream)
            holder.img.setImageBitmap(bitmap)

            //Uri를 바로 넣으면 안되더라...
//            holder.img.setImageURI(contactUri)

        }
    }

    override fun getItemCount(): Int = cursor.count
}
