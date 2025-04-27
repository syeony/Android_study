package com.ssafy.ui.a5_recylerview.basic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ui.a5_recylerview.R

data class UserData(val name: String, val phone: String, val img: Int)

class BasicRecyclerViewActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView

    lateinit var basicAdapter: BasicRecyclerViewAdapter
    private val userDataList = mutableListOf<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_recycler_view)
        recycler = findViewById(R.id.recycler_view)


        for(i in 1..30) {
            userDataList.add(UserData("찰스", "010-1234-7442", R.drawable.person1))
            userDataList.add(UserData("바리", "010-1234-9942", R.drawable.person2))
            userDataList.add(UserData("핀", "010-1234-7042", R.drawable.person3))
            userDataList.add(UserData("유캔", "010-1234-0042", R.drawable.person4))
        }

        basicAdapter = BasicRecyclerViewAdapter(this, userDataList)
        recycler.apply {
            adapter = basicAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        // swipe event 처리를 위한 ItemTouchHelper.Callback 구현체 생성
        val swipeDelete = object : SwipeToDeleteCallback(this) {
            // SwipeToDeleteCallback의 onSwiped 함수 재정의
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // swipe 이벤트 발생 시 adapter의 deleteItem 함수 호출
                basicAdapter.deleteItem(viewHolder.adapterPosition) //absoluteAdapterPosition
            }
        }

        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(recycler)
    }

}