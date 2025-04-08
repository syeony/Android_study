package com.ssafy.permission

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.button_1).text = "권한없음. 오류발생"
        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent(this, MainActivity1::class.java);
            startActivity(intent)
        }


        findViewById<Button>(R.id.button_2).text = "권한체크"
        findViewById<Button>(R.id.button_2).setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).text = "요청창 띄우고 응답받기"
        findViewById<Button>(R.id.button_3).setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_4).text = "최종 권한 없을때 설정으로 이동"
        findViewById<Button>(R.id.button_4).setOnClickListener{
            val intent = Intent(this, MainActivity4::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_5).text = "추가. launch로 호출 사용"
        findViewById<Button>(R.id.button_5).setOnClickListener{
            val intent = Intent(this, MainActivity5::class.java);
            startActivity(intent)
        }
    }
}