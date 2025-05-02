package com.ssafy.exercise.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ssafy.exercise.dto.Memo
import com.ssafy.exercise.service.dao.MemoDao

class BoundService :Service() {

    private lateinit var memoDao: MemoDao

    // 1. LocalBinder 클래스 정의
    inner class LocalBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }

    // 2. 바인더 객체 생성
    private val binder = LocalBinder()

    // 3. onCreate 에서 DAO 생성 및 DB 연결
    override fun onCreate() {
        super.onCreate()
        memoDao = MemoDao(this, "clean_store.db",null,1)
        memoDao.writableDatabase // DB 연결
    }

    // 4. 바인더 반환
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    // 5. 외부에서 사용할 수 있도록 DAO 함수 래핑
    fun add(memo: Memo): Long {
        return memoDao.add(memo)
    }

    fun update(memo: Memo): Int {
        return memoDao.update(memo)
    }

    fun remove(id: Int): Int {
        return memoDao.remove(id)
    }

    fun search(id: Int): Memo? {
        return memoDao.search(id)
    }

    fun getList(): List<Memo> {
        return memoDao.getList()
    }

    // 운동종류 오름차순 정렬
    fun getListSortedByExerciseTypeAsc(): List<Memo> {
        return memoDao.getListSortedByExerciseTypeAsc()
    }

    // 운동종류 내림차순 정렬
    fun getListSortedByExerciseTypeDesc(): List<Memo> {
        return memoDao.getListSortedByExerciseTypeDesc()
    }

    // 운동일자 오름차순 정렬
    fun getListSortedByDateAsc(): List<Memo> {
        return memoDao.getListSortedByDateAsc()
    }

    // 운동일자 내림차순 정렬
    fun getListSortedByDateDesc(): List<Memo> {
        return memoDao.getListSortedByDateDesc()
    }

    // 6. 서비스가 종료될 때 DAO 닫기
    override fun onDestroy() {
        memoDao.close()
        super.onDestroy()
    }
}