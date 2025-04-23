package com.ssafy.ssafygo

import android.os.Bundle
import android.view.View
import android.webkit.RenderProcessGoneDetail
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ssafy.ssafygo.adapter.StoreAdapter
import com.ssafy.ssafygo.dao.StoreDAO
import com.ssafy.ssafygo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val storeDao = StoreDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        storeDao.dbOpenHelper(this)
        storeDao.open()

        init()

        binding.button.setOnClickListener {
            loadStore()
        }
    }

    private fun init(){
        binding.storeListView.visibility = View.GONE
        binding.status.text="준비 완료"
        binding.percentage.text = "0"
    }

    private fun loadStore() {
        binding.button.isEnabled = false
        binding.status.text = "불러오는 중입니다..."
        binding.progressBar.progress = 0
        binding.percentage.text = "0"

        CoroutineScope(Dispatchers.IO).launch {
            var progress = 0
            while (progress < 100) {
                val increment = Random.nextInt(1, 10)
                progress = (progress + increment).coerceAtMost(100)

                withContext(Dispatchers.Main) {
                    binding.progressBar.progress = progress
                    binding.percentage.text = "$progress"
                }

                delay(200L)
            }

            val storeList = storeDao.storeSelectAll()

            withContext(Dispatchers.Main) {
                if (storeList.isNotEmpty()) {
                    val adapter = StoreAdapter(binding.root.context, storeList)
                    binding.storeListView.adapter = adapter
                }

                setStoreTV()
            }
        }

    }

    //xml하드코딩했던거
//    private fun loadStore(){
//        binding.button.isEnabled = false
//        binding.status.text = "불러오는 중입니다..."
//        binding.progressBar.progress = 0
//
//        lifecycleScope.launch {
//            var progress = 0
//            while (progress < 100) {
//                // 1~10 사이의 랜덤 증가
//                val increment = Random.nextInt(1, 10)
//                progress = (progress+increment).coerceAtMost(100)
//
//                // UI 업데이트
//                binding.progressBar.progress = progress
//                binding.percentage.text = "$progress"
//
//                delay(200L) // 조금 기다림 (너무 빠르면 부자연스러움)
//            }
//
//            // 완료 처리
//            setStoreTV()
//        }
//    }

    private fun setStoreTV(){
        binding.storeListView.visibility = View.VISIBLE
        binding.button.isEnabled=true
        binding.status.text="불러오기 완료!!"
    }
}