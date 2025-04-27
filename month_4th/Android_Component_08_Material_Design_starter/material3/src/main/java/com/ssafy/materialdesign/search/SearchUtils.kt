package com.ssafy.materialdesign.search

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.R


internal object SearchUtils {
    //메뉴(cast, 삼점)붙은 검색창 만들기.
    fun setUpSearchBar(activity: Activity, searchBar: SearchBar) {
        searchBar.inflateMenu(R.menu.searchbar_menu)
        searchBar.setOnMenuItemClickListener { menuItem: MenuItem ->
            showSnackbar(activity, menuItem)
            true
        }
    }

    //검색어 입력하는 searchview 구성 및 이벤트 처리
    @SuppressLint("NewApi")
    fun setUpSearchView(activity: Activity, searchBar: SearchBar, searchView: SearchView) {
        //검색창 열릴때 오른쪽 끝 마이크 이미지.
        searchView.inflateMenu(R.menu.searchview_menu)
        searchView.setOnMenuItemClickListener { menuItem: MenuItem ->
            showSnackbar(activity, menuItem)
            true
        }
        //검색창에 엔터(돋보기) 눌렀을때
        searchView.editText.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            //text입력하고 열린 검색뷰 닫기
            submitSearchQuery(searchBar, searchView, searchView.text.toString())
            false
        }
    }

    private fun showSnackbar(activity: Activity, menuItem: MenuItem) {
        menuItem.title?.let {
            Snackbar.make(activity.findViewById(android.R.id.content),
                it, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun startOnLoadAnimation(searchBar: SearchBar, bundle: Bundle?) {
        // Don't start animation on rotation. Only needed in demo because minIntervalSeconds is 0.
        if (bundle == null) {
            searchBar.startOnLoadAnimation()
        }
    }
    //자동완성 창 구성.
    fun setUpSuggestions(suggestionContainer: ViewGroup, searchBar: SearchBar, searchView: SearchView) {
        //자동완성 타이틀
        addSuggestionTitleView(suggestionContainer, R.string.cat_searchview_suggestion_section_title_yesterday)
        //자동완성 view
        addSuggestionItemViews(suggestionContainer, yesterdaySuggestions, searchBar, searchView)
        addSuggestionTitleView(suggestionContainer, R.string.cat_searchview_suggestion_section_title_this_week)
        addSuggestionItemViews(suggestionContainer, thisWeekSuggestions, searchBar, searchView)
    }

    private fun addSuggestionTitleView(parent: ViewGroup, @StringRes titleResId: Int) {
        val titleView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cat_search_suggestion_title, parent, false) as TextView
        titleView.setText(titleResId)
        parent.addView(titleView)
    }

    private fun addSuggestionItemViews(
        parent: ViewGroup,
        suggestionItems: List<SuggestionItem>,
        searchBar: SearchBar,
        searchView: SearchView
    ) {
        for (suggestionItem in suggestionItems) {
            addSuggestionItemView(parent, suggestionItem, searchBar, searchView)
        }
    }

    private fun addSuggestionItemView(
        parent: ViewGroup,
        suggestionItem: SuggestionItem,
        searchBar: SearchBar,
        searchView: SearchView
    ) {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_search_suggestion_item, parent, false)
        val iconView = view.findViewById<ImageView>(R.id.cat_searchbar_suggestion_icon)
        val titleView = view.findViewById<TextView>(R.id.cat_searchbar_suggestion_title)
        val subtitleView = view.findViewById<TextView>(R.id.cat_searchbar_suggestion_subtitle)
        iconView.setImageResource(suggestionItem.iconResId)
        titleView.text = suggestionItem.title
        subtitleView.text = suggestionItem.subtitle
        view.setOnClickListener { v: View? ->
            submitSearchQuery(
                searchBar,
                searchView,
                suggestionItem.title
            )
        }
        parent.addView(view)
    }

    private val yesterdaySuggestions: List<SuggestionItem>
        get() {
            val suggestionItems: MutableList<SuggestionItem> = java.util.ArrayList()
            suggestionItems.add(
                SuggestionItem(
                    R.drawable.ic_schedule_vd_theme_24, "481 Van Brunt Street", "Brooklyn, NY"
                )
            )
            suggestionItems.add(
                SuggestionItem(
                    R.drawable.ic_home_vd_theme_24, "Home", "199 Pacific Street, Brooklyn, NY"
                )
            )
            return suggestionItems
        }
    private val thisWeekSuggestions: List<SuggestionItem>
        get() {
            val suggestionItems: MutableList<SuggestionItem> = java.util.ArrayList()
            suggestionItems.add(
                SuggestionItem(
                    R.drawable.ic_schedule_vd_theme_24,
                    "BEP GA",
                    "Forsyth Street, New York, NY"
                )
            )
            suggestionItems.add(
                SuggestionItem(
                    R.drawable.ic_schedule_vd_theme_24,
                    "Sushi Nakazawa",
                    "Commerce Street, New York, NY"
                )
            )
            suggestionItems.add(
                SuggestionItem(
                    R.drawable.ic_schedule_vd_theme_24,
                    "IFC Center",
                    "6th Avenue, New York, NY"
                )
            )
            return suggestionItems
        }

    private fun submitSearchQuery(searchBar: SearchBar, searchView: SearchView, query: String) {
        searchBar.setText(query)
        searchView.hide()
    }

    private class SuggestionItem constructor(
        @field:DrawableRes val iconResId: Int,
        val title: String,
        val subtitle: String
    )
}
