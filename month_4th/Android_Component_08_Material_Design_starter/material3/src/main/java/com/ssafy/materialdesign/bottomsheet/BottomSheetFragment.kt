package com.ssafy.materialdesign.bottomsheet

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.databinding.BottomsheetDialogContentBinding
import com.ssafy.materialdesign.databinding.FragmentBottomsheetBinding

private const val TAG = "BottomSheetFragment_싸피"
class BottomSheetFragment : BaseFragment() {

    //바닥에 깔린 화면
    private var _binding: FragmentBottomsheetBinding? = null
    private val binding get() = _binding!!

    //버튼 클릭하여 뜨는 bottomSheet의 화면
    private var _bindingBottomSheetDialog: BottomsheetDialogContentBinding? = null
    private val bindingBottomSheetDialog get() = _bindingBottomSheetDialog!!

    //버튼 클릭하여 뜨는 bottomSheet는 dialog로 동작하는데, 이걸 담을 BottomSheetDialog
    private lateinit var bottomSheetDialog: BottomSheetDialog

    //BottomSheet의 코드상 조작은 behavior 를 통해서 이루어진다.
    //초기엔 보이지 않지만, 버튼 클릭시 보이게될 bottomsheet의 behavior
    private lateinit var bottomSheetDialogBehavior: BottomSheetBehavior<FrameLayout>
    //바닥에 고정되어서 보이는 bottomsheet의 behavior
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    // java에서는 pixel만 사용가능하며, dp를 그대로 사용하지 못함. 200dp 를 pixel로 변환.
    private var defaultPeekHeight = 0

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        _binding = FragmentBottomsheetBinding.inflate(layoutInflater, viewGroup, false)
        _bindingBottomSheetDialog = BottomsheetDialogContentBinding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _bindingBottomSheetDialog = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // java에서는 pixel만 사용가능하며, dp를 그대로 사용하지 못함. 200dp 를 pixel로 변환.
        defaultPeekHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, requireContext().resources.displayMetrics).toInt()

        // 버튼클릭하면 뜰 BottomSheetDialog 만들기
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bindingBottomSheetDialog.root)
        bottomSheetDialog.setDismissWithAnimation(true)

        // 버튼 클릭해서 뜨는 BottomSheet의 behavior를 dialog behavior로 지정
        bottomSheetDialogBehavior = bottomSheetDialog.behavior

        //바닥에 깔린 bottomSheet의 behavior는 xml에 지정되어 있다.
        bottomSheetBehavior = BottomSheetBehavior.from(binding.persistBottomsheetDrawer)

        // 처음 열릴때 높이 지정.
        bottomSheetDialogBehavior.peekHeight = defaultPeekHeight


        //bottom sheet state callback 등록
        bottomSheetDialogBehavior.addBottomSheetCallback(createBottomSheetCallback(bindingBottomSheetDialog.bottomsheetState))
        bottomSheetBehavior.addBottomSheetCallback(createBottomSheetCallback(binding.catPersistentBottomsheetState))
        //bottom sheet state callback 등록

        //bottom sheet 높이 계산.
        updateBottomSheetHeight();

        // bottom modal bottomsheet button
        binding.bottomsheetButton.setOnClickListener { v: View ->
            bottomSheetDialog.show()
            bottomSheetDialog.setTitle(getText(R.string.cat_bottomsheet_title)) //Bottom Sheet

            Log.d(TAG, "onViewCreated: ${bindingBottomSheetDialog.root.layoutParams.height}")

            bindingBottomSheetDialog.catBottomsheetModalButton.setOnClickListener { v0: View? ->
                Toast.makeText(
                    v.context,
                    R.string.cat_bottomsheet_button_clicked,
                    Toast.LENGTH_SHORT
                ).show()
            }

            bindingBottomSheetDialog.catBottomsheetModalEnabledSwitch.setOnCheckedChangeListener { buttonSwitch: CompoundButton?, isSwitchChecked: Boolean ->
                val updatedText = getText(
                    if (isSwitchChecked) R.string.cat_bottomsheet_button_label_enabled else R.string.cat_bottomsheet_button_label_disabled
                )
                bindingBottomSheetDialog.catBottomsheetModalButton.text = updatedText
                bindingBottomSheetDialog.catBottomsheetModalButton.isEnabled = isSwitchChecked
            }
        }

        //full screen mode switch
        binding.catFullscreenSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                //두개 같이 켜지는 못하도록 설정.
                binding.catBottomsheetExpansionSwitch.setEnabled(!isChecked)
                updateBottomSheetHeight()
            })
        // restricte expantion switch
        binding.catBottomsheetExpansionSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                //두개 같이 켜지는 못하도록 설정.
                binding.catFullscreenSwitch.setEnabled(!isChecked)
                updateBottomSheetHeight()
                //고정
                bottomSheetBehavior.isDraggable = !isChecked
                bottomSheetDialogBehavior.isDraggable = !isChecked
            })

    }

    private fun updateBottomSheetHeight(){
        val persistsheetParams = binding.persistBottomsheetDrawer.layoutParams
        val bottomsheetParams = bindingBottomSheetDialog.bottomDrawer2.layoutParams

        var fitContents = true
        // fullscreen mode일때
        if(binding.catFullscreenSwitch.isChecked){
            Log.d(TAG, "updateBottomSheetHeight: 1")
            //높이 full가능하도록
            persistsheetParams.height = getWindowHeight()
            bottomsheetParams.height = getWindowHeight()

            // contents 높이에 맞지 않도록 설정해야 half 접는게 가능
            fitContents = false

            // 중간으로 접었을때 화면 크기 0.7로 지정
            bottomSheetDialogBehavior.halfExpandedRatio = 0.7f
            bottomSheetBehavior.halfExpandedRatio = 0.7f

        }else if(binding.catBottomsheetExpansionSwitch.isChecked){ // 크기 조정 잠겼을때(restricted expansion)
            Log.d(TAG, "updateBottomSheetHeight: 2")
            persistsheetParams.height = binding.persistBottomsheetDrawer.height
            bottomsheetParams.height = bindingBottomSheetDialog.bottomDrawer2.height
        }else{ //둘다 꺼진 경우
            Log.d(TAG, "updateBottomSheetHeight: 3")
            bottomsheetParams.height = getWindowHeight() * 3 / 5
            persistsheetParams.height = getWindowHeight() * 3 / 5
        }
        bottomSheetDialogBehavior.isFitToContents = fitContents
        bottomSheetBehavior.isFitToContents = fitContents
        binding.persistBottomsheetDrawer.layoutParams = persistsheetParams
        bindingBottomSheetDialog.bottomDrawer2.layoutParams = bottomsheetParams
    }


    private fun getWindowHeight(): Int {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display =  requireActivity().windowManager.currentWindowMetrics
            return display.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            return  displayMetrics.heightPixels
        }
    }

    private fun createBottomSheetCallback(text: TextView): BottomSheetCallback {
        // Set up BottomSheetCallback
        return object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> text.setText(R.string.cat_bottomsheet_state_dragging)
                    BottomSheetBehavior.STATE_EXPANDED -> text.setText(R.string.cat_bottomsheet_state_expanded)
                    BottomSheetBehavior.STATE_COLLAPSED -> text.setText(R.string.cat_bottomsheet_state_collapsed)
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        val bottomSheetBehavior =
                            BottomSheetBehavior.from(bottomSheet)
                        text.text = getString(
                            R.string.cat_bottomsheet_state_half_expanded,
                            bottomSheetBehavior.halfExpandedRatio
                        )
                    }
                    else -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        }
    }

}