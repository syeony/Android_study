package com.ssafy.memo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MemoEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_memo_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.goHome).setOnClickListener{
            finish()
        }

        findViewById<Button>(R.id.save).setOnClickListener{
            val intent = Intent()
            val todo = findViewById<EditText>(R.id.todo).text
            val context = findViewById<EditText>(R.id.description).text
            if(todo.isNotBlank() && context.isNotBlank()){
                intent.putExtra("memo", "${todo}" + " , " + "${context}")
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this, "값을 입력하세요!!", Toast.LENGTH_LONG).show()
            }
        }
    }
}