package com.ssafy.android_ui

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "ListViewActivity_싸피"
class ListViewActivity : AppCompatActivity() {
    private val strData = mutableListOf<String>()
    private val mapData = mutableListOf<Map<String, String>>()

    private lateinit var listview1: ListView
    private lateinit var listview2:ListView
    private lateinit var listview3:ListView
    private lateinit var selectedTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        for(i in 1..10){
            strData.add("data $i")
            mapData.add(mapOf("id" to "hong $i", "name" to "홍길동 $i"))
        }

        listview1 = findViewById(R.id.listview1)
        listview2 = findViewById(R.id.listview2)
        listview3 = findViewById(R.id.listview3)
        selectedTv = findViewById(R.id.tvResult2)

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, strData)
        listview1.adapter = adapter1

        listview1.setOnItemClickListener { parent, view, position, id ->
            selectedTv.text = parent.adapter.getItem(position).toString()
            Log.d(TAG, "onCreate: ${parent.adapter.getItem(position)}")
        }

        //이미 안드로이드에서 만들어져있는거 갖다 쓴 것
        val adapter2 = SimpleAdapter(this,  mapData, android.R.layout.simple_list_item_2, arrayOf("id","name"), intArrayOf(android.R.id.text1, android.R.id.text2))
        listview2.adapter = adapter2
        listview2.setOnItemClickListener { parent, view, position, id ->
            selectedTv.text = parent.adapter.getItem(position).toString()
            Log.d(TAG, "onCreate: ${parent.adapter.getItem(position)}")
        }

        //직접 구현
        val adapter3 = CustomListAdapter(this,  mapData, R.layout.list_item_layout)
        listview3.adapter = adapter3
        listview3.setOnItemClickListener { parent, view, position, id ->
            selectedTv.text = parent.adapter.getItem(position).toString()
            Log.d(TAG, "onCreate: ${parent.adapter.getItem(position)}")
        }
    }
}