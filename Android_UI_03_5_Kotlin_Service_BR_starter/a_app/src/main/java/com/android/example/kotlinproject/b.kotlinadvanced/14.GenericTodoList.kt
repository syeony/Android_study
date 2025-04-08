package com.android.example.kotlinproject.b.kotlinadvanced

import com.android.example.kotlinproject.b.kotlinadvanced.ToDoContainerImpl.Index.todoIdx

data class ToDo(val title: String,
                val content: String,
                var idx: Int = todoIdx++,
                var completed: Boolean = false)

interface ToDoContainer<T> {
    fun addNewItem(item: T)
    fun markCompleted(idx: Int)
}

class ToDoContainerImpl : ToDoContainer<ToDo> {
    var todoItems: MutableList<ToDo> = arrayListOf()
    override fun addNewItem(item: ToDo) {
        println("addNewItem : $item")
        todoItems.add(item)
    }
    override fun markCompleted(idx: Int) {
        todoItems.filter { it.idx == idx }[0].let {
            it.completed = true
            println("$idx is marked as completed")
            println("$it")
        }
    }
    companion object Index{
        var todoIdx = 1
    }
}



fun main(){

    var todayTodoList = ToDoContainerImpl()
    todayTodoList.addNewItem(ToDo("자바공부", "자바 기본은 마스터 했음."))
    todayTodoList.addNewItem(ToDo("코틀린공부", "코틀린 공부를 시작하자!!!!"))
    todayTodoList.markCompleted(1)

}


