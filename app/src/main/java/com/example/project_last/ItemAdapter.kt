package com.example.project_last

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ItemListBinding

class ItemViewHolder(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root)

class ItemAdapter(val itemList: ArrayList<Item>): RecyclerView.Adapter<ItemViewHolder>() {
    var mode = "normal"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder
            = ItemViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding


        with(binding) {
            tvName.text = itemList[position].rest_name
            tvComment.text = itemList[position].rest_comment
            tvStar.text = itemList[position].rest_star.toString()
        }
        binding.itemLayout.setOnClickListener {
            setMultipleSelection(position)
        }
        if (itemList[position].selected)
            binding.itemLayout.setBackgroundColor(Color.parseColor("#90caf9"))
        else
            binding.itemLayout.setBackgroundColor(Color.parseColor("#ffffff"))
    }

    override fun getItemCount(): Int = itemList.size

    fun setMultipleSelection(position: Int) {
        if (mode != "normal") {
            itemList[position].selected = !itemList[position].selected
            notifyDataSetChanged()
        }
    }
}