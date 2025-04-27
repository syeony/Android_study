package com.ssafy.materialdesign.shapetheming

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.MainActivity
import com.ssafy.materialdesign.R

private const val TAG = "ShapeThemingB_싸피"
abstract class ShapeThemingFragment : BaseFragment() {


    private var statusBarColor = 0
    private lateinit var wrappedContext: ContextThemeWrapper

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        Log.d(TAG, "onCreateView: ")
        wrappedContext = ContextThemeWrapper(context, getShapeTheme())
        val layoutInflaterWithThemedContext = layoutInflater.cloneInContext(wrappedContext)
        val window = requireActivity().window
        statusBarColor = window.statusBarColor
        val value = TypedValue()
        wrappedContext.theme.resolveAttribute(R.attr.colorPrimaryDark, value, true)
        window.statusBarColor = value.data
        return super.onCreateView(layoutInflaterWithThemedContext, viewGroup, bundle)
    }

    override fun onDestroyView() {
        val window = requireActivity().window
        window.statusBarColor = statusBarColor
        super.onDestroyView()
    }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        Log.d(TAG, "onCreateFragmentView: ")
        val view: View = layoutInflater.inflate(R.layout.cat_shape_theming_container, viewGroup, false )
        val container = view.findViewById<ViewGroup>(R.id.container)
        layoutInflater.inflate(R.layout.cat_shape_theming_content, container,true)

        val materialButton = container.findViewById<MaterialButton>(R.id.material_button)
        val materialAlertDialogBuilder: MaterialAlertDialogBuilder =
            MaterialAlertDialogBuilder(requireContext(), getShapeTheme())
                .setTitle(R.string.cat_shape_theming_dialog_title)
                .setMessage(R.string.cat_shape_theming_dialog_message)
                .setPositiveButton(R.string.cat_shape_theming_dialog_ok, null)
        materialButton.setOnClickListener { v: View? -> materialAlertDialogBuilder.show() }
        val bottomSheetDialog = BottomSheetDialog(wrappedContext)

        bottomSheetDialog.setContentView(R.layout.cat_shape_theming_bottomsheet_content)
//        bottomSheetDialog.behavior.peekHeight = 500

        val button = container.findViewById<MaterialButton>(R.id.material_button_2)
        button.setOnClickListener { v: View? -> bottomSheetDialog.show() }
        return view
    }

    @StyleRes
    protected abstract fun getShapeTheme(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.add(0,0,0,"Base")
        menu.add(0,1,0,"Crane")
        menu.add(0,2,0,"Fortnightly")
        menu.add(0,3,0,"Shrine")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            0 -> (requireActivity() as MainActivity).changeFragment(ShapeThemingBaseFragment())
            1 -> (requireActivity() as MainActivity).changeFragment(ShapeThemingCraneFragment())
            2 -> (requireActivity() as MainActivity).changeFragment(ShapeThemingFortnightlyFragment())
            3 -> (requireActivity() as MainActivity).changeFragment(ShapeThemingShrineFragment())
        }

        return super.onOptionsItemSelected(item)
    }
}
