package com.ssafy.android_ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class EditTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)

        val edt = findViewById<EditText>(R.id.edt)
        val btn:Button = findViewById(R.id.btn)

        btn.setOnClickListener {
            Toast.makeText(this,edt.text.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}



