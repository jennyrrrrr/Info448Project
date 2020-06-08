package com.example.info448project.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateInfo (
    val date: Int,
    val state: String,
    val positive: Int,
    val negative: Int,
    val pending: String,
    val hospitalizedCurrently: Int,
    val hospitalizedCumulative: String,
    val inIcuCurrently: Int,
    val inIcuCumulative: Int,
    val onVentilatorCurrently: Int,
    val onVentilatorCumulative: Int,
    val recovered: Int,
    val dataQualityGrade: String,
    val lastUpdateEt: String,
    val dateModified: String,
    val checkTimeEt: String,
    val death: Int,
    val hospitalized: Int,
    val dateChecked: String,
    val fips: String,
    val positiveIncrease: Int,
    val negativeIncrease: Int,
    val total: Int,
    val totalTestResults: Int,
    val totalTestResultsIncrease: Int,
    val posNeg: Int,
    val deathIncrease: Int,
    val hospitalizedIncrease: Int,
    val hash: String,
    val commercialScore: Int,
    val negativeRegularScore: Int,
    val negativeScore: Int,
    val positiveScore: Int,
    val score: Int,
    val grade: String
): Parcelable