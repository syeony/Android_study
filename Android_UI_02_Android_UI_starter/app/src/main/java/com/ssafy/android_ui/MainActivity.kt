package com.ssafy.android_ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1 = findViewById<Button>(R.id.button1)
        btn1.setOnClickListener( object : View.OnClickListener { // object : anonymous nested class
            override fun onClick(v: View) {
                Toast.makeText(this@MainActivity, "Hello World", Toast.LENGTH_SHORT).show()
            }
        })

//        btn1.setOnClickListener( { view ->  //parameter를 명시적 변수로 받을 경우
//            Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show()
//        })
//
//        btn1.setOnClickListener( { _ ->  // parameter를 사용하지 않을 경우
//            Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show()
//        })
//
        //한개인 변수명은 it으로 자동선언 된다.
//        btn1.setOnClickListener {
//            Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show()
//        }

//        btn1.setOnClickListener {
//            Toast.makeText(this, "Hello World" + it.javaClass.name, Toast.LENGTH_SHORT).show()
//        }
    }
}



