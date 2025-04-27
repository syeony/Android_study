package com.ssafy.materialdesign

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ssafy.materialdesign.databinding.FragmentBaseBinding

abstract class BaseFragment : Fragment() {
    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        _binding = FragmentBaseBinding.inflate(layoutInflater)

        initActionBar()
        binding.fragmentContainer.addView(onCreateFragmentView(layoutInflater, viewGroup, bundle))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener{
            (requireActivity() as MainActivity).pop()
        }
    }

    private fun initActionBar() {
        if (shouldShowDefaultActionBar()) {
            val activity = requireActivity() as AppCompatActivity
            activity.setSupportActionBar(binding.toolbar)
            setActionBarTitle(activity.supportActionBar!!)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            if (shouldShowDefaultActionBarCloseButton()) {
                activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        } else {
            binding.toolbar.visibility = View.GONE
        }
    }

    // action bar를 보일것인가?
    protected open fun shouldShowDefaultActionBar(): Boolean {
        return true
    }

    // 기본 닫기(X)를 보일것인가?
    protected open fun shouldShowDefaultActionBarCloseButton(): Boolean {
        return true
    }

    // 타이틀은 어떤값으로 할지
    protected open fun getDefaultTitle():String{
        val pm: PackageManager = requireActivity().packageManager
        val info: PackageInfo = pm.getPackageInfo(requireActivity().packageName, 0) //com.ssafy.materialdesign
        return  info.applicationInfo.loadLabel(requireActivity().packageManager).toString() //MaterialDesign
    }

    // 타이틀은 어떤값으로 할지.
    protected open fun getActionBarTitle():String{
        return ""
    }

    // getActionBarTitle이 있으면 그값으로, 없으면 class 이름으로.
    private fun setActionBarTitle(actionBar: ActionBar) {
        if(getActionBarTitle() == ""){
            actionBar.title = javaClass.simpleName
        }else{
            actionBar.title = getActionBarTitle();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun onCreateFragmentView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View

}