package com.ssafy.materialdesign.sidesheet

import android.animation.LayoutTransition
import android.os.Bundle
import android.text.Layout
import android.util.LayoutDirection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets.Side
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.google.android.material.sidesheet.SideSheetBehavior
import com.google.android.material.sidesheet.SideSheetCallback
import com.google.android.material.sidesheet.SideSheetDialog
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class SideSheetFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view = layoutInflater.inflate(R.layout.fragment_sidesheet, viewGroup, false )
        setUpToolbar(view)

        // Set up standard side sheet.
        val standardRightSideSheet = setUpSideSheet(
            view,
            R.id.standard_side_sheet_container,
            R.id.show_standard_side_sheet_button,
            R.id.close_icon_button
        )
        setSideSheetCallback(
            standardRightSideSheet, R.id.side_sheet_state_text, R.id.side_sheet_slide_offset_text
        )

        // Set up detached standard side sheet.
        val detachedStandardSideSheet = setUpSideSheet(
            view,
            R.id.standard_detached_side_sheet_container,
            R.id.show_standard_detached_side_sheet_button,
            R.id.detached_close_icon_button
        )
        setSideSheetCallback(
            detachedStandardSideSheet,
            R.id.detached_side_sheet_state_text,
            R.id.detached_side_sheet_slide_offset_text
        )

        // Set up vertically scrolling side sheet.
        val verticallyScrollingSideSheet = setUpSideSheet(
            view,
            R.id.vertically_scrolling_side_sheet_container,
            R.id.show_vertically_scrolling_side_sheet_button,
            R.id.vertically_scrolling_side_sheet_close_icon_button
        )
        setSideSheetCallback(
            verticallyScrollingSideSheet,
            R.id.vertically_scrolling_side_sheet_state_text,
            R.id.vertically_scrolling_side_sheet_slide_offset_text
        )

        // Set up modal side sheet.
        val sideSheetDialog = SideSheetDialog(requireContext())
        setUpModalSheet(
            sideSheetDialog,
            R.layout.sidesheet_content,
            R.id.m3_side_sheet,
            R.id.side_sheet_title_text,
            R.string.cat_sidesheet_modal_title
        )
        val showModalSideSheetButton = view.findViewById<View>(R.id.show_modal_side_sheet_button)
        showModalSideSheetButton.setOnClickListener { v: View? -> sideSheetDialog.show() }
        sideSheetDialog
            .behavior
            .addCallback(
                createSideSheetCallback(
                    sideSheetDialog.findViewById(R.id.side_sheet_state_text)!!,
                    sideSheetDialog.findViewById(R.id.side_sheet_slide_offset_text)!!
                )
            )
        val modalSideSheetCloseIconButton =
            sideSheetDialog.findViewById<View>(R.id.close_icon_button)
        modalSideSheetCloseIconButton?.setOnClickListener { v: View? -> sideSheetDialog.hide() }

        // Set up detached modal side sheet.
        val detachedSideSheetDialog =
            SideSheetDialog(requireContext(), getDetachedModalThemeOverlayResId())
        setUpModalSheet(
            detachedSideSheetDialog,
            R.layout.sidesheet_content,
            R.id.m3_side_sheet,
            R.id.side_sheet_title_text,
            R.string.cat_sidesheet_modal_detached_title
        )
        val showDetachedModalSideSheetButton =
            view.findViewById<View>(R.id.show_modal_detached_side_sheet_button)
        showDetachedModalSideSheetButton.setOnClickListener { v: View? -> detachedSideSheetDialog.show() }
        detachedSideSheetDialog
            .behavior
            .addCallback(
                createSideSheetCallback(
                    detachedSideSheetDialog.findViewById<TextView>(R.id.side_sheet_state_text)!!,
                    detachedSideSheetDialog.findViewById<TextView>(R.id.side_sheet_slide_offset_text)!!
                )
            )
        val detachedModalSideSheetCloseIconButton =
            detachedSideSheetDialog.findViewById<View>(R.id.close_icon_button)
        detachedModalSideSheetCloseIconButton?.setOnClickListener { v: View? -> detachedSideSheetDialog.hide() }

        // Set up coplanar side sheet.
        val coplanarSideSheet = setUpSideSheet(
            view,
            R.id.coplanar_side_sheet_container,
            R.id.show_coplanar_side_sheet_button,
            R.id.coplanar_side_sheet_close_icon_button
        )
        setSideSheetCallback(
            coplanarSideSheet,
            R.id.coplanar_side_sheet_state_text,
            R.id.coplanar_side_sheet_slide_offset_text
        )

        // Set up detached coplanar side sheet. coplanar : 동일평면
        val detachedCoplanarSideSheet = setUpSideSheet(
            view,
            R.id.coplanar_detached_side_sheet_container,
            R.id.show_coplanar_detached_side_sheet_button,
            R.id.coplanar_detached_side_sheet_close_icon_button
        )
        setSideSheetCallback(
            detachedCoplanarSideSheet,
            R.id.coplanar_detached_side_sheet_state_text,
            R.id.coplanar_detached_side_sheet_slide_offset_text
        )
        return view
    }

    private fun setUpSideSheet(
        view: View,
        @IdRes sideSheetContainerId: Int,
        @IdRes showSideSheetButtonId: Int,
        @IdRes closeIconButtonId: Int
    ): View {
        val sideSheet = view.findViewById<View>(sideSheetContainerId)
        val sideSheetBehavior = SideSheetBehavior.from(sideSheet)
        val showSideSheetButton = view.findViewById<Button>(showSideSheetButtonId)
        showSideSheetButton.setOnClickListener { unusedView: View? ->
            showSideSheet(
                sideSheetBehavior
            )
        }
        val standardSideSheetCloseIconButton = sideSheet.findViewById<View>(closeIconButtonId)
        standardSideSheetCloseIconButton.setOnClickListener { v: View? ->
            hideSideSheet(
                sideSheetBehavior
            )
        }
        return sideSheet
    }

    private fun setSideSheetCallback(sideSheet: View, stateTextViewId: Int, slideOffsetTextId: Int) {
        val sideSheetBehavior = SideSheetBehavior.from(sideSheet)
        sideSheetBehavior.addCallback(
            createSideSheetCallback(
                sideSheet.findViewById(stateTextViewId), sideSheet.findViewById(slideOffsetTextId)
            )
        )
    }

    private fun setUpModalSheet(
        sheetDialog: AppCompatDialog,
        @LayoutRes sheetContentLayoutRes: Int,
        @IdRes sheetContentRootIdRes: Int,
        @IdRes sheetTitleIdRes: Int,
        @StringRes sheetTitleStringRes: Int
    ) {
        sheetDialog.setContentView(sheetContentLayoutRes)
        val modalSheetContent = sheetDialog.findViewById<View>(sheetContentRootIdRes)
        if (modalSheetContent != null) {
            val modalSideSheetTitle = modalSheetContent.findViewById<TextView>(sheetTitleIdRes)
            modalSideSheetTitle.setText(sheetTitleStringRes)
        }
    }

    //툴바 커스텀으로.
    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }

    private fun setUpToolbar(view: View) {
        val toolbar = ViewCompat.requireViewById<Toolbar>(view, R.id.toolbar)
        val activity = requireActivity() as AppCompatActivity?
        if (activity != null) {
            toolbar.setNavigationOnClickListener { v: View? ->
                activity.onBackPressed()
            }
        }
    }

    private fun showSideSheet(sheetBehavior: SideSheetBehavior<View>) {
        sheetBehavior.expand()
    }

    private fun hideSideSheet(sheetBehavior: SideSheetBehavior<View>) {
        sheetBehavior.hide()
    }

    @StyleRes
    private fun getDetachedModalThemeOverlayResId(): Int {
        return R.style.ThemeOverlay_Catalog_SideSheet_Modal_Detached
    }


    private fun createSideSheetCallback(stateTextView: TextView, slideOffsetTextView: TextView): SideSheetCallback {
        return object : SideSheetCallback() {
            override fun onStateChanged(sheet: View, newState: Int) {
                when (newState) {
                    SideSheetBehavior.STATE_DRAGGING -> stateTextView.setText(R.string.cat_sidesheet_state_dragging)
                    SideSheetBehavior.STATE_EXPANDED -> stateTextView.setText(R.string.cat_sidesheet_state_expanded)
                    SideSheetBehavior.STATE_SETTLING -> stateTextView.setText(R.string.cat_sidesheet_state_settling)
                    SideSheetBehavior.STATE_HIDDEN -> {}
                    else -> {}
                }
            }

            override fun onSlide(sheet: View, slideOffset: Float) {
                slideOffsetTextView.text =
                    resources.getString(R.string.cat_sidesheet_slide_offset_text, slideOffset)
            }
        }
    }
}
