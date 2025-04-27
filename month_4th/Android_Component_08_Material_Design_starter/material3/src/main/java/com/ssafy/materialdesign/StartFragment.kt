package com.ssafy.materialdesign

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.ssafy.materialdesign.adaptive.AdaptiveFeedActivity
import com.ssafy.materialdesign.adaptive.AdaptiveHeroActivity
import com.ssafy.materialdesign.adaptive.AdaptiveListViewActivity
import com.ssafy.materialdesign.adaptive.AdaptiveSupportingPanelActivity
import com.ssafy.materialdesign.bottomappbar.BottomAppBarFragment
import com.ssafy.materialdesign.bottomappbar.M2StyleBottomAppBarFragment
import com.ssafy.materialdesign.bottomsheet.BottomSheetFragment
import com.ssafy.materialdesign.bottomsheet.BottomSheetScrollableFragment
import com.ssafy.materialdesign.button.Buttons2Fragment
import com.ssafy.materialdesign.button.ButtonsFragment
import com.ssafy.materialdesign.cards.CardDragDropFragment
import com.ssafy.materialdesign.cards.CardFragment
import com.ssafy.materialdesign.cards.CardRichMediaFragment
import com.ssafy.materialdesign.cards.CardSelectionActivity
import com.ssafy.materialdesign.cards.CardStatesFragment
import com.ssafy.materialdesign.cards.CardSwipeDismissFragment
import com.ssafy.materialdesign.checkbox.CheckBoxFragment
import com.ssafy.materialdesign.chips.ChipsFragment
import com.ssafy.materialdesign.chips.ChipGroupFragment
import com.ssafy.materialdesign.databinding.FragmentStartBinding
import com.ssafy.materialdesign.datepicker.DatePickerFragment
import com.ssafy.materialdesign.dialogs.DialogFragment
import com.ssafy.materialdesign.fab.FabFragment
import com.ssafy.materialdesign.fab.FabFragmentCollapsing
import com.ssafy.materialdesign.fab.FabFragmentShrink
import com.ssafy.materialdesign.imageview.ImageViewFragment
import com.ssafy.materialdesign.menu.MenuFragment
import com.ssafy.materialdesign.navigation_bar.NavigationBarFragment
import com.ssafy.materialdesign.navigation_drawer.NavigationDrawerActivity
import com.ssafy.materialdesign.navigation_rail.NavigationRailFragment
import com.ssafy.materialdesign.progressindicator.ProgressIndicatorDeterminateFragment
import com.ssafy.materialdesign.progressindicator.ProgressIndicatorFragment
import com.ssafy.materialdesign.progressindicator.ProgressIndicatorIndeterminateFragment
import com.ssafy.materialdesign.progressindicator.ProgressIndicatorStandaloneFragment
import com.ssafy.materialdesign.radiobutton.RadioButtonFragment
import com.ssafy.materialdesign.search.SearchActivity
import com.ssafy.materialdesign.shapetheming.ShapeThemingBaseFragment
import com.ssafy.materialdesign.sidesheet.SideSheetFragment
import com.ssafy.materialdesign.slider.SliderContinuousFragment
import com.ssafy.materialdesign.slider.SliderDiscreteFragment
import com.ssafy.materialdesign.slider.SliderFragment
import com.ssafy.materialdesign.slider.SliderLabelFragment
import com.ssafy.materialdesign.switch.SwitchFragment
import com.ssafy.materialdesign.tab.TabsControllableFragment
import com.ssafy.materialdesign.tab.TabsFragment
import com.ssafy.materialdesign.tab.TabsScrollableFragment
import com.ssafy.materialdesign.textfield.TextFieldDropdownFragment
import com.ssafy.materialdesign.textfield.TextFieldFilledFragment
import com.ssafy.materialdesign.textfield.TextFieldFilledIconsFragment
import com.ssafy.materialdesign.textfield.TextFieldLegacyFragment
import com.ssafy.materialdesign.textfield.TextFieldMainFragment
import com.ssafy.materialdesign.textfield.TextFieldOutlinedFragment
import com.ssafy.materialdesign.textfield.TextFieldOutlinedIconsFragment
import com.ssafy.materialdesign.textfield.TextFieldPrefixSuffixFragment
import com.ssafy.materialdesign.timepicker.TimePickerFragment
import com.ssafy.materialdesign.topappbar.TopAppBarCollapsingTitleFragment
import com.ssafy.materialdesign.topappbar.TopAppBarCompressEffectFragment
import com.ssafy.materialdesign.topappbar.TopAppBarFragment
import com.ssafy.materialdesign.topappbar.TopAppBarToolbarFragment
import com.ssafy.materialdesign.transition.TransitionContainerTransformStartActivity
import com.ssafy.materialdesign.transition.TransitionContainerTransformFragment
import com.ssafy.materialdesign.transition.TransitionContainerTransformViewFragment
import java.lang.Math.abs

