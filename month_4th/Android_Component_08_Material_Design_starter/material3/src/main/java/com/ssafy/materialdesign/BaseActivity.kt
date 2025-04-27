package com.ssafy.materialdesign

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.materialdesign.databinding.ActivityBaseBinding

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDemoActionBar()
        binding.activityContainer.addView(onCreateActivityView(LayoutInflater.from(this), binding.activityContainer, savedInstanceState))
    }

    private fun initDemoActionBar() {
        if (shouldShowDefaultActionBar()) {
            setSupportActionBar(binding.toolbar)
            setActionBarTitle(supportActionBar!!)
            if (shouldShowDefaultActionBarCloseButton()) {
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            }
        } else {
            binding.toolbar.visibility = View.GONE
        }
    }

    // action bar를 보일것인가?
    protected open fun shouldShowDefaultActionBar(): Boolean {
        return false
    }

    // 기본 닫기(X)를 보일것인가?
    protected open fun shouldShowDefaultActionBarCloseButton(): Boolean {
        return false
    }
    // 타이틀은 어떤값으로 할지. default
    private fun getDefaultTitle():String{
        val pm: PackageManager = packageManager
        val info: PackageInfo = pm.getPackageInfo(packageName, 0) //com.ssafy.materialdesign
        return  info.applicationInfo.loadLabel(packageManager).toString() //MaterialDesign
    }

    // 타이틀은 어떤값으로 할지.
    protected open fun getActionBarTitle():String{
        return ""
    }

    private fun setActionBarTitle(actionBar: ActionBar) {
        if(getActionBarTitle() == ""){
            actionBar.title = getDefaultTitle()
        }else{
            actionBar.title = getActionBarTitle();
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    abstract fun onCreateActivityView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View
}