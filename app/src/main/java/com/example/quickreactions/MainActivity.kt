package com.example.quickreactions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quickreactions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).apply {
            setSupportActionBar(toolbar)
        }.root)
    }
}