package com.example.project_last

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.RestListBinding
import com.example.project_last.databinding.MenuListBinding

class MenuViewHolder(val binding: MenuListBinding):RecyclerView.ViewHolder(binding.root)
class MenuAdapter(val itemList: ArrayList<Item>):RecyclerView.Adapter<MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder
            = MenuViewHolder(MenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val binding = holder.binding

        binding.tvMenuName.setText(itemList[position].menu_name)
        binding.tvMenuComment.setText(itemList[position].menu_comment)
        binding.tvPrice.setText(itemList[position].price.toString())
        binding.menuRatingbar.rating = itemList[position].menu_star.toFloat()
        // 카테고리, 이미지는 나중에 고려..

    }

    override fun getItemCount(): Int = itemList.size
}