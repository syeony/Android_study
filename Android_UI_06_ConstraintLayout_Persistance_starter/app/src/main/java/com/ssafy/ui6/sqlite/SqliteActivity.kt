package com.ssafy.ui6.sqlite

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.ui6.databinding.ActivitySqliteBinding

private const val TAG = "SqliteActivity_싸피"
class SqliteActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var dao: MyTableDao


    private val binding: ActivitySqliteBinding by lazy {
        ActivitySqliteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dbHelper = DBHelper(this, "newdb.db", null, 1)
        dao = MyTableDao(dbHelper.writableDatabase)
        Log.d(TAG, "onCreate: 생성된 DB 정보: $dao")

        //textview에 scroll 달기. xml에 scrollbars="vertical"추가하고 아래추가하기
        binding.listTv.movementMethod = ScrollingMovementMethod.getInstance()

        binding.insertBtn.setOnClickListener {
            val content = binding.contentEt.text.toString()
            dao.insert(content)
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            binding.listBtn.performClick() // listBtn 을 클릭해서 화면을 갱신함
        }

        binding.listBtn.setOnClickListener {
            val list = dao.list()
            val text = list.fold(""){
                    acc, myTableDto ->  "$acc${myTableDto.id} , ${myTableDto.txt}\n"
            }
            binding.listTv.text = text

            Log.d(TAG, "list: $list")
        }

        binding.updateBtn.setOnClickListener {
            val id = binding.idEt.text.toString()
            val content = binding.contentEt.text.toString()
            dao.update(id, content)
            Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()

            binding.listBtn.performClick()// 수정 완료 후 화면 갱신
        }

        binding.deleteBtn.setOnClickListener {
            val id = binding.idEt.text.toString()
            dao.delete(id)
            Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

            binding.listBtn.performClick()// 삭제 완료 후 화면 갱신
        }

        binding.searchBtn.setOnClickListener {
            val id = binding.idEt.text.toString()

            val result =  dao.select(id)
            binding.listTv.text = ""
            binding.contentEt.setText(result.txt)
        }
    }
}