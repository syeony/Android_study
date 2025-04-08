package com.ssafy.ws_android_ui_layout_2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.ws_android_ui_layout_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initAdapter()
        initEvent()
    }

    private fun initEvent() {
        binding.register.setOnClickListener{
            val intent = Intent(this,EditActivity::class.java)
            intent.putExtra("mode","register")
            startActivity(intent)
        }
    }

    //다른 메서드에서도 어뎁터를 써야하므로 전역변수(멤버변수)로 빼놔야한다
    //lateinit은 기본적으로 디폴트값이 null
    lateinit var adapter1: ArrayAdapter<MemoItem>
    private fun initAdapter() {
        //->시간있으면 BaseAdapter를 상속받는 custom adapter를 만드십시다. 지금은 급하니 ...
        adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, MemoItemMgr.getList())

        //viewBinding사용했을때...
        binding.listView.adapter = adapter1

        //viewBinding사용안했을때...
//        val listView = findViewById<ListView>(R.id.listView)
//        listView.adapter = adapter1

        binding.listView.setOnItemClickListener { parent, view, position, id ->
//            selectedTv.text = parent.adapter.getItem(position).toString()
            Log.d(TAG, "onCreate: ${position}") //리스트뷰에 한개 클릭했을때 로그가 찍힐것이다!

            val intent = Intent(this,EditActivity::class.java)
            intent.putExtra("mode","update")
            intent.putExtra("position",position)

            startActivity(intent)
        }
    }

    private fun initData() {
        //초기데이터 세팅.
        MemoItemMgr.add(MemoItem("메모앱 만들기1","1번 Content", "03-27 10:00"))
        MemoItemMgr.add(MemoItem("메모앱 만들기2","2번 Content", "03-27 11:00"))
        MemoItemMgr.add(MemoItem("메모앱 만들기3","3번 Content", "03-27 12:00"))
    }

    override fun onResume(){
        super.onResume()
        adapter1.notifyDataSetChanged()
    }
}