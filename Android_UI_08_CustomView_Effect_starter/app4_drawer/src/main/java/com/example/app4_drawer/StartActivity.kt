package com.example.app4_drawer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent(this, IncludeActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).setOnClickListener{
            val intent = Intent(this, IncludeDrawerActivity::class.java);
            startActivity(intent)
        }
        findViewById<Button>(R.id.button_3).setOnClickListener{
            val intent = Intent(this, IncludeDrawer2Activity::class.java);
            startActivity(intent)
        }


    }
}