class StartFragment : BaseFragment() {
    private var _binding:FragmentStartBinding? = null
    private val binding get() = _binding!!

    lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        _binding = FragmentStartBinding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ActionBar 보이게
    override fun shouldShowDefaultActionBar(): Boolean {
        return true
    }

    //첫 화면이라 뒤로가기 없음.
    override fun shouldShowDefaultActionBarCloseButton(): Boolean {
        return false
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button01.apply {
            text = "Adaptive. 반응형."
            setOnClickListener{
                mainActivity.startActivity(Intent(requireContext(), AdaptiveListViewActivity::class.java))
            }
        }

        binding.button02.apply {
            text = "Adaptive. Feed 반응형."
            setOnClickListener{
                mainActivity.startActivity(Intent(requireContext(), AdaptiveFeedActivity::class.java))
            }
        }
        binding.button03.apply {
            text = "Adaptive. Hero 반응형."
            setOnClickListener{
                mainActivity.startActivity(Intent(requireContext(), AdaptiveHeroActivity::class.java))
            }
        }
        binding.button04.apply {
            text = "Adaptive. SupportPanel 반응형."
            setOnClickListener{
                mainActivity.startActivity(Intent(requireContext(), AdaptiveSupportingPanelActivity::class.java))
            }
        }

        binding.button1.apply {
            text = "Buttons"
            setOnClickListener{
                mainActivity.changeFragment(ButtonsFragment())
            }
        }
        binding.button2.apply {
            text = "Buttons 2"
            setOnClickListener{
                mainActivity.changeFragment(Buttons2Fragment())
            }
        }
        binding.button3.apply {
            text = "Bottom App Bar"
            setOnClickListener{
                mainActivity.changeFragment(BottomAppBarFragment())
            }
        }
        binding.button3b.apply {
            text = "M2 Style Bottom App Bar"
            setOnClickListener{
                mainActivity.changeFragment(M2StyleBottomAppBarFragment())
            }
        }
        binding.button4.apply {
            text = "Bottom Sheet"
            setOnClickListener{
                mainActivity.changeFragment(BottomSheetFragment())
            }
        }
        binding.button5.apply {
            text = "Bottom Sheet_scrollable"
            setOnClickListener{
                mainActivity.changeFragment(BottomSheetScrollableFragment())
            }
        }
        binding.button6.apply {
            text = "Card"
            setOnClickListener{
                mainActivity.changeFragment(CardFragment())
            }
        }
        binding.button7.apply {
            text = "Card Selection"
            setOnClickListener{
                mainActivity.startActivity(Intent(requireContext(), CardSelectionActivity::class.java))
            }
        }
        binding.button8.apply {
            text = "Card States"
            setOnClickListener{
                mainActivity.changeFragment(CardStatesFragment())
            }
        }
        binding.button9.apply {
            text = "Card Rich Media"
            setOnClickListener{
                mainActivity.changeFragment(CardRichMediaFragment())
            }
        }
        binding.button10.apply {
            text = "Card Drag and Drop"
            setOnClickListener{
                mainActivity.changeFragment(CardDragDropFragment())
            }
        }
        binding.button11.apply {
            text = "Card Swipe Dismiss"
            setOnClickListener{
                mainActivity.changeFragment(CardSwipeDismissFragment())
            }
        }
        binding.button11a.apply {
            text = "CheckBox"
            setOnClickListener{
                mainActivity.changeFragment(CheckBoxFragment())
            }
        }
        binding.button12.apply {
            text = "Chips"
            setOnClickListener{
                mainActivity.changeFragment(ChipsFragment())
            }
        }
        binding.button12.apply {
            text = "Chip Group"
            setOnClickListener{
                mainActivity.changeFragment(ChipGroupFragment())
            }
        }
        binding.button121.apply {
            text = "Date Picker"
            setOnClickListener{
                mainActivity.changeFragment(DatePickerFragment())
            }
        }
        binding.button13.apply {
            text = "Dialogs"
            setOnClickListener{
                mainActivity.changeFragment(DialogFragment())
            }
        }
        binding.button14.apply {
            text = "Floating Action Button"
            setOnClickListener{
                mainActivity.changeFragment(FabFragment())
            }
        }
        binding.button15.apply {
            text = "Floating Action Button Shrink"
            setOnClickListener{
                mainActivity.changeFragment(FabFragmentShrink())
            }
        }
        binding.button15a.apply {
            text = "Floating Action Button Collapsing"
            setOnClickListener{
                mainActivity.changeFragment(FabFragmentCollapsing())
            }
        }
        binding.button16.apply {
            text = "ImageView Fragment"
            setOnClickListener{
                mainActivity.changeFragment(ImageViewFragment())
            }
        }
        binding.button17.apply {
            text = "Menu Fragment"
            setOnClickListener{
                mainActivity.changeFragment(MenuFragment())
            }
        }
        binding.button18.apply {
            text = "Navigation Bar(Bottom Navigation)"
            setOnClickListener{
                mainActivity.changeFragment(NavigationBarFragment())
            }
        }
        binding.button19.apply {
            text = "Navigation Drawer"
            setOnClickListener{
                mainActivity.startActivity(Intent(requireContext(), NavigationDrawerActivity::class.java))
            }
        }
        binding.button20.apply {
            text = "Navigation Rail"
            setOnClickListener{
                mainActivity.changeFragment(NavigationRailFragment())
            }
        }
        binding.button21.apply {
            text = "Progress Indicator"
            setOnClickListener{
                mainActivity.changeFragment(ProgressIndicatorFragment())
            }
        }
        binding.button21a.apply {
            text = "Progress Indicator Indeterminated"
            setOnClickListener{
                mainActivity.changeFragment(ProgressIndicatorIndeterminateFragment())
            }
        }
        binding.button22.apply {
            text = "Progress Indicator Determinated"
            setOnClickListener{
                mainActivity.changeFragment(ProgressIndicatorDeterminateFragment())
            }
        }
        binding.button23.apply {
            text = "Progress Indicator Standalone"
            setOnClickListener{
                mainActivity.changeFragment(ProgressIndicatorStandaloneFragment())
            }
        }
        binding.button24.apply {
            text = "RadioButton"
            setOnClickListener{
                mainActivity.changeFragment(RadioButtonFragment())
            }
        }
        binding.button25.apply {
            text = "SearchActivity"
            setOnClickListener{
                mainActivity.startActivity(Intent(requireContext(), SearchActivity::class.java))
            }
        }
        binding.button26.apply {
            text = "Shape Theming"
            setOnClickListener{
                mainActivity.changeFragment(ShapeThemingBaseFragment())
            }
        }
        binding.button27.apply {
            text = "Side Sheet"
            setOnClickListener{
                mainActivity.changeFragment(SideSheetFragment())
            }
        }
        binding.button28.apply {
            text = "Slider"
            setOnClickListener{
                mainActivity.changeFragment(SliderFragment())
            }
        }
        binding.button28a.apply {
            text = "Slider Continuous"
            setOnClickListener{
                mainActivity.changeFragment(SliderContinuousFragment())
            }
        }
        binding.button29.apply {
            text = "Slider Discrete"
            setOnClickListener{
                mainActivity.changeFragment(SliderDiscreteFragment())
            }
        }
        binding.button30.apply {
            text = "Slider with Label"
            setOnClickListener{
                mainActivity.changeFragment(SliderLabelFragment())
            }
        }
        binding.button31.apply {
            text = "Switch"
            setOnClickListener{
                mainActivity.changeFragment(SwitchFragment())
            }
        }
        binding.button32.apply {
            text = "Tabs"
            setOnClickListener{
                mainActivity.changeFragment(TabsFragment())
            }
        }
        binding.button33.apply {
            text = "Tabs Controllable"
            setOnClickListener{
                mainActivity.changeFragment(TabsControllableFragment())
            }
        }
        binding.button34.apply {
            text = "Tabs Scrollable"
            setOnClickListener{
                mainActivity.changeFragment(TabsScrollableFragment())
            }
        }
        binding.button35.apply {
            text = "TextField"
            setOnClickListener{
                mainActivity.changeFragment(TextFieldMainFragment())
            }
        }
        binding.button36.apply {
            text = "TextField Filled"
            setOnClickListener{
                mainActivity.changeFragment(TextFieldFilledFragment())
            }
        }
        binding.button37.apply {
            text = "TextField Outlined"
            setOnClickListener{
                mainActivity.changeFragment(TextFieldOutlinedFragment())
            }
        }
        binding.button38.apply {
            text = "TextField DropDown Menu"
            setOnClickListener{
                mainActivity.changeFragment(TextFieldDropdownFragment())
            }
        }
        binding.button39.apply {
            text = "TextField Filled Icons"
            setOnClickListener{
                mainActivity.changeFragment(TextFieldFilledIconsFragment())
            }
        }
        binding.button40.apply {
            text = "TextField Outlined Icons"
            setOnClickListener{
                mainActivity.changeFragment(TextFieldOutlinedIconsFragment())
            }
        }
        binding.button41.apply {
            text = "TextField Prefix Suffix"
            setOnClickListener{
                mainActivity.changeFragment(TextFieldPrefixSuffixFragment())
            }
        }
        binding.button42.apply {
            text = "TextField Legacy"
            setOnClickListener{
                mainActivity.changeFragment(TextFieldLegacyFragment())
            }
        }
        binding.button43.apply {
            text = "TimePicker"
            setOnClickListener{
                mainActivity.changeFragment(TimePickerFragment())
            }
        }
        binding.button44.apply {
            text = "TopAppBar"
            setOnClickListener{
                mainActivity.changeFragment(TopAppBarFragment())
            }
        }
        binding.button45.apply {
            text = "TopAppBar Compress Effect"
            setOnClickListener{
                mainActivity.changeFragment(TopAppBarCompressEffectFragment())
            }
        }
        binding.button46.apply {
            text = "TopAppBar Collapsing Title"
            setOnClickListener{
                mainActivity.changeFragment(TopAppBarCollapsingTitleFragment())
            }
        }
        binding.button47.apply {
            text = "TopAppBar Toolbar"
            setOnClickListener{
                mainActivity.changeFragment(TopAppBarToolbarFragment())
            }
        }
        binding.button48.apply {
            text = "Transition Container Transform(Activity)"
            setOnClickListener{
                mainActivity.startActivity(Intent(requireContext(), TransitionContainerTransformStartActivity::class.java))
            }
        }
        binding.button49.apply {
            text = "Transition Container Transform(Fragment)"
            setOnClickListener{
                mainActivity.changeFragment(TransitionContainerTransformFragment())
            }
        }
        binding.button50.apply {
            text = "Transition Container Transform(View)"
            setOnClickListener{
                mainActivity.changeFragment(TransitionContainerTransformViewFragment())
            }
        }
    }
}