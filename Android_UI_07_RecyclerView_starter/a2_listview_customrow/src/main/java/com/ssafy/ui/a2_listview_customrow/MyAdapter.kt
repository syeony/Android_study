package com.ssafy.ui.a2_listview_customrow

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

private const val TAG = "MyAdapter_싸피"
class MyAdapter(context: Context, val resource:Int, val arr:Array<String>) : ArrayAdapter<String>(context,resource,arr) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        Log.d(TAG, "getView: $position $convertView")
        //layoutinflater xml-->java
        val view = if(convertView!=null){ //재사용
            Log.d(TAG, "getView: $position 재사용!!! ")
            convertView
        }else {
            Log.d(TAG, "getView: $position 생성. ")
            val inflater = LayoutInflater.from(parent.context)
            inflater.inflate(resource, parent, false)
        }

        val tv=view.findViewById<TextView>(R.id.text1)
        tv.setText(arr[position])

        return view
    }
}