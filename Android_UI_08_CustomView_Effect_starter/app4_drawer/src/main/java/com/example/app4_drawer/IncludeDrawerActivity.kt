package com.example.app4_drawer

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.app4_drawer.databinding.IncludeDrawerBinding

class IncludeDrawerActivity : AppCompatActivity() {

    private lateinit var binding: IncludeDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = IncludeDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 삼선 보이기
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //뒤로가기 on
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24) //indicator바꾸기

        binding.mainInclude.mainBtn.setOnClickListener {
            binding.mainDrawerLayout.openDrawer(GravityCompat.START)
        }

        //메뉴 이벤트 처리
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1->{
                    Toast.makeText(this,"첫번째 클릭.", Toast.LENGTH_SHORT).show()
                }
                R.id.item2->{
                    Toast.makeText(this,"두번째 클릭.", Toast.LENGTH_SHORT).show()
                }
                R.id.item3->{
                    Toast.makeText(this,"세번째 클릭.", Toast.LENGTH_SHORT).show()
                }
            }

            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)

            true //여기서 종료. false: 다음 이벤트 수행
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            if(binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            }else{
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}



