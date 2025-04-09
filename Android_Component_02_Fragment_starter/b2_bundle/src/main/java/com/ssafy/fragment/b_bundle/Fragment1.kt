package com.ssafy.fragment.b_bundle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val PARAM = "param"
class Fragment1 : Fragment() {

    //타입캐스팅을 여기서함 (왜냐면 일일히 하기 귀찮으니까)
    lateinit var myActivity: FragmentNavigationActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as FragmentNavigationActivity
    }

    private lateinit var value:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        value = arguments?.getString(PARAM) ?:"no result"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().findViewById<TextView>(R.id.text_view1).text = value
        requireView().findViewById<View>(R.id.goto_fragment2).setOnClickListener {
            //위에서 타입캐스팅 해놨으니까 여기선 myActivity만 하면 됨
            myActivity
                .changeFragmentView(FragmentNavigationActivity.SECOND_FRAGMENT,"안녕하세요")


        }
    }


    companion object{
        @JvmStatic
        fun newInstance(param:String) =
            Fragment1().apply{
                arguments = Bundle().apply{
                    putString(PARAM, param)
                }
            }
    }

}