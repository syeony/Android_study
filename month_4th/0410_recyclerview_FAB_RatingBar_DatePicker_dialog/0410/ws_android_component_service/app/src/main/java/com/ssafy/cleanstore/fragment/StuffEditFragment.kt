package com.ssafy.cleanstore.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.cleanstore.MainActivity
import com.ssafy.cleanstore.databinding.FragmentStuffEditBinding
import com.ssafy.cleanstore.dto.Stuff

class StuffEditFragment : Fragment() {

    private var _binding: FragmentStuffEditBinding? = null
    private val binding get() = _binding!!

    private var actionFlag = -1
    private var position = -1
    private lateinit var stuff: Stuff

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var selectedDate: String = ""
    private var selectedRate: Int = -1

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

        // BottomSheetBehavior 초기화
        bottomSheetBehavior = BottomSheetBehavior.from(binding.persistBottomsheetDrawer)
        bottomSheetBehavior.peekHeight = 200

        // 날짜 선택 이벤트 처리
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d/%02d/%02d", year % 100, month + 1, dayOfMonth)
            binding.day.text = selectedDate
        }

        // 확인 버튼 클릭 시 BottomSheet 내리기
        binding.btnConfirmDate.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        if (actionFlag == StuffFragment.REGISTER) {
            binding.btnDelete.visibility = View.GONE
        } else {
            binding.btnDelete.visibility = View.VISIBLE
            binding.name.setText(stuff.name)
            binding.count.setText(stuff.count.toString())

            // regDate 셋팅 (예: "24/04/21")
            selectedDate = stuff.regDate
            binding.day.text = selectedDate

            // 추가: ratingBar 값 설정
            binding.ratingBar.rating = stuff.rating.toFloat()

            // CalendarView도 해당 날짜로 이동
            try {
                val parts = stuff.regDate.split("/")
                if (parts.size == 3) {
                    val year = parts[0].toInt() + 2000
                    val month = parts[1].toInt() - 1
                    val day = parts[2].toInt()

                    val calendar = java.util.Calendar.getInstance().apply {
                        set(year, month, day)
                    }
                    binding.calendarView.date = calendar.timeInMillis
                }
            } catch (e: Exception) {
                Log.e("StuffEditFragment", "날짜 변환 오류: ${e.message}")
            }
        }

        binding.btnCancel.setOnClickListener {
            myActivity.changeFragmentView(MainActivity.STUFF_FRAGMENT)
        }

        binding.btnSave.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val countText = binding.count.text.toString().trim()
            val day = binding.day.text.toString().trim()
            val rating = binding.ratingBar.rating.toInt() // 추가: 별점 읽기

            if (name.isNotEmpty() && countText.isNotEmpty() && countText.isDigitsOnly() && day.isNotEmpty()) {
                val count = countText.toInt()
                val item = Stuff(-1,name, count, selectedDate, rating) //아이디는 어떻게?

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