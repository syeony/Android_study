package com.ssafy.ui6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.ui6.sqlite.SqliteActivity

class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.button_0).text = getString(R.string.mainactivity)
        findViewById<Button>(R.id.button_0).setOnClickListener{
            val intent = Intent(this, ConstraintLayoutActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_1).text = getString(R.string.preferences)
        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent(this, PreferenceActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).text = getString(R.string.file)
        findViewById<Button>(R.id.button_2).setOnClickListener{
            val intent = Intent(this, MyFileOutActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).text = getString(R.string.sqlite_database)
        findViewById<Button>(R.id.button_3).setOnClickListener{
            val intent = Intent(this, SqliteActivity::class.java);
            startActivity(intent)
        }

    }

}