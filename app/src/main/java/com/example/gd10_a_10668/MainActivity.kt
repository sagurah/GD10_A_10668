package com.example.gd10_a_10668

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.gd10_a_10668.databinding.ActivityMainBinding
import com.example.gd10_a_10668.fragment.DataMahasiswaFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDataFragment()
        binding.txtCari.setOnKeyListener(View.OnKeyListener{ _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                showDataFragment()
                return@OnKeyListener true
            }
            false
        })
        binding.btnAdd.setOnClickListener{
            startActivity(
                Intent(this, FormAddMahasiswaActivity::class.java)
            )
        }
    }

    fun showDataFragment() {
        val mFragmentTransaction = supportFragmentManager.beginTransaction()
        val mFragment = DataMahasiswaFragment()
        val textCari = binding.txtCari.text
        val mBundle = Bundle()
        mBundle.putString("cari", textCari.toString())
        mFragment.arguments = mBundle
        mFragmentTransaction.replace(R.id.fl_data, mFragment).commit()
    }
}