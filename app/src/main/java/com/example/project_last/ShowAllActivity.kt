package com.example.project_last


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_last.databinding.ActivityShowAllBinding

class ShowAllActivity : BaseActivity() {
    val binding by lazy { ActivityShowAllBinding.inflate(layoutInflater) }
    lateinit var adapter: RestAdapter
    lateinit var restList:ArrayList<Restaurent>
    lateinit var originalList: ArrayList<Restaurent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActivity()
        // db로 부터 데이터를 가져온다.
        val keyword = intent.getStringExtra("KEYWORD") ?: ""
        if (keyword.isNotEmpty())
            restList = db.getKeywordData(keyword)
        else
            restList = db.getAllRestaurent()

        // 데이터를 recycler view에 뿌린다.
        val listAdapter = RestAdapter(restList)
        adapter = listAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
        sortRest()
    }

    private fun sortRest() {
        binding.btnSort.setOnClickListener {
            if (binding.btnSort.text == "별점순") {
                originalList = restList
                restList.sortByDescending { rest -> rest.recent_star }
                binding.btnSort.text = "날짜순"
            }
            else {
                restList = originalList
                binding.btnSort.text = "별점순"
            }
            adapter.notifyDataSetChanged()
        }

    }

    private fun initActivity() {
        setSupportActionBar(binding.showAllToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("일기")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}