package com.ssafy.custom.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ssafy.custom.R

private const val TAG = "CustomNameCard_싸피"
class CustomNameCard : ConstraintLayout {
    lateinit var iv: ImageView
    lateinit var name: TextView
    lateinit var org: TextView

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_name_card_view, this, false)
        addView(view);
        iv = findViewById(R.id.user_img_iv)
        name = findViewById(R.id.userName)
        org = findViewById(R.id.user_org_tv)
    }
    // attrs.xml 파일로부터 속성 정보 확보 - typedArray
    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomNameCard)
        setTypedArray(typedArray)
    }
    // 속성값을 view 요소들에 연결
    private fun setTypedArray(typedArray: TypedArray) {
        name.text = typedArray.getText(R.styleable.CustomNameCard_userName)
        org.text = typedArray.getText(R.styleable.CustomNameCard_userOrg)
        iv.setImageResource(
            typedArray.getResourceId(
                R.styleable.CustomNameCard_userImg,
                R.drawable.ic_launcher_foreground
            )
        )
        typedArray.recycle()
    }
}