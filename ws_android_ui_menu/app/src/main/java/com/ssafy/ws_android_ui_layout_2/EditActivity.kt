package com.ssafy.ws_android_ui_layout_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.ws_android_ui_layout_2.databinding.ActivityEditBinding
import com.ssafy.ws_android_ui_layout_2.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//class EditActivity : AppCompatActivity() {
//    private var position = -1
//    private lateinit var binding: ActivityEditBinding //-->activity_sub.xml
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityEditBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnCancel.setOnClickListener{
//            finish()
//        }
//
//        val mode=intent.getStringExtra("mode") //register
//        checkMode(mode)
//        initEvent(mode)
//    }
//
//    private fun initEvent(mode:String?) {
//        binding.btnSave.setOnClickListener{
//            val item = MemoItem(binding.title.text.toString(), binding.content.getText().toString(), "03-27 17:00")
//            if("registar".equals(mode)){//입력일 경우
//                MemoItemMgr.add(item)
//            }else{//수정일 경우
//
//            }
//            finish()
//        }
//    }
//
//    private fun checkMode(mode: String?) {
//        if("register".equals(mode)){ //등록버튼 눌러서 왔을 때
//            binding.regDate.visibility = View.GONE
//            binding.btnDelete.visibility = View.GONE
//            binding.time.visibility = View.GONE
//        }else{ // 수정모드 때...
//            binding.time.visibility = View.VISIBLE
//            binding.btnDelete.visibility = View.VISIBLE
//            binding.time.visibility = View.VISIBLE
//
//            position = intent.getIntExtra("position",-1)
//
//            if(position!=-1){
//                val item = MemoItemMgr.search(position)
//                binding.title.setText(item.title)
//                binding.title.setText(item.content)
//                binding.title.setText(item.regDate)
//            }
//        }
//    }
//}

class EditActivity : AppCompatActivity() {
    private var position = -1
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener {
            finish()
        }

        val mode = intent.getStringExtra("mode")
        checkMode(mode)
        initEvent(mode)
    }

    private fun initEvent(mode: String?) {
        binding.btnSave.setOnClickListener {
            val title = binding.title.text.toString().trim()
            val content = binding.content.text.toString().trim()
            val date = getCurrentDate()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val item = MemoItem(title, content, date)
                if ("register" == mode) {
                    // 새 메모 추가
                    MemoItemMgr.add(item)
                    Toast.makeText(this, "메모가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // 기존 메모 수정
                    if (position != -1) {
                        MemoItemMgr.update(position, item)
                        Toast.makeText(this, "메모가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "모든 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            if (position != -1) {
                MemoItemMgr.remove(position)
                Toast.makeText(this, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun checkMode(mode: String?) {
        if ("register" == mode) {
            // 등록 모드
            binding.regDate.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
            binding.time.visibility = View.GONE
        } else {
            // 수정 모드
            binding.time.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.VISIBLE

            position = intent.getIntExtra("position", -1)
            if (position != -1) {
                val item = MemoItemMgr.search(position)
                binding.title.setText(item.title)
                binding.content.setText(item.content)
                binding.regDate.setText(item.regDate)
            }
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}