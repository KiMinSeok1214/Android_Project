package com.example.project_last

import android.content.Intent
import android.os.Bundle
import com.example.project_last.databinding.ActivityCategoryBinding

class CategoryActivity:BaseActivity() {
    private val binding: ActivityCategoryBinding by lazy{ ActivityCategoryBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.layoutCategoryModify.setOnClickListener {
            val intent= Intent(this, ModifyHashtag::class.java)
            startActivity(intent)
        }
    }

}