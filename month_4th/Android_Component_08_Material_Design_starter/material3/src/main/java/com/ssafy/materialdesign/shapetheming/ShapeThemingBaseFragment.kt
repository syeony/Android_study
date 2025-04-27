package com.ssafy.materialdesign.shapetheming

import androidx.annotation.StyleRes
import com.ssafy.materialdesign.R

class ShapeThemingBaseFragment : ShapeThemingFragment() {


    @StyleRes
    override fun getShapeTheme(): Int {
        return R.style.ThemeOverlay_Baseline
    }

}
