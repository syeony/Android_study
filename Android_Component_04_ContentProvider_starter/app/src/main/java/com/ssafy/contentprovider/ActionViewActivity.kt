package com.ssafy.contentprovider

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActionViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activon_view)

        findViewById<Button>(R.id.button_0).text = "연락처 보기"
        findViewById<Button>(R.id.button_0).setOnClickListener{
            val intent = Intent()


            startActivity(intent)
        }

        findViewById<Button>(R.id.button_1).text = "연락처 1번 보기"
        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent()


            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).text = "연락처 등록"
        findViewById<Button>(R.id.button_2).setOnClickListener{
            val intent = Intent()


            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).text = "1번 연락처 수정"
        findViewById<Button>(R.id.button_3).setOnClickListener{
            val intent = Intent()


            startActivity(intent)
        }

        //ACTION_CALL: 전화걸기 permission필요함.
        findViewById<Button>(R.id.button_4).text = "특정 번호 전화연결"
        findViewById<Button>(R.id.button_4).setOnClickListener{
            val intent = Intent()


            startActivity(intent)
        }

        findViewById<Button>(R.id.button_5).text = "SMS 작성"
        findViewById<Button>(R.id.button_5).setOnClickListener{
            val intent = Intent()


            startActivity(intent)
        }

        findViewById<Button>(R.id.button_6).text = "위경도로 map 실행"
        findViewById<Button>(R.id.button_6).setOnClickListener{
            val intent = Intent()


            startActivity(intent)
        }

        findViewById<Button>(R.id.button_7).text = "웹사이트 이동"
        findViewById<Button>(R.id.button_7).setOnClickListener{
            val intent = Intent()


            startActivity(intent)
        }

        findViewById<Button>(R.id.button_8).text = "특정앱 실행/마켓 이동"
        findViewById<Button>(R.id.button_8).setOnClickListener{
            val go = "com.kakao.talk"
            val wantToGo = packageManager.getLaunchIntentForPackage(go)
            if( wantToGo == null ){
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse("market://details?id=$go")
                startActivity(intent)  // market이 없으면?
            }else{
                wantToGo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(wantToGo)
            }
        }
    }
}