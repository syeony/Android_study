package com.ssafy.materialdesign.imageview

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import java.util.Random

class ImageViewFragment : BaseFragment() {

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_imageview, viewGroup, false)
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.togglegroup)

        // 2개 이미지를 가져와서 shape변경.
        val imageView = view.findViewById<ShapeableImageView>(R.id.image_view)
        val iconView = view.findViewById<ShapeableImageView>(R.id.icon_view)
        val shapes = SparseArray<ShapeAppearanceModel>()
        shapes.put(R.id.button_diamond,ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.CUT,  /*cornerSize=*/0f)
                .setAllCornerSizes(ShapeAppearanceModel.PILL)
                .build()
        )
        shapes.put(
            R.id.button_circle,
            ShapeAppearanceModel.builder().setAllCornerSizes(ShapeAppearanceModel.PILL).build()
        )

        shapes.put(R.id.button_square, ShapeAppearanceModel.builder().build())
        val random = Random()
        toggleGroup.addOnButtonCheckedListener { group: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean ->
            if (!isChecked) {
                return@addOnButtonCheckedListener
            }
            val shape = shapes[checkedId]

            // Randomly makes dog wink.
            imageView.setImageResource(
                if (random.nextBoolean()) R.drawable.dog_image else R.drawable.dog_image_wink
            )
            imageView.shapeAppearanceModel = shape
            iconView.shapeAppearanceModel = shape
        }
        return view
    }
}
