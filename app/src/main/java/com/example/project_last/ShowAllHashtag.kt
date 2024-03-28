package com.example.project_last


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.system.Os.rename
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ActivityModifyHashtagBinding
import com.example.project_last.databinding.ActivityShowallHashtagBinding
import com.example.project_last.databinding.HashtagModifyListBinding
import com.example.project_last.databinding.HashtagShowallListBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.nio.file.Files.delete
import java.util.ArrayList

class ShowAllHashtag : BaseActivity() {
    private val binding by lazy { ActivityShowallHashtagBinding.inflate(layoutInflater) }
    private lateinit var adapter: HashtagShowAllAdapter
    private lateinit var hashtagList: ArrayList<Item>
    var mode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        // db로 부터 데이터를 가져온다.
        hashtagList = db.getAllHashtag()

        // 데이터를 recycler view에 뿌린다.
        adapter = HashtagShowAllAdapter(hashtagList)
        binding.showallhashtagrecyclerView.layoutManager = LinearLayoutManager(this)
        binding.showallhashtagrecyclerView.adapter = adapter

    }

    inner class HashtagShowAllViewHolder(val binding: HashtagShowallListBinding): RecyclerView.ViewHolder(binding.root) {}
    inner class HashtagShowAllAdapter(private val hashtagList: ArrayList<Item>) : RecyclerView.Adapter<HashtagShowAllViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagShowAllViewHolder {
            val binding = HashtagShowallListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HashtagShowAllViewHolder(binding)
        }

        override fun onBindViewHolder(holder: HashtagShowAllViewHolder, position: Int) {
            val binding2 = holder.binding
            binding2.tvHashtagname.text = "#" + hashtagList[position].hashtag

            binding2.tvHashtagname.setOnClickListener {
                if (mode == 0) {
                    hashtagList[position].selected = true
                    binding.hashApply.visibility = View.VISIBLE
                    mode = 1
                }
                else {
                    hashtagList[position].selected = false
                    binding.hashApply.visibility = View.INVISIBLE
                    mode = 0
                }
                adapter.notifyDataSetChanged()
            }

            binding.hashApply.setOnClickListener {
                hashtagList[position].selected = false
                binding.hashApply.visibility = View.INVISIBLE
                mode = 0
                adapter.notifyDataSetChanged()
                val intent = Intent(binding.root.context, ShowAllActivity::class.java)
                intent.putExtra("KEYWORD", hashtagList[position].hashtag)
                intent.putExtra("PREACTIVITY", "hashtag")
                startActivity(intent)
            }

            if (hashtagList[position].selected == true) {
                binding2.tvHashtagname.setBackgroundResource(R.drawable.box_hashtagpos1)
            }
            else {
                binding2.tvHashtagname.setBackgroundResource(R.drawable.box_hashtag)
            }
        }



        override fun getItemCount(): Int = hashtagList.size
    }
}