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
import com.ssafy.cleanstore.StuffMgr
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStuffBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, StuffMgr.getList())
        binding.listView.adapter = adapter

        binding.register.setOnClickListener {
            myActivity.changeFragmentView(
                MainActivity.STUFF_EDIT_FRAGMENT,
                MainActivity.DEFAULT_STUFF, -1, REGISTER)
        }

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            val item = StuffMgr.search(position)
            myActivity.changeFragmentView(MainActivity.STUFF_EDIT_FRAGMENT, item, position, UPDATE)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
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