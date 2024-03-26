package com.example.project_last

import android.content.Intent
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
            val intent = Intent(this, TestHashtagActivity::class.java)
            startActivity(intent)
        }
        binding.btnCategory.setOnClickListener {
            val intent=Intent(this, TestCategoryActivity::class.java)
            startActivity(intent)
        }
        binding.btnHashtagModify.setOnClickListener {
            val intent=Intent(this, HashtagActivity::class.java)
            startActivity(intent)
        }
        binding.btnCategoryModify.setOnClickListener {
            val intent=Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
        binding.btnSearch.setOnClickListener {
            val intent=Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}