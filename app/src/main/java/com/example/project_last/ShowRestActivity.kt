package com.example.project_last

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ActivityShowRestBinding
import com.example.project_last.databinding.DiaryListBinding
import com.example.project_last.databinding.MenuListBinding
import com.example.project_last.databinding.SearchListBinding
import com.example.project_last.databinding.ShowRestHashtagBinding
import com.example.project_last.databinding.ShowhasgtagListBinding

class ShowRestActivity : BaseActivity() {
    val binding by lazy { ActivityShowRestBinding.inflate(layoutInflater) }
    companion object {
        lateinit var diaryadapter: DiaryAdapter
        var diaryList = ArrayList<Diary>()
    }
    lateinit var hashtagadapter: HashtagAdapter
    lateinit var originalList: ArrayList<Diary>
    var rest_name: String? = null
    var preactivity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActivity()
        preactivity = intent.getStringExtra("PREACTIVITY")
        rest_name = intent.getStringExtra("rest_name")
        binding.tvRestname.text = rest_name

        rest_name?.let {
            diaryList = db.getDiaryList(rest_name!!)
            diaryadapter = DiaryAdapter(diaryList)
            binding.rvDiary.layoutManager = LinearLayoutManager(this)
            binding.rvDiary.adapter = diaryadapter
        }
        addDiary()
    }

    private fun addDiary() {
        binding.fabAddDiary.setOnClickListener {
            val intent = Intent(this, AddDiaryActivity::class.java)
            intent.putExtra("rest_name", rest_name)
            startActivity(intent)
        }
    }

    private fun initActivity() {
        setSupportActionBar(binding.showRestToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("음식일기")

        // url 클릭 시 이동
        binding.etUrl.setOnClickListener {
            // 문자열이 아닐 때 입력 창
            if (binding.etUrl.text.isNotEmpty()) {
                val address = binding.etUrl.text.toString()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
                startActivity(intent)
            }
        }
        // 해시태그 추가시
        binding.tvAddHash.setOnClickListener {

        }
        // 별점순 누르면
        binding.btnSortdiary.setOnClickListener {
            if (binding.btnSortdiary.text == "별점순") {
                originalList = diaryList
                diaryList.sortByDescending { diary -> diary.rest_star }
                binding.btnSortdiary.text = "날짜순"
            }
            else {
                diaryList = originalList
                binding.btnSortdiary.text = "별점순"
            }
            diaryadapter.notifyDataSetChanged()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    inner class HashtagViewHolder(val binding: ShowhasgtagListBinding):RecyclerView.ViewHolder(binding.root)
    inner class HashtagAdapter(val hashList: ArrayList<String>): RecyclerView.Adapter<HashtagViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagViewHolder
                = HashtagViewHolder(ShowhasgtagListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun onBindViewHolder(holder: HashtagViewHolder, position: Int) {
            val hashbinding = holder.binding

            hashbinding.autotextview.setText(hashList[position])
        }

        override fun getItemCount(): Int = hashList.size
    }


    inner class DiaryAdapter(val diaryList: java.util.ArrayList<Diary>): RecyclerView.Adapter<DiaryAdapter.RestViewHolder>() {
        inner class RestViewHolder(val binding: DiaryListBinding): RecyclerView.ViewHolder(binding.root) {
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestViewHolder
                = RestViewHolder(DiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun getItemCount(): Int = diaryList.size
        override fun onBindViewHolder(holder: RestViewHolder, position: Int) {
            val binding = holder.binding

            binding.tvDiaryDate.text = diaryList[position].date
            binding.tvDiaryComment.text = diaryList[position].rest_comment
            binding.diartRatingBar.rating = diaryList[position].rest_star

            binding.ivDiarydelete.setOnClickListener {
                db.deleteDiary(rest_name!!, diaryList[position].date)
                diaryList.removeAt(position)
                notifyItemRemoved(position)
                if (diaryList.isEmpty()) {
                    onBackPressed()
                    MainActivity.adapter?.notifyDataSetChanged()
                    ShowAllActivity.adapter?.notifyDataSetChanged()
                }
            }
        }
    }
}