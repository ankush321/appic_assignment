package com.appic.assignment.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.appic.assignment.R
import com.appic.assignment.ui.model.Item
import com.appic.assignment.util.inflateView

class BrandListAdapter(private val list: List<Item>) : RecyclerView.Adapter<BrandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(list[position])
        holder.clickListener(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val brandName: CheckBox = itemView.findViewById(R.id.rv_cb)

    fun bind(item: Item) {
        brandName.text = item.name
        brandName.isChecked = item.status
    }

    fun clickListener(item: Item) {
        brandName.setOnClickListener{
            item.status = !item.status
        }

    }

    companion object {
        fun create(parent: ViewGroup): BrandViewHolder = BrandViewHolder(parent.inflateView(R.layout.recycler_layout))
    }

}