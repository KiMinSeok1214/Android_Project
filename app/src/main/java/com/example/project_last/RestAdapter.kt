package com.example.project_last

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.RestListBinding

class RestAdapter(val restList: ArrayList<Restaurent>): RecyclerView.Adapter<RestAdapter.RestViewHolder>() {
    var mode = "normal"
    inner class RestViewHolder(val binding: RestListBinding): RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestAdapter.RestViewHolder
            = RestViewHolder(RestListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RestAdapter.RestViewHolder, position: Int) {
        val binding = holder.binding

        with(binding) {
            tvName.text = restList[position].rest_name
            avgRate.rating = restList[position].recent_star.toFloat()
            tvMycomment.text = restList[position].comment
            Log.d("ki", avgRate.rating.toString())

        }
        binding.restLayout.setOnClickListener {
            val intent = Intent(binding.root.context, ShowRestActivity::class.java)
            intent.putExtra("rest_name", restList[position].rest_name)
            binding.root.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int = restList.size
}