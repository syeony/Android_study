package com.example.app4_drawer

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.app4_drawer.databinding.IncludeDrawer2Binding

class IncludeDrawer2Activity : AppCompatActivity() {

    private lateinit var binding: IncludeDrawer2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = IncludeDrawer2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainInclude.mainBtn.setOnClickListener {
//            binding.mainDrawerLayout.openDrawer(GravityCompat.START)
            binding.mainDrawerLayout.openDrawer(binding.drawLinear)
        }
        //action bar에 삼선 보이기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_format_list_bulleted_24)


    }

    //햄버거 메뉴 선택시 drawer 펼치기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            }else{
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}



