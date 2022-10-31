package com.appic.assignment.ui

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.appic.assignment.databinding.BottomsheetSelectBrandBinding
import com.appic.assignment.ui.adapter.BrandListAdapter
import com.appic.assignment.ui.model.Item
import com.appic.assignment.util.BRAND_LIST_KEY
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BrandBottomSheetFragment : BottomSheetDialogFragment() {

    private var brandList: List<Item>? = null
    lateinit var binding: BottomsheetSelectBrandBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            BottomSheetBehavior.from(binding.bottomSheetLayout).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetSelectBrandBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        initialization()
    }

    private fun initialization() {

        Log.i(BRAND_LIST_KEY, brandList.toString())
        val brandAdapter = brandList?.let { BrandListAdapter(it) }
        binding.rvBrands.adapter = brandAdapter
        binding.rvBrands.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setUpListener() {
        binding.etSearchBrands.addTextChangedListener(object : TextWatcher {
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