package com.example.project_last

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_last.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity() {
    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    lateinit var adapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // db로 부터 데이터를 가져온다.
        // 데이터를 recycler view에 뿌린다.
        val searchList = search_db.getAllData()
        val listAdapter = SearchAdapter(searchList)
        adapter = listAdapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchRecyclerView.adapter = adapter

        binding.ivSearchicon.setOnClickListener {
            val keyword = binding.etSearchbar.text.toString()
            searchList.add(keyword)
            search_db.insertKeyword(keyword)
            val intent= Intent(this, ShowAllActivity::class.java)
            intent.putExtra("KEYWORD", keyword)
            startActivity(intent)
            adapter.notifyDataSetChanged()
        }
    }
}