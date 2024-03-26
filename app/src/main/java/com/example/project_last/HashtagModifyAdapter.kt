package com.example.project_last

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.HashtagModifyListBinding

class HashtagModifyViewHolder(val binding: HashtagModifyListBinding): RecyclerView.ViewHolder(binding.root) {

}

class HashtagModifyAdapter(val hashtagList: ArrayList<Item>): RecyclerView.Adapter<HashtagModifyViewHolder>() {
    var mode = "normal"
    var selectpos:Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagModifyViewHolder
            = HashtagModifyViewHolder(HashtagModifyListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: HashtagModifyViewHolder, position: Int) {
        val binding = holder.binding

        with(binding) {
            tvHashtagname.text = "#" + hashtagList[position].hashtag
        }
        binding.ivMore.setOnClickListener {
            selectpos = position
            Log.d("ki", "$selectpos")
            val activity = ModifyHashtag.getInstance()
            activity?.showpanel()
        }
    }
    override fun getItemCount(): Int = hashtagList.size

}