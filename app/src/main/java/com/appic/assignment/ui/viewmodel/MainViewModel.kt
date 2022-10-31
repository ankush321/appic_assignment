package com.appic.assignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appic.assignment.data.model.Data
import com.appic.assignment.data.repository.DataRepository
import com.appic.assignment.ui.model.Item
import com.appic.assignment.util.Resource
import com.appic.assignment.util.Status
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private var _data = MutableLiveData<Resource<Data>>()
    val data: LiveData<Resource<Data>>
        get() = _data

    private var _showCompany = MutableLiveData<Boolean>()
    val showCompany: LiveData<Boolean>
        get() = _showCompany

    private var _accountList = MutableLiveData<ArrayList<Item>>()
    val accountList: LiveData<ArrayList<Item>>
        get() = _accountList

    private var _brandList = MutableLiveData<ArrayList<Item>>()
    val brandList: LiveData<ArrayList<Item>>
        get() = _brandList

    private var _locationList = MutableLiveData<ArrayList<Item>>()
    val locationList: LiveData<ArrayList<Item>>
        get() = _locationList

    private var _companyList = MutableLiveData<ArrayList<String>>()
    val companyList: LiveData<ArrayList<String>>
        get() = _companyList


    init {
        fetchData()
    }

    private fun checkCompany() {
        var flag = false
        var accountCount = 0
        var brandCount = 0
        var locationCount = 0
        brandList.value?.let { brandL ->
            brandCount = brandL.count { it.status }
        }
        locationList.value?.let { loc ->
            locationCount = loc.count { it.status }
        }
        accountList.value?.let { accoutL ->
            accountCount = accoutL.count { it.status }

        }

        if (brandCount > 0 && locationCount > 0 && accountCount > 0)
            flag = true
        _showCompany.postValue(flag)
    }

    fun setAccountList(list: ArrayList<Item>) {
        _accountList.postValue(list)
        checkCompany()
    }

    fun setBrandList(list: ArrayList<Item>) {
        _brandList.postValue(list)
        checkCompany()
    }

    fun setLocationList(list: ArrayList<Item>) {
        _locationList.postValue(list)
        checkCompany()
    }

    private fun setData(data: Data) {
        val brandList = ArrayList<Item>()
        val locationList = ArrayList<Item>()
        val accountList = ArrayList<Item>()
        val listCompany = ArrayList<String>()

        data.filterData.forEach { filterData ->
            listCompany.add(filterData.companyName)
            filterData.hierarchy.forEach { hierarchy ->
                accountList.add(Item(hierarchy.accountNumber, false))
                hierarchy.brandNameList.forEach { brandNameList ->
                    brandList.add(Item(brandNameList.brandName, false))
                    brandNameList.locationNameList.forEach {
                        locationList.add(Item(it.locationName, false))
                    }
                }
            }
        }
        _accountList.postValue(accountList)
        _brandList.postValue(brandList)
        _locationList.postValue(locationList)
        _companyList.postValue(listCompany)
    }

    private fun fetchData() {
        viewModelScope.launch {
            _data.postValue(Resource.loading(null))
            dataRepository.getData().let {
                if (it.status == Status.SUCCESS) {
                    _data.postValue(Resource.success(it.data))
                    it.data?.let { it1 -> setData(it1) }
                } else _data.postValue(Resource.error(it.message.toString(), null))
            }
        }

    }
}