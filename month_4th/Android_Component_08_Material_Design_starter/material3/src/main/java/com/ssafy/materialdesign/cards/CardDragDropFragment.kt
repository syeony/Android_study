package com.ssafy.materialdesign.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate
import com.google.android.material.card.MaterialCardView
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import java.util.Locale

class CardDragDropFragment : BaseFragment() {

    private val CARD_COUNT = 30

    fun getDemoTitleResId(): Int {
        return R.string.cat_card_list
    }

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view: View = layoutInflater.inflate(
            R.layout.fragment_card_dragdrop, viewGroup,  /* attachToRoot= */false
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.cat_card_list_recycler_view)
        val cardAdapter = CardAdapter(generateCardNumbers())
        val itemTouchHelper = ItemTouchHelper(CardItemTouchHelperCallback(cardAdapter))
        // Provide an ItemTouchHelper to the Adapter; can't use constructor due to circular dependency.
        cardAdapter.setItemTouchHelper(itemTouchHelper)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = cardAdapter
        recyclerView
            .setAccessibilityDelegateCompat(object :
                RecyclerViewAccessibilityDelegate(recyclerView) {
                override fun getItemDelegate(): AccessibilityDelegateCompat {
                    return object : ItemDelegate(this) {
                        override fun onInitializeAccessibilityNodeInfo(
                            host: View,
                            info: AccessibilityNodeInfoCompat
                        ) {
                            super.onInitializeAccessibilityNodeInfo(host, info)
                            val position = recyclerView.getChildLayoutPosition(host)
                            if (position != 0) {
                                info.addAction(
                                    AccessibilityActionCompat(
                                        R.id.move_card_up_action,
                                        host.resources.getString(R.string.cat_card_action_move_up)
                                    )
                                )
                            }
                            if (position != (CARD_COUNT - 1)) {
                                info.addAction(
                                    AccessibilityActionCompat(
                                        R.id.move_card_down_action,
                                        host.resources.getString(R.string.cat_card_action_move_down)
                                    )
                                )
                            }
                        }

                        override fun performAccessibilityAction(
                            host: View,
                            action: Int,
                            args: Bundle?
                        ): Boolean {
                            val fromPosition = recyclerView.getChildLayoutPosition(host)
                            if (action == R.id.move_card_down_action) {
                                swapCards(fromPosition, fromPosition + 1, cardAdapter)
                                return true
                            } else if (action == R.id.move_card_up_action) {
                                swapCards(fromPosition, fromPosition - 1, cardAdapter)
                                return true
                            }
                            return super.performAccessibilityAction(host, action, args)
                        }
                    }
                }
            })
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return view
    }

    private fun generateCardNumbers(): IntArray {
        val cardNumbers = IntArray(CARD_COUNT)
        for (i in 0 until CARD_COUNT) {
            cardNumbers[i] = i + 1
        }
        return cardNumbers
    }

    class CardAdapter (val cardNumbers: IntArray) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var itemTouchHelper: ItemTouchHelper? = null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view: View =
                inflater.inflate(R.layout.card_list_item, parent,  /* attachToRoot= */false)
            return CardViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            (viewHolder as CardViewHolder).bind(cardNumbers[position], itemTouchHelper)
        }

        override fun getItemCount(): Int {
            return cardNumbers.size
        }

        fun setItemTouchHelper(itemTouchHelper: ItemTouchHelper) {
            this.itemTouchHelper = itemTouchHelper
        }

        private class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val titleView: TextView
            private val dragHandleView: View

            init {
                titleView = itemView.findViewById<TextView>(R.id.cat_card_list_item_title)
                dragHandleView = itemView.findViewById<View>(R.id.cat_card_list_item_drag_handle)
            }

            fun bind(cardNumber: Int, itemTouchHelper: ItemTouchHelper?) {
                titleView.text =
                    String.format(Locale.getDefault(), "Card #%02d", cardNumber)
                dragHandleView.setOnTouchListener { v: View?, event: MotionEvent ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        itemTouchHelper!!.startDrag(this@CardViewHolder)
                        return@setOnTouchListener true
                    }
                    false
                }
            }
        }
    }

    inner class CardItemTouchHelperCallback (private val cardAdapter: CardAdapter) : ItemTouchHelper.Callback() {
        private var dragCardView: MaterialCardView? = null
        override fun getMovementFlags(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(DRAG_FLAGS, SWIPE_FLAGS)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            swapCards(fromPosition, toPosition, cardAdapter)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
                dragCardView = viewHolder.itemView as MaterialCardView
                dragCardView!!.isDragged = true
            } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE && dragCardView != null) {
                dragCardView!!.isDragged = false
                dragCardView = null
            }
        }
    }

    companion object {
        const val DRAG_FLAGS = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        const val SWIPE_FLAGS = 0
    }

    private fun swapCards(fromPosition: Int, toPosition: Int, cardAdapter: CardAdapter) {
        val fromNumber = cardAdapter.cardNumbers[fromPosition]
        cardAdapter.cardNumbers[fromPosition] = cardAdapter.cardNumbers[toPosition]
        cardAdapter.cardNumbers[toPosition] = fromNumber
        cardAdapter.notifyItemMoved(fromPosition, toPosition)
    }
}
