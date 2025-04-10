package com.ssafy.ws_android_ui_layout_2

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.ws_android_ui_layout_2.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var memoDBHelper: MemoDBHelper
    private lateinit var dbActions: MemoDBActions
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter1: MemoAdapter
    private lateinit var memoList: MutableList<MemoDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memoDBHelper = MemoDBHelper(this, "newdb.db", null, 1)
        dbActions = MemoDBActions(memoDBHelper.writableDatabase)

        initAdapter()
        setSupportActionBar(binding.toolbar)
    }

    private fun initAdapter() {
        memoList = dbActions.selectAllMemos().toMutableList()
        adapter1 = MemoAdapter(memoList) { _, memo ->
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("mode", "update")
                putExtra("_id", memo._id)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter1
    }


//    private fun initAdapter() {
//        memoList = dbActions.selectAllMemos().toMutableList()
//        adapter1 = MemoAdapter(
//            memoList,
//            onItemClick = { memo ->
//                val intent = Intent(this, EditActivity::class.java).apply {
//                    putExtra("mode", "update")
//                    putExtra("_id", memo._id)
//                }
//                startActivity(intent)
//            }
//        )
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter1
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.register2 -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("mode", "register")
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnDelete -> {
                val position = adapter1.selectedPosition
                if (position != -1 && adapter1.getItem(position)._id != -1) {
                    val memo = adapter1.getItem(position)
                    dbActions.deleteMemo(memo._id)
                    memoList.removeAt(position)
                    adapter1.notifyItemRemoved(position)
                    Toast.makeText(this, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }
}
