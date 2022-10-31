package com.appic.assignment.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appic.assignment.R
import com.appic.assignment.databinding.BottomsheetSelectLocationBinding
import com.appic.assignment.ui.adapter.BrandListAdapter
import com.appic.assignment.ui.model.Item
import com.appic.assignment.util.BRAND_LIST_KEY


class LocationBottomSheetFragment() : Fragment() {

    private var brandList: List<Item>? = null
    lateinit var binding: BottomsheetSelectLocationBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetSelectLocationBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.bottomsheet_select_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        initialization()
    }

    private fun initialization() {

        Log.i(BRAND_LIST_KEY, brandList.toString())
        val brandAdapter = brandList?.let { BrandListAdapter(it) }
        binding.rvLocation.adapter = brandAdapter
        binding.rvLocation.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setUpListener() {
        binding.etSearchLocation.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i("Text Changed ", text.toString())
            }
        })
    }

}