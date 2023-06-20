package com.fghilmany.sharedpreference

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fghilmany.sharedpreference.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var preferenceManager: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        preferenceManager = SharedPreferenceManager(this).instance
        clickEvent()
    }

    @SuppressLint("SetTextI18n")
    private fun clickEvent() {
        binding.btSave.setOnClickListener {
            val value = binding.etInput.text.toString()
            preferenceManager.setValue(SharedPreferenceManager.SP_KEY, value)
            val dataFromSP = preferenceManager.getString(SharedPreferenceManager.SP_KEY)
            binding.tvResult.text = "Data from SP: $dataFromSP"
        }
        binding.btClear.setOnClickListener {
            preferenceManager.clear()
            val dataFromSP = preferenceManager.getString(SharedPreferenceManager.SP_KEY)
            binding.tvResult.text = "Data from SP: $dataFromSP"
        }
    }
}