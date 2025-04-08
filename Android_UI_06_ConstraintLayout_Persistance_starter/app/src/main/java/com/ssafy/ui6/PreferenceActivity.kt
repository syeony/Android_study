package com.ssafy.ui6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PreferenceActivity : AppCompatActivity() {
    private lateinit var prefResultEv :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_preference)

        prefResultEv = findViewById(R.id.pref_result)

        val prefs = getPreferences(MODE_PRIVATE)
        // 정보 추출하기
        val v = prefs.getInt("KEY", 0)
        // 정보 저장하기
        val editor = prefs.edit()
        editor.putInt("KEY", v.toInt()+1)

        // commit : 즉시 반영, apply() :비동기 반영
        // main thread이고 결과값에 대한 처리가 없다면 apply 추천. 아니면 commit 사용
        editor.apply()
//        editor.commit()

        prefResultEv.text = "preference test: $v"

    }
}