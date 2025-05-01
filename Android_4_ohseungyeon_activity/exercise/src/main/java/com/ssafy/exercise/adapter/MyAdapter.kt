package com.ssafy.exercise.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.exercise.R
import com.ssafy.exercise.dto.Memo

class MyAdapter(
    private val context: Context,
    private var stuffList: List<Memo>,
    private val onItemClick: (Memo) -> Unit)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // 뷰 홀더 정의
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exercisetype: TextView = view.findViewById(R.id.exercisetype)
        val date: TextView = view.findViewById(R.id.date)
        val memo: TextView = view.findViewById(R.id.memo)
        val intensity: RatingBar = view.findViewById(R.id.intensity)
        val time: TextView = view.findViewById(R.id.time)
    }

    // 뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    // 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val stuff = stuffList[position]
        holder.exercisetype.text = stuff.exerciseType
        //날짜 Long타입에서 yyyy-mm-dd형식으로 포맷하기
        val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val formattedDate = dateFormat.format(java.util.Date(stuff.date))
        holder.date.text = formattedDate
        holder.memo.text = stuff.memo
        holder.intensity.rating = stuff.intensity
        holder.time.text = stuff.duration.toString()

        holder.itemView.setOnClickListener {
            onItemClick(stuff)
        }
    }

    // 리스트 아이템 개수
    override fun getItemCount(): Int = stuffList.size

    fun updateList(newList: List<Memo>) {
        stuffList = newList
        notifyDataSetChanged()
    }
}