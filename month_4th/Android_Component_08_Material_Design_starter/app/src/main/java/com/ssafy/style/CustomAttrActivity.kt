package com.ssafy.style

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "CustomAttrActivity_싸피"
class CustomAttrActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_attr)

        // attr에 titleSize로 설정된 값 읽어오기 : 40sp, 여러값을 읽으려면 intArray에 추가
        val typedArray = theme.obtainStyledAttributes(android.R.style.Widget_TextView, intArrayOf(R.attr.titleSize))
        val titleSize = typedArray.getDimensionPixelSize(0, 0) //1개 읽었으니, 0번째 값
        typedArray.recycle() //  TypedArray는 사용하고 나서 recycle 해 줘야 함.

        Log.d(TAG, "titleSize 읽은 값 : $titleSize")

        // 자바는 sp로 처리가 안되고, 항상 pixel로 처리해야 함. 40sp가 현재 해상도에서 몇 pixel로 나오는지 계산
        val pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 40f, resources.displayMetrics).toInt()
        Log.d(TAG, "40sp의 pixel은: $pixel")

    }
}