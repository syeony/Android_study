package com.ssafy.cleanstore.stuff

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.StuffMgr
import com.ssafy.cleanstore.databinding.ActivityStuffBinding
import com.ssafy.cleanstore.dto.Stuff

class StuffActivity : AppCompatActivity() {
    private lateinit var binding:ActivityStuffBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityStuffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initAdapter()
        initEvent()
    }

    private fun initEvent() {
        binding.register.setOnClickListener{
            val intent = Intent(this,StuffEditActivity::class.java)
            intent.putExtra("mode","register")
            startActivity(intent)
        }
    }

    lateinit var adapter1: ArrayAdapter<Stuff>
    private fun initAdapter() {
        adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, StuffMgr.getList())
        binding.listView.adapter = adapter1

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,StuffEditActivity::class.java)
            intent.putExtra("mode","update")
            intent.putExtra("position",position)

            startActivity(intent)
        }
    }

    private fun initData() {
        StuffMgr.add(Stuff("사과","10"))
    }

    override fun onResume(){
        super.onResume()
        adapter1.notifyDataSetChanged()
    }

}