package com.ssafy.ui.a5_recylerview.hori

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ui.a5_recylerview.R

data class Transportation(val title: String, val logo: Int)

class HorizontalRecyclerViewActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView

    private val dataList = mutableListOf<Transportation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_recycler_view)
        recycler = findViewById(R.id.hor_recycler_view)

        for(i in 1..3) {
            dataList.add(Transportation("자전거", R.drawable.ic_bike))
            dataList.add(Transportation("버스", R.drawable.ic_bus))
            dataList.add(Transportation("자동차", R.drawable.ic_car))
            dataList.add(Transportation("뛰기", R.drawable.ic_run))
        }

        recycler.apply {
            adapter = HorizontalRecyclerViewAdapter(this@HorizontalRecyclerViewActivity, dataList)
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

    }
}