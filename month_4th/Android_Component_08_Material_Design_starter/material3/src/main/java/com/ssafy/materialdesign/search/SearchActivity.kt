package com.ssafy.materialdesign.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.ssafy.materialdesign.BaseActivity
import com.ssafy.materialdesign.R

class SearchActivity : BaseActivity() {

    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView
    private lateinit var suggestionContainer: LinearLayout

    override fun onCreateActivityView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.activity_search, viewGroup, false)
        searchBar = view.findViewById<SearchBar>(R.id.cat_search_bar)
        searchView = view.findViewById<SearchView>(R.id.cat_search_view) // 검색어 입력
        suggestionContainer = view.findViewById<LinearLayout>(R.id.cat_search_view_suggestion_container) //검색결과보여질 창
        //메뉴(cast, 삼점)붙은 검색창 만들기.
        SearchUtils.setUpSearchBar(this, searchBar)
        //검색어 입력하는 searchview 구성 및 이벤트 처리
        SearchUtils.setUpSearchView(this, searchBar, searchView)
        //자동완성 창 구성
        SearchUtils.setUpSuggestions(suggestionContainer, searchBar, searchView)
        SearchUtils.startOnLoadAnimation(searchBar, bundle)
        return view
    }

    override fun onBackPressed() {
        if (searchView.isShowing) {
            searchView.hide()
            return
        }
        super.onBackPressed()
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }
}
