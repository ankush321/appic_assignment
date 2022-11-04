package com.appic.assignment.ui.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.appic.assignment.R
import com.appic.assignment.ui.model.Item
import com.appic.assignment.util.inflateView
import java.util.*
import kotlin.collections.ArrayList

class BrandListAdapter(private var list: ArrayList<Item>) : RecyclerView.Adapter<BrandViewHolder>(),
    Filterable {

    private var filteredList = ArrayList<Item>()
    private val copyOriginalList = mutableListOf<Item>().apply { addAll(list) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(list[position])
        holder.clickListener(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateData(list: ArrayList<Item>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(text: CharSequence?): FilterResults {
                val filterResult = FilterResults()
                if (text == null || text.isEmpty()) {
                    filterResult.values = copyOriginalList
                    filterResult.count = copyOriginalList.size
                    return filterResult
                } else {
                    filteredList.clear()
                    val filterText = text.toString().lowercase(Locale.getDefault())
                    copyOriginalList.forEach {
                        if (it.name.lowercase().startsWith(filterText, true)) filteredList.add(it)
                    }
                }
                filterResult.values = filteredList
                filterResult.count = filteredList.size
                Log.i("FilterData", filteredList.toString())
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, result: FilterResults?) {
                list.clear()
                list.addAll(result!!.values as ArrayList<Item>)
                notifyDataSetChanged()
            }

        }
        return filter
    }
}

class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val brandName: CheckBox = itemView.findViewById(R.id.rv_cb)

    fun bind(item: Item) {
        brandName.text = item.name
        brandName.isChecked = item.status
    }

    fun clickListener(item: Item) {
        brandName.setOnClickListener {
            item.status = !item.status
        }

    }

    companion object {
        fun create(parent: ViewGroup): BrandViewHolder =
            BrandViewHolder(parent.inflateView(R.layout.recycler_layout))
    }

}