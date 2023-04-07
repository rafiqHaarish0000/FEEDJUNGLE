package com.example.feedjungle.ui.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.feedjungle.R
import com.example.feedjungle.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textView11.text = "hello dev"
    }
}