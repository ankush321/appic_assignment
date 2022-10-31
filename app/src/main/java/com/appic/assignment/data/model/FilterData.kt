package com.appic.assignment.data.model

data class FilterData(
    val Cif: String,
    val companyName: String,
    val hierarchy: List<Hierarchy>
)