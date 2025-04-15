package com.ssafy.contentprovider

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        findViewById<Button>(R.id.button_0).setOnClickListener{
            val intent = Intent(this, ActionViewActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent(this, SimpleResolverActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).setOnClickListener{
            val intent = Intent(this, ContactsActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).setOnClickListener{
            val intent = Intent(this, SimpleCursorAdapterActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_4).setOnClickListener{
            val intent = Intent(this, ImageResolver::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_5).setOnClickListener{
            val intent = Intent(this, MediaActivity::class.java);
            startActivity(intent)
        }
    }
}