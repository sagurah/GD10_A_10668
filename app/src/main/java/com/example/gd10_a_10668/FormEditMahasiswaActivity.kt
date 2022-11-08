package com.example.gd10_a_10668

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.gd10_a_10668.databinding.ActivityFormEditMahasiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class FormEditMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFormEditMahasiswaBinding
    private var b:Bundle? = null
    private val listMahasiswa = ArrayList<MahasiswaData>()
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormEditMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Form Edit Mahasiswa"
        b = intent.extras
        val nim = b?.getString("nim")
        Toast.makeText(this, nim, Toast.LENGTH_SHORT).show()
        binding.tvEditTgl.setOnClickListener {
            val datePicker = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayofMonth ->
                binding.tglEditView.text = year.toString() + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayofMonth)
            }
            dateDialog(this,datePicker).show()
        }
        nim?.let { getDetailData(it) }
        binding.btnUpdate.setOnClickListener {
            with(binding) {
                val nama = txtEditNama.text.toString()
                val alamat = txtEditAlamat.text.toString()
                val prodi = txtEditProdi.text.toString()
                val tgllahir = tglEditView.text.toString()

                RClient.instances.updateData(nim,nama,alamat,prodi,tgllahir).enqueue(object : Callback<ResponseCreate> {
                    override fun onResponse(
                        call: Call<ResponseCreate>,
                        response: Response<ResponseCreate>
                    ) {
                        if(response.isSuccessful) {
                            Toast.makeText(applicationContext,"${response.body()?.pesan}",
                                Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                    override fun onFailure(call: Call<ResponseCreate>, t: Throwable) {
                    }
                })
            }
        }
    }
    fun getDetailData(nim:String) {
        RClient.instances.getData(nim).enqueue(object: Callback<ResponseDataMahasiswa> {
            override fun onResponse(
                call: Call<ResponseDataMahasiswa>,
                response: Response<ResponseDataMahasiswa>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        listMahasiswa.addAll(it.data) }
                    with(binding) {
                        txtNobp.setText(listMahasiswa[0].nim)
                        txtEditNama.setText(listMahasiswa[0].nama)
                        txtEditAlamat.setText(listMahasiswa[0].alamat)
                        txtEditProdi.setText(listMahasiswa[0].prodi)
                        tglEditView.setText(listMahasiswa[0].tgllhr)
                    }

                    Toast.makeText(applicationContext,"Data: ${response.body()}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseDataMahasiswa>, t: Throwable) {
                // Alert dialog
                AlertDialog.Builder(this@FormEditMahasiswaActivity)
                    .setTitle("Error")
                    .setMessage(t.localizedMessage)
                    .setPositiveButton("OK"){dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
        })
    }

    private fun dateDialog(context: Context, datePicker:
    DatePickerDialog.OnDateSetListener):DatePickerDialog {
        val calender = Calendar.getInstance()
        return DatePickerDialog(
            context, datePicker,
            calender[Calendar.YEAR],
            calender[Calendar.MONTH],
            calender[Calendar.DAY_OF_MONTH],
        )
    }
}