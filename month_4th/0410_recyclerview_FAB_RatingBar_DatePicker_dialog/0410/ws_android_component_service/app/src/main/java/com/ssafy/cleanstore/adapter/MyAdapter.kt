package com.ssafy.cleanstore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.dto.Stuff
import org.w3c.dom.Text

class MyAdapter(
    private val context: Context,
    private val stuffList: List<Stuff>,
    private val onItemClick: (Stuff) -> Unit,
    private val onItemLongClick: (Stuff) -> Unit)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // 뷰 홀더 정의
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val contentTextView: TextView = view.findViewById(R.id.contentTextView)
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val ratingTextView: TextView = view.findViewById(R.id.ratingTextView)
    }

    // 뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stuff_list_item, parent, false)
        return MyViewHolder(view)
    }

    // 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val stuff = stuffList[position]
        holder.titleTextView.text = stuff.name
        holder.contentTextView.text = stuff.count.toString()
        holder.timeTextView.text = stuff.regDate
        holder.ratingTextView.text = stuff.rating.toString()

        holder.itemView.setOnClickListener {
            onItemClick(stuff)
        }
        // 길게 눌렀을 때 삭제 동작 처리
        holder.itemView.setOnLongClickListener {
            onItemLongClick(stuff)
            true // 길게 누름이 처리되었다는 표시
        }
    }

    // 리스트 아이템 개수
    override fun getItemCount(): Int = stuffList.size
}
