package com.example.project_last

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_last.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnShowall.setOnClickListener {
            val intent = Intent(this, ShowAllActivity::class.java)
            startActivity(intent)
        }
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddDiaryActivity::class.java)
            startActivity(intent)
        }
        binding.btnHashtag.setOnClickListener {
            val intent = Intent(this, TestHasgtagActivity::class.java)
            startActivity(intent)
        }
        binding.btnCategory.setOnClickListener {
            val intent=Intent(this, ShowCategoryActivity::class.java)
            startActivity(intent)
        }
    }
}