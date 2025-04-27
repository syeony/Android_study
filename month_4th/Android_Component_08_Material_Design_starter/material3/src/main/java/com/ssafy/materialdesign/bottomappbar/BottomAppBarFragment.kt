package com.ssafy.materialdesign.bottomappbar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.MainActivity
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.databinding.BottomappbarContentBinding
import com.ssafy.materialdesign.databinding.FragmentBottomAppBarBinding

private const val TAG = "Buttons2Activity_싸피"
class BottomAppBarFragment : BaseFragment() {

    private var _binding: FragmentBottomAppBarBinding? = null
    private val binding get() = _binding!!

    //include 된 layout bind
    private val includeBinding by lazy{
        BottomappbarContentBinding.bind(binding.root)
    }

    private lateinit var bottomDrawerBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //fragment에서 옵션 메뉴 활성화.
        setHasOptionsMenu(true)
    }
    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        _binding = FragmentBottomAppBarBinding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 하위 삼선 옆에 menu 생성해서 붙히기.
        (requireActivity() as MainActivity).setSupportActionBar(binding.bar)
        setUpBottomDrawer()// 삼선클릭시 bottom sheet 보이기

        binding.fab.setOnClickListener(View.OnClickListener { v: View? ->
            showSnackbar(binding.fab.getContentDescription())
        })

        binding.navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            item.title?.let { showSnackbar(it) }
            false
        }

        setUpDemoControls()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.demo_primary_alternate, menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        menuItem.title?.let { showSnackbar(it) }

        return true
    }


    protected fun setUpBottomDrawer() {
        val bottomDrawer: View = binding.coordinatorLayout.findViewById<View>(R.id.bottom_drawer)
        bottomDrawerBehavior = BottomSheetBehavior.from(bottomDrawer)
        bottomDrawerBehavior.setUpdateImportantForAccessibilityOnSiblings(true)
        bottomDrawerBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
        binding.bar.setNavigationOnClickListener(
            View.OnClickListener { v: View? ->
                bottomDrawerBehavior.setState(
                    BottomSheetBehavior.STATE_HALF_EXPANDED
                )
            })
    }

    private fun setUpDemoControls() {

        // Set up FAB visibility mode toggle buttons.
        includeBinding.showFabButton.setOnClickListener { v: View? -> binding.fab.show() }
        includeBinding.hideFabButton.setOnClickListener { v: View? -> binding.fab.hide() }

        //scroll 하면 숨기기 or 보이기.
        includeBinding.barScrollSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            binding.bar.hideOnScroll = isChecked
        }
    }

    private fun showSnackbar(text: CharSequence) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT)
            //anchorview : 겹치거나 위치시킬 View의 ID. FAB 보여짐 여부에 따라 조금씩 위치가 다름.
            .setAnchorView(if (binding.fab.getVisibility() == View.VISIBLE) binding.fab else binding.bar)
//            .setAnchorView(includeBinding.showFabButton)
            .show()
    }

}