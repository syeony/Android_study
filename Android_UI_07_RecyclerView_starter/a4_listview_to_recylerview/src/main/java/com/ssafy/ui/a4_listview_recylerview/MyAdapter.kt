package com.ssafy.ui.a4_listview_recylerview

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val list:ArrayList<String>)
    : RecyclerView.Adapter<MyAdapter.CustomViewHolder>()
    {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnCreateContextMenuListener{ //itemView: 하나의 view
        //registerforcontextmenu
        init {
            itemView.setOnCreateContextMenuListener(this)
        }
        val name:TextView = itemView.findViewById(R.id.name)

        fun bindInfo(data: String){
            name.setText(data)
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val item=menu.add("삭제 ")
            item.setOnMenuItemClickListener{
                Toast.makeText(itemView.context, "선택되었습니다.", Toast.LENGTH_SHORT).show()
                true //true : 이벤트 처리 끝났다 의미. 여기서 끝. false : 뒤에 있는 이벤트 수행하시오.
            }
        }
    }

    //아래 세개의 함수는 내가 만든게 아님
    //만들어주는 것만! 재사용? 알아서해 느낌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return CustomViewHolder(view)
    }

    // 리스트 갯수 리턴
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.apply{
            bindInfo(list[position])
            itemView.setOnClickListener {
                //다운로드한다????-->외부에서 처리하는 것이 맞다.
                Toast.makeText(it.context, "clicked $position", Toast.LENGTH_SHORT).show()
                myItemClickListener.onMyClick(position, list[position])
            }
        }
    }

    //내가 새로 만든 인터페이스
    lateinit var myItemClickListener: MyItemClickListener //setter, getter 다 만들어짐 자동으로

    fun   interface MyItemClickListener{
        fun onMyClick(position:Int, data:String)
    }



}