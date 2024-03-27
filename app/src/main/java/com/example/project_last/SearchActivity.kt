package com.example.project_last

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ActivitySearchBinding
import com.example.project_last.databinding.SearchListBinding

class SearchActivity : BaseActivity() {
    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    lateinit var adapter: SearchAdapter

    inner class SearchViewHolder(val binding: SearchListBinding) : RecyclerView.ViewHolder(binding.root)
    inner class SearchAdapter(val searchList: ArrayList<String>): RecyclerView.Adapter<SearchViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
            val itemBinding = SearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SearchViewHolder(itemBinding)
        }

        override fun getItemCount(): Int = searchList.size

        override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            val itembinding = holder.binding

            itembinding.tvSearch.text = searchList[position]
            itembinding.ivKwdelete.setOnClickListener {
                val keyword = itembinding.tvSearch.text.toString()
                if (searchList.contains(keyword)) {
                    search_db.deleteItem(keyword)
                    searchList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
            itembinding.tvSearch.setOnClickListener {
                val keyword = itembinding.tvSearch.text.toString() // 클릭한 항목의 검색어 텍스트 가져오기
                val intent = Intent(this@SearchActivity, ShowAllActivity::class.java)
                intent.putExtra("KEYWORD", keyword) // 검색어 정보를 Intent에 추가
                startActivity(intent)
            }
        }
    }
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
            val intent = Intent(this, ShowAllActivity::class.java)
            intent.putExtra("KEYWORD", keyword)
            startActivity(intent)
            binding.etSearchbar.setText("")
            adapter.notifyDataSetChanged()
        }

    }

}