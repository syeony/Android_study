package com.ssafy.cleanstore.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ssafy.cleanstore.dto.Stuff
import com.ssafy.cleanstore.service.dao.StuffDao

class BoundService : Service(){

    private lateinit var stuffDAO: StuffDao

    // 1. LocalBinder 클래스 정의
    inner class LocalBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }

    // 2. 바인더 객체 생성
    private val binder = LocalBinder()

    // 3. onCreate 에서 DAO 생성 및 DB 연결
    override fun onCreate() {
        super.onCreate()
        stuffDAO = StuffDao(this, "clean_store.db",null,1)
        stuffDAO.writableDatabase // DB 연결
    }

    // 4. 바인더 반환
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    // 5. 외부에서 사용할 수 있도록 DAO 함수 래핑
    fun add(stuff: Stuff): Long {
        return stuffDAO.add(stuff)
    }

    fun update(stuff: Stuff): Int {
        return stuffDAO.update(stuff)
    }

    fun remove(id: Int): Int {
        return stuffDAO.remove(id)
    }

    fun search(id: Int): Stuff? {
        return stuffDAO.search(id)
    }

    fun getList(): List<Stuff> {
        return stuffDAO.getList()
    }

    fun searchByName(namePart: String): List<Stuff>{
        return stuffDAO.searchByName(namePart)
    }

    fun getListSortedByCount(): List<Stuff> {
        return stuffDAO.getListSortedByCount()
    }

    // 6. 서비스가 종료될 때 DAO 닫기
    override fun onDestroy() {
        stuffDAO.close()
        super.onDestroy()
    }

}