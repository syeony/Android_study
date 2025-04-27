package com.ssafy.materialdesign.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.databinding.FragmentCardSwipeBinding

class CardSwipeDismissFragment : BaseFragment() {

    private var _binding: FragmentCardSwipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        _binding = FragmentCardSwipeBinding.inflate(layoutInflater, viewGroup, false);
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeDismissBehavior = SwipeDismissBehavior<View>()
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)

        val coordinatorParams = binding.cardContentLayout.layoutParams as CoordinatorLayout.LayoutParams
        coordinatorParams.behavior = swipeDismissBehavior

        swipeDismissBehavior.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View) {
                Snackbar.make(binding.cardContainer, R.string.cat_card_dismissed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.cat_card_undo) { v -> resetCard(binding.cardContentLayout) }.show()
            }

            override fun onDragStateChanged(state: Int) {
                onDragStateChanged(state, binding.cardContentLayout)
            }
        }
    }

    fun onDragStateChanged(state: Int, cardContentLayout: MaterialCardView) {
        when (state) {
            SwipeDismissBehavior.STATE_DRAGGING, SwipeDismissBehavior.STATE_SETTLING -> cardContentLayout.isDragged =
                true

            SwipeDismissBehavior.STATE_IDLE -> cardContentLayout.isDragged = false
            else -> {}
        }
    }

    private fun resetCard(cardContentLayout: MaterialCardView) {
        val params = cardContentLayout
            .layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, 0, 0, 0)
        cardContentLayout.alpha = 1.0f
        cardContentLayout.requestLayout()
    }
}
