package com.example.gd10_a_10668

import com.google.gson.annotations.SerializedName

data class MahasiswaData(
    @SerializedName("mhsnobp") val nim:String,
    @SerializedName("mhsnama") val nama:String,
    @SerializedName("mhsalamat") val alamat:String,
    @SerializedName("prodinama") val prodi:String,
    @SerializedName("mhstgllhr") val tgllhr:String,
)