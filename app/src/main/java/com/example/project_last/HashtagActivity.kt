package com.example.project_last

import android.content.Intent
import android.os.Bundle
import com.example.project_last.databinding.ActivityHashtagBinding

class HashtagActivity:BaseActivity() {
    private val binding: ActivityHashtagBinding by lazy{ ActivityHashtagBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.layoutHashtagModify.setOnClickListener {
            val intent= Intent(this, ModifyHashtag::class.java)
            startActivity(intent)
        }
        binding.layoutHashtagCombine.setOnClickListener {
            val intent= Intent(this, CombineHashtag::class.java)
            startActivity(intent)
        }
    }
}