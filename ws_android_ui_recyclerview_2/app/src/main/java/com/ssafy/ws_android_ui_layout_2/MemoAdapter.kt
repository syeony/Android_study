package com.ssafy.ws_android_ui_layout_2

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemoAdapter(
    private val items: List<MemoDto>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    var selectedPosition: Int = -1

    inner class MemoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {

        private val title = itemView.findViewById<TextView>(R.id.titleTextView)
        private val content = itemView.findViewById<TextView>(R.id.contentTextView)
        private val time = itemView.findViewById<TextView>(R.id.timeTextView)

        init {
            itemView.setOnCreateContextMenuListener(this)
            itemView.setOnLongClickListener {
                selectedPosition = adapterPosition
                false
            }
        }

        fun bind(memo: MemoDto) {
            title.text = memo.title
            content.text = memo.content
            time.text = memo.regDate

            itemView.setOnClickListener {
                listener.onClick(adapterPosition, memo)
            }
        }

        override fun onCreateContextMenu(menu: ContextMenu, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val inflater = MenuInflater(view?.context)
            inflater.inflate(R.menu.menu_context, menu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.memo_list_item, parent, false)
        return MemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): MemoDto = items[position]

    fun interface OnItemClickListener {
        fun onClick(position: Int, memo: MemoDto)
    }
}

//// MemoAdapter.kt
//class MemoAdapter(
//    private val memos: List<MemoDto>,
//    private val onItemClick: (MemoDto) -> Unit,
//) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {
//
//    // 👉 Adapter 내에서 선택된 position 저장
//    var selectedPosition: Int = -1
//
//    inner class MemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        private val title = view.findViewById<TextView>(R.id.titleTextView)
//        private val content = view.findViewById<TextView>(R.id.contentTextView)
//        private val time = view.findViewById<TextView>(R.id.timeTextView)
//
//        fun bind(memo: MemoDto) {
//            title.text = memo.title
//            content.text = memo.content
//            time.text = memo.regDate
//
//            view.setOnClickListener { onItemClick(memo) }
//
//            view.setOnLongClickListener {
//                selectedPosition = adapterPosition // 선택된 position 저장
//                false
//            }
//
//            view.setOnCreateContextMenuListener { menu, _, _ ->
//                val inflater = MenuInflater(view.context)
//                inflater.inflate(R.menu.menu_context, menu)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.memo_list_item, parent, false)
//        return MemoViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
//        holder.bind(memos[position])
//    }
//
//    override fun getItemCount(): Int = memos.size
//
//    fun getItem(position: Int): MemoDto = memos[position]
//}
