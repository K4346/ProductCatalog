package com.example.productcatalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.example.productcatalog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun initToolBar(title: String, hasAction: Boolean = false, action: () -> Unit = { }) {
        binding.actionBarMain.tvTitle.text = title
        if (hasAction) {
            binding.actionBarMain.buttonBack.isInvisible = false
            binding.actionBarMain.buttonBack.setOnClickListener { action.invoke() }
        } else {
            binding.actionBarMain.buttonBack.isInvisible = true
        }

    }
}