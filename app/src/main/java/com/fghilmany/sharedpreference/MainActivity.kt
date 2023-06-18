package com.fghilmany.sharedpreference

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fghilmany.sharedpreference.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        clickEvent()
    }

    @SuppressLint("SetTextI18n")
    private fun clickEvent() {
        binding.root.setOnClickListener {
            binding.tvResult.text = "Data from SP"
        }
    }
}