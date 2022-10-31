package com.appic.assignment.ui

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appic.assignment.R
import com.appic.assignment.data.model.Data
import com.appic.assignment.databinding.ActivityMainBinding
import com.appic.assignment.ui.adapter.BrandListAdapter
import com.appic.assignment.ui.adapter.CompanyAdapter
import com.appic.assignment.ui.model.Item
import com.appic.assignment.ui.viewmodel.MainViewModel
import com.appic.assignment.util.Status
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var data: Data
    private var locationList: ArrayList<Item> = ArrayList()
    private var brandList: ArrayList<Item> = ArrayList()
    private var accountList: ArrayList<Item> = ArrayList()
    private var companyList: ArrayList<String> = ArrayList()
    lateinit var binding: ActivityMainBinding

    private lateinit var companyAdapter: CompanyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpDefaultValue()
        setObserver()
    }

    private fun setUpDefaultValue() {
        binding.vgAccount.tvAccount.text = "0"
        binding.vgSelectAc.tvSelectedAcCount.text = "0"
        binding.vgBrand.tvBrand.text = "0"
        binding.vgSelectBrand.tvSelectedBrandCount.text = "0"
        binding.vgLocation.tvLocation.text = "0"
        binding.vgSelectLocation.tvSelectedLocationCount.text = "0"
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver() {
        mainViewModel.data.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        setData(data)
                    }
                    data = it.data!!
                    Log.d("Data ", data.toString())
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        }

        mainViewModel.accountList.observe(this) { list ->
            accountList = list
            val count = list.count { it.status }
            binding.vgSelectAc.tvSelectedAcCount.text = "$count"
            binding.vgAccount.tvAccount.text = "$count"
            Log.i("Selected Count ", "$count")
        }

        mainViewModel.brandList.observe(this) { list ->
                    brandList = list
                    val count = list.count { it.status }
                    binding.vgSelectBrand.tvSelectedBrandCount.text = "$count"
                    binding.vgBrand.tvBrand.text = "$count"
                    Log.i("Selected Count ", "$count")
                }

        mainViewModel.locationList.observe(this) { list ->
            locationList = list
            val count = list.count { it.status }
            binding.vgSelectLocation.tvSelectedLocationCount.text = "$count"
            binding.vgLocation.tvLocation.text = "$count"
            Log.i("Selected Count ", "$count")
        }

        mainViewModel.showCompany.observe(this) { flag ->
            if (flag)
                binding.rvCompany.visibility = View.VISIBLE
            else
                binding.rvCompany.visibility = View.INVISIBLE
            val list = companyList
            companyAdapter = CompanyAdapter(list)
            binding.rvCompany.adapter = companyAdapter
            companyAdapter.notifyDataSetChanged()
        }

        mainViewModel.companyList.observe(this) { list ->
            companyList = list
        }
    }

    private fun setData(data: Data) {
        this.data = data
        setClickListener()
    }

    private fun setClickListener() {
        binding.vgSelectAc.root.setOnClickListener{
            showAccountBottomSheet(R.layout.bottomsheet_select_account, accountList)
        }
        (binding.vgSelectBrand.root as View).setOnClickListener {
            showBrandBottomSheet(R.layout.bottomsheet_select_brand, brandList)
        }
        binding.vgSelectLocation.root.setOnClickListener {
            Log.d("Location List ", locationList.toString())
            showLocationBottomSheet(R.layout.bottomsheet_select_location, locationList)
        }
    }

    private fun showAccountBottomSheet(layout: Int, locationList: List<Item>) {
        val sheetView = layoutInflater.inflate(layout, null)
        val dialog = showBottomSheetDialog(layout, sheetView)
        sheetView.findViewById<ImageView>(R.id.close_bottomsheet)?.setOnClickListener {
            dialog.dismiss()
        }
        val btApplyFilter = sheetView.findViewById<Button>(R.id.bt_account_filter)
        btApplyFilter.setOnClickListener {
            mainViewModel.setAccountList(accountList)
            dialog.dismiss()
        }

        val locationAdaptor = BrandListAdapter(locationList)
        val recyclerView = sheetView.findViewById<RecyclerView>(R.id.rv_accounts)
        recyclerView.adapter = locationAdaptor
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun showBrandBottomSheet(layout: Int, locationList: List<Item>) {
        val sheetView = layoutInflater.inflate(layout, null)
        val dialog = showBottomSheetDialog(layout, sheetView)
        sheetView.findViewById<ImageView>(R.id.close_bottomsheet)?.setOnClickListener {
            dialog.dismiss()
        }
        val btApplyFilter = sheetView.findViewById<Button>(R.id.bt_brand_filter)
        btApplyFilter.setOnClickListener {
            mainViewModel.setBrandList(brandList)
            dialog.dismiss()
        }



        val locationAdaptor = BrandListAdapter(locationList)
        val recyclerView = sheetView.findViewById<RecyclerView>(R.id.rv_brands)
        recyclerView.adapter = locationAdaptor
        recyclerView.layoutManager = LinearLayoutManager(this)

        val selectAllCB = sheetView.findViewById<CheckBox>(R.id.cb_select_all)
        selectAllCB.setOnCheckedChangeListener { _, flag ->
            brandList.forEach {
                it.status = flag
                locationAdaptor.notifyDataSetChanged()
            }
        }
    }

    private fun showLocationBottomSheet(layout: Int, locationList: List<Item>) {
        val sheetView = layoutInflater.inflate(layout, null)
        val dialog = showBottomSheetDialog(layout, sheetView)
        sheetView.findViewById<ImageView>(R.id.close_bottomsheet)?.setOnClickListener {
            dialog.dismiss()
        }
        val btLocationFilter = sheetView.findViewById<Button>(R.id.bt_location_filter)
        btLocationFilter.setOnClickListener {
            mainViewModel.setLocationList(this.locationList)
            dialog.dismiss()
        }

        val locationAdaptor = BrandListAdapter(locationList)
        val recyclerView = sheetView.findViewById<RecyclerView>(R.id.rv_location)
        recyclerView.adapter = locationAdaptor
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun showBottomSheetDialog(layout: Int, sheetView: View): BottomSheetDialog {
        val dialog = BottomSheetDialog(this)
        dialog.setOnShowListener {
            val bottomSheet: FrameLayout =
                dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                    ?: return@setOnShowListener
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            showFullScreenBottomSheet(bottomSheet)
            expandBottomSheet(bottomSheetBehavior)
        }

        dialog.setContentView(sheetView)
        dialog.show()
        return dialog
    }

    private fun expandBottomSheet(bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) {
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun showFullScreenBottomSheet(bottomSheet: FrameLayout) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = Resources.getSystem().displayMetrics.heightPixels
        bottomSheet.layoutParams = layoutParams
    }
}