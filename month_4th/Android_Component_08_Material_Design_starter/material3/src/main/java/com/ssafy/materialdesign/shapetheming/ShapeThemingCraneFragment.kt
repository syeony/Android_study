package com.ssafy.materialdesign.shapetheming

import androidx.annotation.StyleRes
import com.ssafy.materialdesign.R

class ShapeThemingCraneFragment : ShapeThemingFragment() {


    @StyleRes
    override fun getShapeTheme(): Int {
        return R.style.ThemeOverlay_Crane
    }
}
