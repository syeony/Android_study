package com.ssafy.ui.a5_recylerview.grid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ui.a5_recylerview.R

data class Food( val name : String, val img : String )

class GridRecyclerViewActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private val foodList = mutableListOf<Food>()
    lateinit var gridAdapter : GridRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_recycler_view)
        recycler = findViewById(R.id.grid_recycler_view)

        for(i in 1..10) {
            foodList.add(Food("오코노미야끼", "https://d20aeo683mqd6t.cloudfront.net/images/imgs/000/015/197/original/image001.jpeg?1561111369&d=750x750"))
            foodList.add(Food("타코야끼", "https://d20aeo683mqd6t.cloudfront.net/images/imgs/000/015/214/original/image006.jpeg?1561342506&d=750x750"))
            foodList.add(Food("쿠시카츠", "https://d20aeo683mqd6t.cloudfront.net/images/imgs/000/015/217/original/image008.jpeg?1561342854&d=750x750"))
            foodList.add(Food("우동", "https://d20aeo683mqd6t.cloudfront.net/images/imgs/000/015/219/original/image010.jpeg?1561343215&d=750x750"))
        }


        gridAdapter = GridRecyclerViewAdapter(this, foodList)
        gridAdapter.onItemClickListener = object: GridRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(this@GridRecyclerViewActivity, "Activity에서 설정한 리스너 : $position", Toast.LENGTH_SHORT).show()
            }
        }
        recycler.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(this@GridRecyclerViewActivity, 2)
        }
    }
}