package com.ssafy.ui.a5_recylerview.grid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.ui.a5_recylerview.R

private const val TAG = "GridRecyclerViewAdapter_싸피"
class GridRecyclerViewAdapter(private val context: Context,
                              private val dataList: MutableList<Food>) :
    RecyclerView.Adapter<GridRecyclerViewAdapter.FoodViewHolder>() {
    // 외부에서 OnItemClickListener를 공급 받기 위한 작업
    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    lateinit var onItemClickListener : OnItemClickListener

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_recyclerview, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.apply {
            bindInfo(dataList[position])
            itemView.setOnClickListener {
                onItemClickListener.onClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class FoodViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.food_title_tv)
        fun bindInfo(food: Food) {
            title.text = food.name
            //Glide.with(view.context).load(R.drawable.person1).into(view.findViewById(R.id.food_iv))
            Glide.with(view.context).load(food.img).into(view.findViewById(R.id.food_iv))

            //로딩시 로딩이미지 보이기
//                Glide.with(view.context).load(food.img).apply(
//                    RequestOptions().placeholder(R.drawable.loading_animation)).into(view.findViewById(R.id.food_iv))
        }
    }
}