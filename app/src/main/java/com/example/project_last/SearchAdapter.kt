package com.example.project_last

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ActivitySearchBinding
import com.example.project_last.databinding.SearchListBinding

class SearchAdapter(val searchList: ArrayList<String>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    class SearchViewHolder(val binding: SearchListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder
            = SearchViewHolder(SearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = searchList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val binding = holder.binding

        binding.tvSearch.text = searchList[position]
    }
}