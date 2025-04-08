package com.ssafy.ws_android_ui_customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

data class Point(
    var x: Float,
    var y: Float,
    var isContinue: Boolean,
    var color: Int,
    var strokeWidth: Float
)

private const val TAG = "DrawSample_싸피"
class DrawSample(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var list = arrayListOf<Point>() //그려진 모든 점 저장 리스트
    // 초기 세팅
    var paintColor: Int = Color.BLACK
    var paintWidth: Float = 50F

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (list.isEmpty()) return

        // 선 그리는 도중 색상이나 두께가 바뀌었는지 체크하기 위해...
        var lastColor = list[0].color
        var lastWidth = list[0].strokeWidth

        val paint = Paint()
        paint.style = Paint.Style.STROKE

        val path = Path()
        for (i in list.indices) {
            val p = list[i]

            //색상이나 두께가 바뀌었거나 선이 끊겼을 경우 새로운 path 시작
            if (i > 0 && (p.color != lastColor || p.strokeWidth != lastWidth || !p.isContinue)) {
                // 이전 path 그리기
                paint.color = lastColor
                paint.strokeWidth = lastWidth
                canvas.drawPath(path, paint)

                // 새 path 시작
                path.reset()
                path.moveTo(p.x, p.y)
            } else {
                if (p.isContinue) {
                    path.lineTo(p.x, p.y)
                } else {
                    path.moveTo(p.x, p.y)
                }
            }

            lastColor = p.color
            lastWidth = p.strokeWidth
        }

        // 마지막 path 그리기
        paint.color = lastColor
        paint.strokeWidth = lastWidth
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                list.add(Point(event.x, event.y, false, paintColor, paintWidth))
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                list.add(Point(event.x, event.y, true, paintColor, paintWidth))
            }
        }
        invalidate()
        return true
    }

    fun clear() {
        list.clear()
        invalidate()
    }
}
