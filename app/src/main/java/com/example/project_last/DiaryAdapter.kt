package com.example.project_last

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.DiaryListBinding
import com.example.project_last.databinding.MenuListBinding
import java.util.ArrayList

class DiaryAdapter(val diaryList: ArrayList<Diary>): RecyclerView.Adapter<DiaryAdapter.RestViewHolder>() {
    inner class RestViewHolder(val binding: DiaryListBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestViewHolder
            = RestViewHolder(DiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = diaryList.size
    override fun onBindViewHolder(holder: RestViewHolder, position: Int) {
        val binding = holder.binding

        binding.tvDiaryDate.text = diaryList[position].date
        binding.tvDiaryComment.text = diaryList[position].rest_comment
        binding.diartRatingBar.rating = diaryList[position].rest_star
    }
}