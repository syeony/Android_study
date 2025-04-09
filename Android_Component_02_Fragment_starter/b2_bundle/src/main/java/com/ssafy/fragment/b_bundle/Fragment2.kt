package com.ssafy.fragment.b_bundle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val TAG = "Fragment2_싸피"
private const val PARAM = "param"
class Fragment2 : Fragment() {

    lateinit var myActivity: FragmentNavigationActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as FragmentNavigationActivity
    }

    private lateinit var value:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        value = arguments?.getString(PARAM) ?: "no result"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment2, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.text_view2).text = value
        view.findViewById<View>(R.id.goto_fragment1).setOnClickListener {
            myActivity
                .changeFragmentView(FragmentNavigationActivity.FIRST_FRAGMENT,"2에서 보냅니다~")

        }
    }


    companion object{
        @JvmStatic
        fun newInstance(param:String) =
            Fragment2().apply {
                arguments = Bundle().apply {
                    putString(PARAM, param)
                }
            }
    }
}