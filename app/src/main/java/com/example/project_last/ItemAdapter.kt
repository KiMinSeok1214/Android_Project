package com.example.project_last

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ItemListBinding

class ItemViewHolder(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {

}

class ItemAdapter(val itemList: ArrayList<Item>): RecyclerView.Adapter<ItemViewHolder>() {
    var mode = "normal"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder
            = ItemViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding

        with(binding) {
            tvName.text = itemList[position].rest_name
            avgRate.rating = itemList[position].rest_star.toFloat()
            tvMycomment.text = itemList[position].rest_comment
        }
        binding.itemLayout.setOnClickListener {
            setMultipleSelection(position)
            if (!itemList[position].selected) {
                itemClickListener.onClick(binding.root, position)
            }
        }
        if (itemList[position].selected)
            binding.itemLayout.setBackgroundColor(Color.parseColor("#90caf9"))
        else
            binding.itemLayout.setBackgroundColor(Color.parseColor("#ffffff"))
    }

    // 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener
    override fun getItemCount(): Int = itemList.size

    fun setMultipleSelection(position: Int) {
        if (mode != "normal") {
            itemList[position].selected = !itemList[position].selected
            notifyDataSetChanged()
        }
    }
}