package com.example.project_last

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_last.databinding.ActivityShowRestBinding

class ShowRestActivity : BaseActivity() {
    val binding by lazy { ActivityShowRestBinding.inflate(layoutInflater) }
    var diaryList = ArrayList<Diary>()
    lateinit var adapter: DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rest_name = intent.getStringExtra("rest_name")
        binding.tvRestname.text = rest_name

        rest_name?.let {
            diaryList = db.getDiaryList(rest_name)
            adapter = DiaryAdapter(diaryList)
            binding.rvDiary.layoutManager = LinearLayoutManager(this)
            binding.rvDiary.adapter = adapter
        }
    }
}