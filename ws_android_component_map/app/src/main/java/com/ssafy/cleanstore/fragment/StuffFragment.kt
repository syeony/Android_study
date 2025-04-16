package com.ssafy.cleanstore.fragment

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.ssafy.cleanstore.MainActivity
import com.ssafy.cleanstore.databinding.FragmentStuffBinding
import com.ssafy.cleanstore.dto.Stuff

class StuffFragment : Fragment() {

    private lateinit var myActivity: MainActivity
    private var _binding: FragmentStuffBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<Stuff>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as MainActivity
    }

    fun refreshList(newList: List<Stuff>) {
        adapter.clear()
        adapter.addAll(newList)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStuffBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //myActivity(=MainActivity)에 있는 getBoundService를 service라는 변수로  가져오자
        val service = myActivity.getBoundService()

        adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, service?.getList() ?: emptyList())
        binding.listView.adapter = adapter

        binding.register.setOnClickListener {
            myActivity.changeFragmentView(
                MainActivity.STUFF_EDIT_FRAGMENT,
                MainActivity.DEFAULT_STUFF, -1, REGISTER)
        }

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            val item = service?.getList()?.get(position)
            item?.let {
                myActivity.changeFragmentView(MainActivity.STUFF_EDIT_FRAGMENT, it, position, UPDATE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val service = myActivity.getBoundService()
        val updatedList = service?.getList() ?: emptyList()
        refreshList(updatedList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(stuff: Stuff, position: Int, actionFlag: Int): StuffFragment {
            return StuffFragment()
        }

        const val REGISTER = 1
        const val UPDATE = 2
    }
}

//class StuffFragment : Fragment() {
//
//    private lateinit var myActivity: MainActivity
//    private var _binding: FragmentStuffBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var adapter: ArrayAdapter<Stuff>
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        myActivity = context as MainActivity
//    }
//
//    fun refreshList(newList: List<Stuff>) {
//        adapter.clear()
//        adapter.addAll(newList)
//        adapter.notifyDataSetChanged()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentStuffBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, StuffMgr.getList())
//        binding.listView.adapter = adapter
//
//        binding.register.setOnClickListener {
//            myActivity.changeFragmentView(
//                MainActivity.STUFF_EDIT_FRAGMENT,
//                MainActivity.DEFAULT_STUFF, -1, REGISTER)
//        }
//
//        binding.listView.setOnItemClickListener { _, _, position, _ ->
//            val item = StuffMgr.search(position)
//            myActivity.changeFragmentView(MainActivity.STUFF_EDIT_FRAGMENT, item, position, UPDATE)
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        adapter.notifyDataSetChanged()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object {
//        fun newInstance(stuff: Stuff, position: Int, actionFlag: Int): StuffFragment {
//            return StuffFragment()
//        }
//
//        const val REGISTER = 1
//        const val UPDATE = 2
//    }
//}