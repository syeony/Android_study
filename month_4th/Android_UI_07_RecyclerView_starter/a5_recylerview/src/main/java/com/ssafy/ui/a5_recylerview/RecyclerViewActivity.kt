package com.ssafy.ui.a5_recylerview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.ui.a5_recylerview.basic.BasicRecyclerViewActivity
import com.ssafy.ui.a5_recylerview.grid.GridRecyclerViewActivity
import com.ssafy.ui.a5_recylerview.hori.HorizontalRecyclerViewActivity


class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        findViewById<Button>(R.id.vertical_btn).setOnClickListener {
            startActivity(Intent(this, BasicRecyclerViewActivity::class.java))
        }

        findViewById<Button>(R.id.horizontal_btn).setOnClickListener {
            startActivity(Intent(this, HorizontalRecyclerViewActivity::class.java))
        }

        findViewById<Button>(R.id.grid_btn).setOnClickListener {
            startActivity(Intent(this, GridRecyclerViewActivity::class.java))
        }
    }
}