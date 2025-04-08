package com.ssafy.cleanstore.stuff

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.StuffMgr
import com.ssafy.cleanstore.databinding.ActivityStuffBinding
import com.ssafy.cleanstore.databinding.ActivityStuffEditBinding
import com.ssafy.cleanstore.dto.Stuff

class StuffEditActivity : AppCompatActivity() {
    private var position = -1
    private lateinit var binding: ActivityStuffEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStuffEditBinding.inflate(layoutInflater)
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
            val name = binding.name.text.toString().trim()
            val count = binding.count.text.toString().trim()

            if (name.isNotEmpty() && count.isNotEmpty()) {
                val item = Stuff(name, count)
                if ("register" == mode) {
                    // 새 물품 추가
                    StuffMgr.add(item)
                    Toast.makeText(this, "물품이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // 기존 물품 수정
                    if (position != -1) {
                        StuffMgr.update(position, item)
                        Toast.makeText(this, "물품이 수정되었습니다.", Toast.LENGTH_SHORT).show()
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
                StuffMgr.remove(position)
                Toast.makeText(this, "해당 물품이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun checkMode(mode: String?) {
        if ("register" == mode) {
            // 등록 모드
            binding.btnDelete.visibility = View.GONE
        } else {
            // 수정 모드
            binding.btnDelete.visibility = View.VISIBLE

            position = intent.getIntExtra("position", -1)
            if (position != -1) {
                val item = StuffMgr.search(position)
                binding.name.setText(item.name)
                binding.count.setText(item.count)
            }
        }
    }
}