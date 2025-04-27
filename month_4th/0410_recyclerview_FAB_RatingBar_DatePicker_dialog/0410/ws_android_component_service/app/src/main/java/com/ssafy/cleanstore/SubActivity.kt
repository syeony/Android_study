package com.ssafy.cleanstore

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.cleanstore.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySubBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dialog.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.rating_bar, null)

        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)

        val builder = AlertDialog.Builder(this)
            .setTitle("별점을 선택해주세요")
            .setView(dialogView)
            .setPositiveButton("확인") { dialog, _ ->
                val rating = ratingBar.rating
                Toast.makeText(this, "선택한 별점: $rating", Toast.LENGTH_SHORT).show()
                binding.score.text="${rating}점"
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

        builder.show()
    }
}