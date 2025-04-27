package com.ssafy.ui.a5_recylerview.basic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ui.a5_recylerview.R

class BasicRecyclerViewAdapter( private val context: Context, private val userDataList: MutableList<UserData>)
            : RecyclerView.Adapter<BasicRecyclerViewAdapter.UserInfoHolder>() {

    // 개별 데이터(UserData)를 item_basic_recyclerview.xml과 연결하는 holder 구성
    inner class UserInfoHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title_tv)
        val phone = view.findViewById<TextView>(R.id.phone_tv)
        val logo = view.findViewById<ImageView>(R.id.logo_iv)

        fun bindInfo(userData: UserData) {
            title.text = userData.name
            phone.text = userData.phone
            logo.setImageResource(userData.img)
        }
    }

    // 아이템과 아이템 레이아웃을 바인딩 하는 UserInfoHolder 생성 및 반환
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ) : UserInfoHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basic_recyclerview, parent, false)

        return UserInfoHolder(view)
    }

    // ViewHolder에 실제 데이터를 binding 하는 메서드
    override fun onBindViewHolder(holder: UserInfoHolder, position: Int ) {
        holder.apply {
            bindInfo(userDataList[position])
            itemView.setOnClickListener {
                Toast.makeText(context, "클릭! 위치 $position", Toast.LENGTH_SHORT).show()
            }
            itemView.setOnLongClickListener {
                Toast.makeText(context, "롱~~ 클릭! 위치 $position",Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    override fun getItemCount(): Int = userDataList.size

    fun deleteItem(index : Int) {
        userDataList.removeAt(index)
        // observer에게 아이템이 변경되었음을 알림
        notifyDataSetChanged()
    }


}

