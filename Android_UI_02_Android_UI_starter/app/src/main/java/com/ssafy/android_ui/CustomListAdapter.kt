package com.ssafy.android_ui

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import org.w3c.dom.Text

class CustomListAdapter(context:Context, var items: List<Map<String, String>>, val layout: Int)
    : BaseAdapter(){
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    val inflater = LayoutInflater.from(context)

    //xml을 inflate시켜서 view로 만들고, 값을 assign 한 후 리턴한다.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        if(convertView==null){
            Log.d(TAG, "getView $position: 새로만듦")
            view = inflater.inflate(layout,parent,false)
        }else{
            Log.d(TAG, "getView $position: 재사용함")
            view = convertView
        }

        view.findViewById<TextView>(R.id.tvIdx).text = "순번: $position"
        view.findViewById<TextView>(R.id.tvId).text = items[position]["id"]
        view.findViewById<TextView>(R.id.tvName).text = items[position]["name"]

        return view
    }

}
