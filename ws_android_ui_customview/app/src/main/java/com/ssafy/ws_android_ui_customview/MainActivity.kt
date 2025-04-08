package com.ssafy.ws_android_ui_customview

import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.ws_android_ui_customview.databinding.ActivityMainBinding

private const val TAG = "DrawActivity_싸피"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawView = binding.draw

        // 색상 버튼 처리
        binding.red.setOnClickListener {
            drawView.paintColor = Color.RED
        }

        binding.black.setOnClickListener {
            drawView.paintColor = Color.BLACK
        }

        binding.blue.setOnClickListener {
            drawView.paintColor = Color.BLUE
        }

        binding.clear.setOnClickListener {
            drawView.clear()
        }

        binding.eraser.setOnClickListener {
            drawView.paintColor = Color.WHITE
        }

        // SeekBar로 두께 조절
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                drawView.paintWidth = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
