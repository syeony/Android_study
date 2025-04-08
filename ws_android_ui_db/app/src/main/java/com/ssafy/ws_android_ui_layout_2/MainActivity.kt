package com.ssafy.ws_android_ui_layout_2

import android.app.AlarmManager
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
import com.ssafy.ws_android_ui_layout_2.databinding.ActivityMainBinding

// MainActivity
private const val TAG = "AlarmReceiver_싸피"
class MainActivity : AppCompatActivity() {
    // DB 선언부
    private lateinit var memoDBHelper: MemoDBHelper
    private lateinit var dbActions: MemoDBActions

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmManager: AlarmManager

    // ActivityResultLauncher 선언
    private val editActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                val _id = data.getIntExtra("_id", -1)
                val tenMinAlarm = data.getBooleanExtra("tenMinAlarm", false)
                val thirtyMinAlarm = data.getBooleanExtra("thirtyMinAlarm", false)
                setAlarms(_id, tenMinAlarm, thirtyMinAlarm)
                adapter1.notifyDataSetChanged()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        // helper인스턴스 생성
        memoDBHelper = MemoDBHelper(this, "newdb.db", null, 1)
        dbActions = MemoDBActions(memoDBHelper.writableDatabase)
        Log.d(TAG, "onCreate: 생성된 DB 정보: $dbActions")

        initAdapter() //리스트뷰만들때 필요
        setSupportActionBar(binding.toolbar) //툴바 생성
        registerForContextMenu(binding.listView) //리스트뷰 생성
    }

    lateinit var adapter1: ArrayAdapter<MemoDto>
    private fun initAdapter() {
        val list=dbActions.selectAllMemos()
        adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.listView.adapter = adapter1
        binding.listView.setOnItemClickListener { parent, view, _id, id ->
            Log.d(TAG, "onCreate: $_id")
            val item=adapter1.getItem(_id) as MemoDto
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("mode", "update")
            intent.putExtra("_id", item._id)

//            startActivityForResult(intent,
//                com.ssafy.ws_android_ui_layout_2.MainActivity.Companion.REQUEST_CODE_EDIT
//            )
//            startActivity(intent)
            editActivityResultLauncher.launch(intent)
        }
    }

    // + 버튼해서 등록
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu) // 메뉴 XML 파일 불러오기
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.register2 -> { // register2 ID가 있는지 확인
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("mode", "register")
//                startActivityForResult(intent, REQUEST_CODE_EDIT)
//                startActivity(intent)
                editActivityResultLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // 길게 누르면 삭제
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu) // 삭제 메뉴 연결
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.btnDelete -> { // 삭제 버튼이 눌렸을 때
                if (adapter1.getItem(info.position)?._id ?: -1 != -1) {
                    adapter1.getItem(info.position)?.let { dbActions.deleteMemo(it._id) }
                    adapter1.notifyDataSetChanged() // 리스트뷰 갱신
                    onResume()
                    Toast.makeText(this, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: ")
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            adapter1.notifyDataSetChanged()
            onResume()
            data?.let {
                val _id = it.getIntExtra("_id", -1)
                val tenMinAlarm = it.getBooleanExtra("tenMinAlarm", false)
                val thirtyMinAlarm = it.getBooleanExtra("thirtyMinAlarm", false)
                setAlarms(_id, tenMinAlarm, thirtyMinAlarm)
            }
        }
    }

    private fun setAlarms(position: Int, tenMinAlarm: Boolean, thirtyMinAlarm: Boolean) {
        Log.d(TAG, "setAlarms: $position")
        if (position == -1) return
//        val memo = MemoItemMgr.search(position)
        val memo = dbActions.selectMemo(position)

        if (tenMinAlarm){
            if (memo != null) {
                registerAlarm(position, 10, memo.content)
            }
        } else {
            cancelAlarm(position, 10)
        }

        if (thirtyMinAlarm){
            if (memo != null) {
                registerAlarm(position, 30, memo.content)
            }
        } else {
            cancelAlarm(position, 30)
        }
    }

    private fun registerAlarm(position: Int, minutes: Int, content: String) {
        val intent = Intent(this, MemoReceiver::class.java).apply {
            putExtra("position", position)
            putExtra("content", content)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            position + minutes,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        val triggerTime = SystemClock.elapsedRealtime() +3000//+ minutes * 60 * 1000
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, triggerTime, pendingIntent)
        Log.d(TAG, "$minutes 분 후 알람이 등록되었습니다.")
    }

    private fun cancelAlarm(position: Int, minutes: Int) {
        val intent = Intent(this, MemoReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            position + minutes,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
        Log.d(TAG, "$minutes 분 후 알람이 취소되었습니다.")
    }

    companion object {
        private const val REQUEST_CODE_EDIT = 1001
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }
}


