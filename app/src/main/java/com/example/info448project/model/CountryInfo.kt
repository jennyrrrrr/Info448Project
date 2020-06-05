package com.example.info448project.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryInfo (
    val data: Int,
    val states: Int,
    val positive: Int,
    val negative: Int,
    val pending: Int,
    val hospitalizedCurrently: Int,
    val hospitalizedCumulative: Int,
    val inIcuCurrently: Int,
    val inIcuCumulative: Int,
    val onVentilatorCurrently: Int,
    val onVentilatorCumulative: Int,
    val recovered: Int,
    val dateChecked: String,
    val death: Int,
    val hospitalized: Int,
    val lastModified: String,
    val total: Int,
    val totalTestResults: Int,
    val posNeg: Int,
    val deathIncrease: Int,
    val hospitalizedIncrease: Int,
    val negativeIncrease: Int,
    val positiveIncrease: Int,
    val totalTestResultsIncrease: Int,
    val hash: String
): Parcelable