package com.ssafy.materialdesign.shapetheming

import androidx.annotation.StyleRes
import com.ssafy.materialdesign.R

class ShapeThemingShrineFragment : ShapeThemingFragment() {


    @StyleRes
    override fun getShapeTheme(): Int {
        return R.style.ThemeOverlay_Shrine
    }
}
