package com.ssafy.paint_basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

data class Point(var x:Float, var y:Float, var isContinue:Boolean)

private const val TAG = "DrawSample_싸피"
class DrawSample : View  {
    var list = arrayListOf<Point>()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.setColor(Color.RED)
        paint.strokeWidth = 50F
        paint.style = Paint.Style.STROKE //라인.

        val path= Path()
        list.forEach {
            if(it.isContinue){
                path.lineTo(it.x,it.y)
//                canvas.drawLine(list[index-1].x, list[index-1].y, list[index].x, list[index].y, paint)
            }else{
                path.moveTo(it.x,it.y)
            }
        }
        canvas.drawPath(path,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "onTouchEvent.Down: $event")
                list.add(Point(event.x,event.y,false))
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "onTouchEvent.Move: $event")
                list.add(Point(event.x,event.y,true))
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "onTouchEvent.Up: $event")
                list.add(Point(event.x,event.y,true))
            }
        }
        invalidate() // 화면을 다시 그리도록 요청. invalidate --> onDraw 호출한다.

        //touch이후에 event를 전달할것인가? true: 여기서 종료. false :뒤로 전달.
        // touch -> click
        return true
    }

}