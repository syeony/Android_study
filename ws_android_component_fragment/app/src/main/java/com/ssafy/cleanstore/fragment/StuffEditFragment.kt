package com.ssafy.cleanstore.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ssafy.cleanstore.MainActivity
import com.ssafy.cleanstore.StuffMgr
import com.ssafy.cleanstore.databinding.FragmentStuffEditBinding
import com.ssafy.cleanstore.dto.Stuff

class StuffEditFragment : Fragment() {

    private lateinit var myActivity: MainActivity
    private var _binding: FragmentStuffEditBinding? = null
    private val binding get() = _binding!!

    private var actionFlag = -1
    private var position = -1
    private lateinit var stuff: Stuff

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            stuff = it.getSerializable("stuff") as Stuff
            position = it.getInt("position")
            actionFlag = it.getInt("actionFlag")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStuffEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (actionFlag == StuffFragment.REGISTER) {
            binding.btnDelete.visibility = View.GONE
        } else {
            binding.btnDelete.visibility = View.VISIBLE
            binding.name.setText(stuff.name)
            binding.count.setText(stuff.count.toString())
        }

        binding.btnCancel.setOnClickListener {
            myActivity.changeFragmentView(MainActivity.STUFF_FRAGMENT)
        }

        binding.btnSave.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val countText = binding.count.text.toString().trim()

            if (name.isNotEmpty() && countText.isNotEmpty()) {
                val count = countText.toInt()
                val item = Stuff(name, count)

                if (actionFlag == StuffFragment.REGISTER) {
                    StuffMgr.add(item)
                    Toast.makeText(requireContext(), "물품이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    if (position != -1) {
                        StuffMgr.update(position, item)
                        Toast.makeText(requireContext(), "물품이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                myActivity.changeFragmentView(MainActivity.STUFF_FRAGMENT)
            } else {
                Toast.makeText(requireContext(), "모든 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            if (position != -1) {
                StuffMgr.remove(position)
                Toast.makeText(requireContext(), "해당 물품이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                myActivity.changeFragmentView(MainActivity.STUFF_FRAGMENT)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(stuff: Stuff, position: Int, actionFlag: Int): StuffEditFragment {
            val fragment = StuffEditFragment()
            val args = Bundle()
            args.putSerializable("stuff", stuff)
            args.putInt("position", position)
            args.putInt("actionFlag", actionFlag)
            fragment.arguments = args
            return fragment
        }
    }
}