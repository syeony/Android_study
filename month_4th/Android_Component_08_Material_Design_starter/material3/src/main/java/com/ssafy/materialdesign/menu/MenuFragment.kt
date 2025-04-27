package com.ssafy.materialdesign.menu

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.InsetDrawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.util.TypedValue
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.ListPopupWindow
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class MenuFragment : BaseFragment() {

    private val ICON_MARGIN = 8
    private val CLIP_DATA_LABEL = "Sample text to copy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fragment에 options menu 달기.
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.popup_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Snackbar.make(requireView(), "menu selected...${item.title}",Snackbar.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_menu, container, false)
        val button = view.findViewById<Button>(R.id.menu_button)
        val iconMenuButton = view.findViewById<Button>(R.id.menu_button_with_icons)
        button.setOnClickListener { v: View ->
            showMenu(v, R.menu.popup_menu)
        }
        iconMenuButton.setOnClickListener { v: View ->
            showMenu(v, R.menu.menu_with_icons)
        }

        val contextMenuTextView = view.findViewById<TextView>(R.id.context_menu_tv)
        registerForContextMenu(contextMenuTextView)

        val listPopupWindowButton = view.findViewById<Button>(R.id.list_popup_window)
        val listPopupWindow = initializeListPopupMenu(listPopupWindowButton)
        listPopupWindowButton.setOnClickListener { v: View? ->
            listPopupWindow.show()
        }
        return view
    }

    @SuppressLint("RestrictedApi")
    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        // Inflating the Popup using xml file
        popup.menuInflater.inflate(menuRes, popup.menu)
        // There is no public API to make icons show on menus.
        // IF you need the icons to show this works however it's discouraged to rely on library only
        // APIs since they might disappear in future versions.
        if (popup.menu is MenuBuilder) {
            val menuBuilder = popup.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
            for (item in menuBuilder.visibleItems) {
                val iconMarginPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,ICON_MARGIN.toFloat(),resources.displayMetrics).toInt()
                if (item.icon != null) {
                    if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
                        item.icon = InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0)
                    } else {
                        item.icon = object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
                                override fun getIntrinsicWidth(): Int {
                                    return intrinsicHeight + iconMarginPx + iconMarginPx
                                }
                            }
                    }
                }
            }
        }
        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            menuItem.title?.let { Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show() }
            true
        }
        popup.show()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        val contextMenuTextView = v as TextView
        menu.add(android.R.string.copy).setOnMenuItemClickListener { item: MenuItem? ->
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(
                    ClipData.newPlainText(
                        CLIP_DATA_LABEL,
                        contextMenuTextView.text
                    )
                )
                true
            }
        menu.add(R.string.context_menu_highlight).setOnMenuItemClickListener { item: MenuItem? ->
                highlightText(contextMenuTextView)
                true
            }
    }

    private fun initializeListPopupMenu(v: View): ListPopupWindow {
        val listPopupWindow = ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle)
        val adapter = ArrayAdapter<CharSequence>(
            requireContext(),
            R.layout.cat_popup_item,
            resources.getStringArray(R.array.cat_list_popup_window_content)
        )
        listPopupWindow.setAdapter(adapter)
        listPopupWindow.anchorView = v
        listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            Snackbar.make(requireView(), adapter.getItem(position).toString(), Snackbar.LENGTH_LONG).show()
            listPopupWindow.dismiss()
        }
        return listPopupWindow
    }

    private fun highlightText(textView: TextView) {
        val context = textView.context
        val text = textView.text
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, value, true)
        val spanText = Spannable.Factory.getInstance().newSpannable(text)
        spanText.setSpan(
            BackgroundColorSpan(value.data), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spanText
    }
}
