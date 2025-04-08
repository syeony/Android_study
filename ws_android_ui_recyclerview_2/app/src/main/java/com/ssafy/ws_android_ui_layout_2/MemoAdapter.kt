package com.ssafy.ws_android_ui_layout_2

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// MemoAdapter.kt
class MemoAdapter(
    private val memos: List<MemoDto>,
    private val onItemClick: (MemoDto) -> Unit,
    private val onItemLongClick: (Int) -> Unit
) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    // 👉 Adapter 내에서 선택된 position 저장
    var selectedPosition: Int = -1

    inner class MemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.titleTextView)
        private val content = view.findViewById<TextView>(R.id.contentTextView)
        private val time = view.findViewById<TextView>(R.id.timeTextView)

        fun bind(memo: MemoDto) {
            title.text = memo.title
            content.text = memo.content
            time.text = memo.regDate

            view.setOnClickListener { onItemClick(memo) }

            view.setOnLongClickListener {
                selectedPosition = adapterPosition // 🔥 선택된 position 저장
                view.showContextMenu()
                true
            }

            view.setOnCreateContextMenuListener { menu, _, _ ->
                val inflater = MenuInflater(view.context)
                inflater.inflate(R.menu.menu_context, menu)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.memo_list_item, parent, false)
        return MemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(memos[position])
    }

    override fun getItemCount(): Int = memos.size

    fun getItem(position: Int): MemoDto = memos[position]
}
