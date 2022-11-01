package com.appic.assignment.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appic.assignment.R
import com.appic.assignment.ui.model.Item
import com.appic.assignment.util.inflateView

class CompanyAdapter(private val list: List<String>) : RecyclerView.Adapter<CompanyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        return CompanyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateList(list: List<Item>){
    }
}

class CompanyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvCompany = view.findViewById<TextView>(R.id.tv_company)

    fun bind(companyName: String) {
        tvCompany.text = companyName
    }

    companion object {
        fun create(parent: ViewGroup): CompanyViewHolder =
            CompanyViewHolder(parent.inflateView(R.layout.layout_company))
    }
}