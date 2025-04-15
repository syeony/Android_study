package com.ssafy.cleanstore.fragment

import android.Manifest
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.cleanstore.MainActivity
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.adapter.ContactAdapter
import com.ssafy.cleanstore.databinding.FragmentStoreBinding

private const val TAG = "StoreFragment_싸피"
class StoreFragment : Fragment() {

    /** permission check **/
    private val checker = com.ssafy.cleanstore.util.PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.READ_CONTACTS)
    /** permission check **/

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var myActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)

        // 버튼 클릭 시 StuffFragment로 이동
        binding.ssafybucks.setOnClickListener {
            myActivity.changeFragmentView(
                MainActivity.STUFF_FRAGMENT,
                MainActivity.DEFAULT_STUFF,
                -1,
                0 // 예: actionFlag = 0 (등록 모드)
            )
        }

        binding.seeTel.setOnClickListener {
            checkPermission()
        }

        return binding.root
    }

    private fun checkPermission(){

        /** permission check **/
        if (!checker.checkPermission(requireContext(), runtimePermissions)) {
            checker.setOnGrantedListener{ //퍼미션 획득 성공일때
                init()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            init()
        }
        /** permission check **/
    }

    val URI = ContactsContract.Contacts.CONTENT_URI // 전체 주소록
    private fun init() {
        val cursor = requireContext().contentResolver.query(URI, null, null, null, null)
        if (cursor != null) {
            val adapter = ContactAdapter(cursor)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}