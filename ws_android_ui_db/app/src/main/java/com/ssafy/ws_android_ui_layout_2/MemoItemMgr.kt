//package com.ssafy.ws_android_ui_layout_2
//
//object MemoItemMgr { //object 로 선언하면 singletone 클래스가 된다.
//    // 메모를 담는 ArrayList
//    private var memos = arrayListOf<MemoDto>()
//
//    //전체 목록 반환
//    fun getList(): ArrayList<MemoDto> {
//        return memos;
//    }
//
//    // Memo 개수 반환
//    fun size(): Int {
//        return memos.size
//    }
//
//    // Memo 추가
//    fun add(item: MemoDto) {
//        memos.add(item)
//    }
//
//    // 특정 인덱스의 Memo 반환
//    fun search(index: Int): MemoDto {
//        return memos.get(index)
//    }
//
//    // Memo 수정
//    fun update(index: Int, item: MemoDto) {
//        if (index in memos.indices) {
//            memos[index] = item
//        }
//    }
//
//    // Memo 삭제
//    fun remove(index: Int) {
//        if (index in memos.indices) {
//            memos.removeAt(index)
//        }
//    }
//
//    // 전체 Memo 삭제
//    fun clear() {
//        memos.clear()
//    }
//
//
//}