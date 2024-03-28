package com.example.project_last

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ActivityShowRestBinding
import com.example.project_last.databinding.SearchListBinding
import com.example.project_last.databinding.ShowRestHashtagBinding

class ShowRestActivity : BaseActivity() {
    val binding by lazy { ActivityShowRestBinding.inflate(layoutInflater) }
    var diaryList = ArrayList<Diary>()
    lateinit var diaryadapter: DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActivity()
        val rest_name = intent.getStringExtra("rest_name")
        binding.tvRestname.text = rest_name

        rest_name?.let {
            diaryList = db.getDiaryList(rest_name)
            diaryadapter = DiaryAdapter(diaryList)
            binding.rvDiary.layoutManager = LinearLayoutManager(this)
            binding.rvDiary.adapter = diaryadapter
        }
    }
    private fun initActivity() {
        setSupportActionBar(binding.showRestToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("음식일기")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}