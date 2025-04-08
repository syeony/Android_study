package com.ssafy.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //구현
        binding.writeMemo.setOnClickListener{
            val intent=Intent(this,MemoEditActivity::class.java)
            requestActivity.launch(intent)
        }

        binding.memoList.setOnClickListener{
            val intent=Intent(this,MemoInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        //sub로 갔다온 결과를 여기서 받는다.
        if(it.resultCode== RESULT_OK){
            val intent = it.data
            val result = intent?.getStringExtra("memo")
            binding.juyomemo.setText(result)
        }
    }
}