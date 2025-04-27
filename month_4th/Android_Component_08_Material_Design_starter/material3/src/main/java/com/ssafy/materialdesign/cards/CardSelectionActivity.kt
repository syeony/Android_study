package com.ssafy.materialdesign.cards

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.materialdesign.BaseActivity
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.databinding.ActivityCardSelectionBinding
import java.lang.String
import kotlin.Boolean
import kotlin.Long
import kotlin.math.log

private const val TAG = "CardSel_싸피"
class CardSelectionActivity : BaseActivity(), ActionMode.Callback {

    private val ITEM_COUNT = 20
    private var actionMode: ActionMode? = null
    private lateinit var adapter: SelectableCardsAdapter
    private lateinit var selectionTracker: SelectionTracker<Long>

    private lateinit var binding: ActivityCardSelectionBinding

    override fun onCreateActivityView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        binding = ActivityCardSelectionBinding.inflate(layoutInflater)

        setUpRecyclerView(binding.recyclerView)
        return binding.root
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return true
    }

    override fun shouldShowDefaultActionBarCloseButton(): Boolean {
        return true
    }


    // recyclerview selection : implementation 'androidx.recyclerview:recyclerview-selection:1.1.0'
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        adapter = SelectableCardsAdapter()
        adapter.setItems(generateItems())
        recyclerView.adapter = adapter
        selectionTracker = SelectionTracker.Builder(
            "card_selection",
            recyclerView,
            SelectableCardsAdapter.KeyProvider(adapter),
            SelectableCardsAdapter.DetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()
        adapter.setSelectionTracker(selectionTracker)
        selectionTracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long?>() {
                override fun onSelectionChanged() {
                    if (selectionTracker.selection.size() > 0) {
                        if (actionMode == null) {
                            actionMode = startSupportActionMode(this@CardSelectionActivity)!!
                        }
                        actionMode?.title = String.valueOf(selectionTracker.selection.size())
                    } else if (actionMode != null) {
                        actionMode?.finish()
                    }

                }
            })
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun generateItems(): List<SelectableCardsAdapter.Item> {
        val titlePrefix = getString(R.string.cat_card_selectable_item_title)
        val items: MutableList<SelectableCardsAdapter.Item> = ArrayList()
        for (i in 0 until ITEM_COUNT) {
            items.add(
                SelectableCardsAdapter.Item(
                    titlePrefix + " " + (i + 1),
                    getString(R.string.cat_card_selectable_content)
                )
            )
        }
        return items
    }


    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
//        supportActionBar!!.hide()
        return false
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menuItem: MenuItem?): Boolean {
        return false
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
//        supportActionBar!!.show()
        selectionTracker.clearSelection()
        this.actionMode = null
    }
}