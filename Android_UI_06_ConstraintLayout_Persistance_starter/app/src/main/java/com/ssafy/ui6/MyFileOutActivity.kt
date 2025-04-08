package com.ssafy.ui6

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.util.*

private const val TAG = "FileOutActivity_싸피"

class MyFileOutActivity : AppCompatActivity() {
    private lateinit var statusTV: TextView
    private lateinit var saveBtn:Button
    private lateinit var loadBtn:Button
    private lateinit var file: File

    private lateinit var assetsBtn:Button
    private lateinit var externalBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_out)

        statusTV = findViewById(R.id.status_tv)
        saveBtn = findViewById(R.id.save_btn)
        loadBtn = findViewById(R.id.load_btn)
        file = File(filesDir, "data.txt")
        assetsBtn = findViewById(R.id.assets_btn)
        externalBtn = findViewById(R.id.external_btn)

        saveBtn.setOnClickListener {
            saveFile()
        }
        loadBtn.setOnClickListener {
            loadFile()
        }
        assetsBtn.setOnClickListener {
            loadAssets()
        }

        externalBtn.setOnClickListener {
            Log.d(TAG, "현재 미디어의 상태 ${Environment.getExternalStorageState()}")
            Log.d(TAG, "외장 메모리 경로: ${getExternalFilesDir(null)}") // null: 시스템 default 경로 리턴.
            val path = getExternalFilesDir(null)!!.absolutePath
            Log.d(TAG, "onCreate: $path")
            if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
                val file = File(getExternalFilesDir(null), "data.txt")
                try{
                    //FileWriter(file, true) 이면 기존파일에 추가.
                    BufferedWriter(FileWriter(file, true)).use {
                        it.append("외부 저장소에 write 하기\n")
                    }
                    BufferedReader(FileReader(file)).useLines {
                        //fold : 초기값을 설정하고 요소의 첫번째 부터 람다식 오른쪽의 누적값으로 적용하여 accu가 되고, 다음값으로..
                        val lines = it.fold(""){
                                accu, now ->"$accu\n$now"
                        }
                        statusTV.text = lines
                    }
                }catch(e: IOException){
                    Log.e(TAG, "onCreate: 외장 메모리 사용 실패", e)
                }
            }else{
                Toast.makeText(this, "이 앱은 외장 메모리를 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

  private fun saveFile(){
        try {
            // use: try use resource
            BufferedWriter(FileWriter(file, true)).use {
                it.append("지금 시각은 ${Date()} 입니다.\n")
            }
            statusTV.text = "저장 완료"
        }catch(e:IOException){
            Log.e(TAG, "saveFile: ", e)
        }
    }

    private fun loadFile(){
        Log.d(TAG, "file: ${file.canonicalPath}")
        try {
            //useLines : BufferedReader로 읽어서 처리. 모두 읽으면 close한다.
            BufferedReader(FileReader(file)).useLines {

                // 한줄씩 읽어서 순차적으로 진행.
                //fold : 초기값을 설정하고 요소의 첫번째 부터 람다식 오른쪽의 누적값으로 적용하여 accu가 되고, 다음값으로..
                val data =it.fold(""){
                    accu, now ->  "$accu\n$now"
                }
                Log.d(TAG, "loadFile: $data")
                statusTV.text = data
            }

        }catch(e:IOException){
            Log.e(TAG, "loadFile: ", e)
        }
    }
    private fun loadAssets(){
        try{
            BufferedReader(InputStreamReader(assets.open("data.txt"))).useLines {
                val data =it.fold(""){
                        accu, now ->"$accu\n$now"
                }
                Log.d(TAG, "loadFile: $data")
                statusTV.text = data
            }
        }catch(e:IOException){
            Log.e(TAG, "onCreate: assets 파일 로딩 실패", e)
        }
    }
}