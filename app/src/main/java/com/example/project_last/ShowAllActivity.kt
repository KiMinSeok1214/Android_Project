package com.example.project_last


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_last.databinding.ActivityShowAllBinding

class ShowAllActivity : BaseActivity() {
    val binding by lazy { ActivityShowAllBinding.inflate(layoutInflater) }
    lateinit var adapter: RestAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // db로 부터 데이터를 가져온다.
        var restList = db.getAllRestaurent()
        // 데이터를 recycler view에 뿌린다.
        val listAdapter = RestAdapter(restList)
        adapter = listAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

}