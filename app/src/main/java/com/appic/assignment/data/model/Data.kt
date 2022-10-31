package com.appic.assignment.data.model

data class Data(
    val errorCode: String,
    val filterData: List<FilterData>,
    val message: String,
    val status: String
)