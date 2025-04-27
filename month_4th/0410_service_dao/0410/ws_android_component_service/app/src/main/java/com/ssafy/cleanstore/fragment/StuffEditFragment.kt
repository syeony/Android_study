package com.ssafy.cleanstore.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.ssafy.cleanstore.MainActivity
import com.ssafy.cleanstore.databinding.FragmentStuffEditBinding
import com.ssafy.cleanstore.dto.Stuff

class StuffEditFragment : Fragment() {

    private var _binding: FragmentStuffEditBinding? = null
    private val binding get() = _binding!!

    private var actionFlag = -1
    private var position = -1
    private lateinit var stuff: Stuff

    private lateinit var myActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get~이니까 stufffragment에서 가져옴
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

            if (name.isNotEmpty() && countText.isNotEmpty() && countText.isDigitsOnly()) {
                val count = countText.toInt()
                val item = Stuff(-1,name, count) //아이디는 어떻게?

                val service = myActivity.getBoundService()

                if (actionFlag == StuffFragment.REGISTER) {
                    service?.add(item)
                    Toast.makeText(requireContext(), "물품이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    if (stuff.id != -1) {
                        service?.update(item.copy(id = stuff.id))
                        Toast.makeText(requireContext(), "물품이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                myActivity.changeFragmentView(MainActivity.STUFF_FRAGMENT)
            } else {
                Toast.makeText(requireContext(), "형식에 맞게 모두 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            if (stuff.id != -1) {
                val service = myActivity.getBoundService()
                service?.remove(stuff.id)
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
        //stuff, position, actionFlag 값을 받아서
        //Bundle에 담고
        //arguments로 fragment에 연결해줍니다.
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