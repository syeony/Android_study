package com.ssafy.contentprovider.note

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "NoteEditActivity_싸피"

class NoteEditActivity : AppCompatActivity() {

    private lateinit var mTitleText: EditText
    private lateinit var mBodyText: EditText
    private var mRowId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        mRowId = savedInstanceState?.getLong(Notes.ID) ?: -1L

        // -1 이면 앞 Activity에서 넘어온 경우이므로, intent에서 값을 꺼내서, 있으면 수정. 입력:-1
        if (mRowId == -1L) {
            val extras = intent.extras
            mRowId = extras?.getLong(Notes.ID) ?: -1L
        }

        findViewById<Button>(R.id.confirm).setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun initView() {
        mTitleText = findViewById<View>(R.id.title) as EditText
        mBodyText = findViewById<View>(R.id.body) as EditText

        if (mRowId != -1L) {
            val note = getNote(mRowId)

            mTitleText.setText(note.TITLE)
            mBodyText.setText(note.BODY)
        }
    }

    //한건조회.
    // ContentUris.withAppendedId(Notes.CONTENT_URI, mRowId) 로 uri 뒤에 /{id} 형태로 만들어서
    //contentprovider 호출
    @SuppressLint("Range")
    private fun getNote(mRowId:Long):NotesDto{
        var result = NotesDto()
        val queryUri = Notes.CONTENT_URI
        val what = arrayOf(
            Notes.ID,
            Notes.TITLE,
            Notes.BODY
        )
        val uri = ContentUris.withAppendedId(queryUri, mRowId)
        contentResolver.query(uri, what, null, null, null)!!.use{
            if(it.moveToFirst()){
                val id = it.getLong(it.getColumnIndex(Notes.ID))
                val title = it.getString(it.getColumnIndex(Notes.TITLE))
                val body = it.getString(it.getColumnIndex(Notes.BODY))

                Log.d(TAG, "id:$id, title:$title, body:$body");

                result = NotesDto(id, title, body)
            }
        }
        return result
    }

    //비정상 종료시는 id를 기록한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(Notes.ID, mRowId)
    }

    //pause에서 저장하도록 구현
    override fun onPause() {
        super.onPause()
        saveState()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    //-1은 앞 Activity에서 입력으로 넘어온 경우
    private fun saveState() {
        val values = ContentValues()
        values.put(Notes.TITLE, mTitleText.text.toString())
        values.put(Notes.BODY, mBodyText.text.toString())

        if (mRowId == -1L) {
                contentResolver.insert(Notes.CONTENT_URI, values)!!
        } else {
            val uri = ContentUris.withAppendedId(Notes.CONTENT_URI, mRowId)
            contentResolver.update(uri, values, null, null)
        }
    }
}














