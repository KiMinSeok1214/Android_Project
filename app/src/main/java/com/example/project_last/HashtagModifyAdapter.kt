package com.example.project_last

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.HashtagModifyListBinding

class HashtagModifyViewHolder(val binding: HashtagModifyListBinding): RecyclerView.ViewHolder(binding.root) {

}

class HashtagModifyAdapter(val hashtagList: ArrayList<Item>): RecyclerView.Adapter<HashtagModifyViewHolder>() {
    var mode = "normal"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagModifyViewHolder
            = HashtagModifyViewHolder(HashtagModifyListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: HashtagModifyViewHolder, position: Int) {
        val binding = holder.binding

        with(binding) {
            tvHashtagname.text = "#" + hashtagList[position].hashtag
        }
        binding.hashtagLayout.setOnClickListener {
            setMultipleSelection(position)
        }
        if (hashtagList[position].selected)
            binding.hashtagLayout.setBackgroundColor(Color.parseColor("#90caf9"))
        else
            binding.hashtagLayout.setBackgroundColor(Color.parseColor("#ffffff"))
    }
    override fun getItemCount(): Int = hashtagList.size

    fun setMultipleSelection(position: Int) {
        if (mode != "normal") {
            hashtagList[position].selected = !hashtagList[position].selected
            notifyDataSetChanged()
        }
    }
}