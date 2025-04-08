package com.ssafy.android_ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.button_0).text = getString(R.string.button)
        findViewById<Button>(R.id.button_0).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_1).text = getString(R.string.edittext)
        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent(this, EditTextActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).text = getString(R.string.imageview)
        findViewById<Button>(R.id.button_2).setOnClickListener{
            val intent = Intent(this, ImageView::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).text = getString(R.string.listview)
        findViewById<Button>(R.id.button_3).setOnClickListener{
            val intent = Intent(this, ListViewActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_4).text = getString(R.string.fontactivity)
        findViewById<Button>(R.id.button_4).setOnClickListener{
            val intent = Intent(this, FontActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_5).text = getString(R.string.linearlayout)
        findViewById<Button>(R.id.button_5).setOnClickListener{
            val intent = Intent(this, LinearLayoutKotlin::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_6).text = getString(R.string.linearlayout2)
        findViewById<Button>(R.id.button_6).setOnClickListener{
            val intent = Intent(this, DuplicatedActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_11).text = getString(R.string.relativelayout)
        findViewById<Button>(R.id.button_11).setOnClickListener{
            val intent = Intent(this, RelativeLayout::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_7).text = getString(R.string.framelayoutactivity)
        findViewById<Button>(R.id.button_7).setOnClickListener{
            val intent = Intent(this, FrameLayoutActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_8).text = getString(R.string.tablelayout)
        findViewById<Button>(R.id.button_8).setOnClickListener{
            val intent = Intent(this, TableLayout::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_9).text = getString(R.string.tablelayoutacitivity)
        findViewById<Button>(R.id.button_9).setOnClickListener{
            val intent = Intent(this, TableLayoutAcitivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_10).text = getString(R.string.gridlayout)
        findViewById<Button>(R.id.button_10).setOnClickListener{
            val intent = Intent(this, GridLayout::class.java)
            startActivity(intent)
        }
    }
}