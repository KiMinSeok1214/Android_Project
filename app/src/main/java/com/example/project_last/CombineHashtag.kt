package com.example.project_last


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_last.databinding.ActivityCombineHashtagBinding
import com.example.project_last.databinding.ActivityModifyHashtagBinding
import java.nio.file.Files.delete

class CombineHashtag : BaseActivity() {
    val binding by lazy { ActivityCombineHashtagBinding.inflate(layoutInflater) }
    lateinit var adapter: HashtagCombineAdapter
    var select_mode:Boolean = false
    var add_mode:Boolean = false
    lateinit var hashtagList: ArrayList<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // db로 부터 데이터를 가져온다.
        hashtagList = db.getAllHashtag()
        // 데이터를 recycler view에 뿌린다.
        val listAdapter = HashtagCombineAdapter(hashtagList)
        adapter = listAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

}