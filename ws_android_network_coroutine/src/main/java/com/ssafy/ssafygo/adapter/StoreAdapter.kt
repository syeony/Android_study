package com.ssafy.ssafygo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ssafy.ssafygo.R
import com.ssafy.ssafygo.dto.StoreDTO

class StoreAdapter(
    context: Context,
    private val stores: List<StoreDTO>
) : ArrayAdapter<StoreDTO>(context, 0, stores) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_store, parent, false)

        val store = stores[position]
        view.findViewById<TextView>(R.id.storeName).text = store.title().trim()
        view.findViewById<TextView>(R.id.storeLocation).text = store.location().trim()

        return view
    }
}
