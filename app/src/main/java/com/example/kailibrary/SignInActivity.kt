package com.example.kailibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kailibrary.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.imageView8.setOnClickListener {
            onBackPressed()
        }
    }
}