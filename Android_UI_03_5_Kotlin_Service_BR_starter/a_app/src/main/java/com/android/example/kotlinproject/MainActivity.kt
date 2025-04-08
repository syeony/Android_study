package com.android.example.kotlinproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction

class MainActivity : AppCompatActivity() {

    operator fun get(id:Int): View {
        return findViewById(id)
    }

    operator fun TextView.plusAssign(txt:String){
        setText(txt);
    }

    /* findViewById(R.id.textview)  ==>  this[R.id.textview] 가 되도록 연산자 오버로딩 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = this[R.id.textview] as TextView
        title += "안녕. 연산자 오버로딩222"
//        title.setText("안녕. 연산자 오버로딩")

    }
}