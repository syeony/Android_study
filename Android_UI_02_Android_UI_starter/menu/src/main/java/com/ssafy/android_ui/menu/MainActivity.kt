package com.ssafy.android_ui.menu

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var contextMenuBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contextMenuBtn = findViewById(R.id.context_menu_btn)
        // long click 시 context 메뉴를 연결할 view 등록
        registerForContextMenu(contextMenuBtn)

        findViewById<Button>(R.id.popup_menu_btn).setOnClickListener {
            val popupMenu = PopupMenu(applicationContext, it)
            menuInflater.inflate(R.menu.pupupmenu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.popup_menu1) {
                    Toast.makeText(this@MainActivity, "메뉴 1 클릭", Toast.LENGTH_SHORT).show()
                } else if (menuItem.itemId == R.id.popup_menu2) {
                    Toast.makeText(this@MainActivity, "메뉴 2 클릭", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "메뉴 3 클릭", Toast.LENGTH_SHORT).show()
                }
                false
            }
            popupMenu.show();
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        
        menuInflater.inflate(R.menu.contextmenu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.context_menu_blue -> contextMenuBtn.setTextColor(Color.BLUE)
            R.id.context_menu_red -> contextMenuBtn.setTextColor(Color.RED)
            R.id.context_menu_green -> contextMenuBtn.setTextColor(Color.GREEN)
        }
        return super.onContextItemSelected(item)
    }

    // 옵션 메뉴 생성.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menutest, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_exit) {
            finish()
        } else {
            Toast.makeText(this, getString(R.string.msg, item.title), Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}