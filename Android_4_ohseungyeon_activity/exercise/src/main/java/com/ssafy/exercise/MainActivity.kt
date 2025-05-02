package com.ssafy.exercise

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.exercise.adapter.MyAdapter
import com.ssafy.exercise.databinding.ActivityMainBinding
import com.ssafy.exercise.dto.Memo
import com.ssafy.exercise.service.BoundService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // BoundService 관련 멤버
    private lateinit var serviceIntent: Intent
    private var boundService: BoundService? = null
    private var isBound = false

    private lateinit var adapter: MyAdapter

    // ServiceConnection 객체
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
            isBound = true

            // 서비스가 연결되면 리스트 갱신
            refreshList(boundService?.getList() ?: emptyList())
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            boundService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceIntent = Intent(this, BoundService::class.java)

        // RecyclerView 설정
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // FloatingActionButton 클릭 시 EditActivity로 이동
        binding.register.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("memo", DEFAULT_STUFF)
                putExtra("position", -1)
                putExtra("actionFlag", REGISTER)
            }
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sortedList: List<Memo> = when (item.itemId) {
            R.id.sort_asc -> {
                boundService?.getListSortedByExerciseTypeAsc() ?: emptyList()
            }
            R.id.sort_desc -> {
                boundService?.getListSortedByExerciseTypeDesc() ?: emptyList()
            }
            R.id.date_asc -> {
                boundService?.getListSortedByDateAsc() ?: emptyList()
            }
            R.id.date_desc -> {
                boundService?.getListSortedByDateDesc() ?: emptyList()
            }
            else -> return super.onOptionsItemSelected(item)
        }

        // RecyclerView 어댑터에 정렬된 리스트 반영
        adapter.updateList(sortedList)
        return true
    }

    override fun onStart() {
        super.onStart()
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        if (isBound) {
            refreshList(boundService?.getList() ?: emptyList())
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    private fun refreshList(newList: List<Memo>) {
        adapter = MyAdapter(this, newList, { memo ->
            // 아이템 클릭 시 EditActivity로 이동 (수정 모드)
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("memo", memo)
                putExtra("position", newList.indexOf(memo))
                putExtra("actionFlag", UPDATE)
            }
            startActivity(intent)
        })
        binding.recyclerView.adapter = adapter
    }

    companion object {
        const val REGISTER = 1
        const val UPDATE = 2

        val DEFAULT_STUFF = Memo(
            id = -1,
            exerciseType = "",
            duration = -1,
            date = -1,
            intensity = 0f,
            memo = "",
            createdAt = -1
        )
    }
}