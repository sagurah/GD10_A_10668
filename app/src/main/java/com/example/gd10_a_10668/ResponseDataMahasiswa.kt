package com.example.gd10_a_10668

import com.google.gson.annotations.SerializedName

data class ResponseDataMahasiswa(
    @SerializedName("status") val stt:String,
    val totaldata: Int,
    val data:List<MahasiswaData>)