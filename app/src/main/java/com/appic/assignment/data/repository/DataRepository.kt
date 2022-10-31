package com.appic.assignment.data.repository

import android.content.Context
import com.appic.assignment.data.model.Data
import com.appic.assignment.util.Resource
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class DataRepository(private val context: Context) {
    fun getData(): Resource<Data> {
        val jsonString: String =
            context.assets.open("TestJSON.txt").bufferedReader().use { it.readText() }
        val listCountryType = object : TypeToken<Data>() {}.type
        val data: Data? = Gson().fromJson(jsonString, listCountryType)
        return if (data != null) Resource.success(data) else Resource.error(
            "Failed to fetch data",
            null
        )
    }
}