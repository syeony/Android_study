package com.ssafy.ws_android_ui_layout_2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object MemoItemMgr { //object 로 선언하면 singletone 클래스가 된다.
    // 메모를 담는 ArrayList
    private var memos = arrayListOf<MemoItem>()

    //전체 목록 반환
    fun getList(): ArrayList<MemoItem> {
        return memos;

    }

    // Memo 개수 반환
    fun size(): Int {
        return memos.size
    }

    // Memo 추가
    fun add(item: MemoItem) {
        memos.add(item)

    }

    // 특정 인덱스의 Memo 반환
    fun search(index: Int): MemoItem {
        return memos.get(index)
    }

    // Memo 수정
    fun update(index: Int, item: MemoItem) {
        if (index in memos.indices) {
            memos[index] = item
        }
    }

    // Memo 삭제
    fun remove(index: Int) {
        if (index in memos.indices) {
            memos.removeAt(index)
        }
    }

    // 전체 Memo 삭제
    fun clear() {
        memos.clear()
    }


}