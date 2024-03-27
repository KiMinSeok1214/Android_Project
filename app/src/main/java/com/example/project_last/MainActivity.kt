package com.example.project_last

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ActivityMainBinding
import com.example.project_last.databinding.HomeListBinding
import com.example.project_last.databinding.MenuListBinding

class MainActivity : BaseActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActivity()
        var restList = db.getAllRestaurent()
        // 데이터를 recycler view에 뿌린다.
        val listAdapter = HomeAdapter(restList)
        adapter = listAdapter
        binding.carouselRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.carouselRecyclerview.adapter = adapter
        binding.carouselRecyclerview.apply {
            set3DItem(true)
            setAlpha(true)
        }
    }

    private fun initActivity() {
        // 검색
        binding.ivSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.ivSearch2.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        //모든 일기보기
        binding.tvShowall.setOnClickListener {
            val intent = Intent(this, ShowAllActivity::class.java)
            startActivity(intent)
        }
        binding.ivShowAll.setOnClickListener {
            val intent = Intent(this, ShowAllActivity::class.java)
            startActivity(intent)
        }
        // 카테고리 설정
        binding.setCago.setOnClickListener {

        }
        binding.ivCago.setOnClickListener {

        }
        // 필터 적용
        binding.adaptFilter.setOnClickListener {

        }
        binding.ivFilter.setOnClickListener {

        }

    }
}

class HomeHolder(val binding: HomeListBinding) : RecyclerView.ViewHolder(binding.root)
class HomeAdapter(val restList:ArrayList<Restaurent>) : RecyclerView.Adapter<HomeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder
            = HomeHolder(HomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val binding = holder.binding

        binding.tvHomecomment.text = restList[position].comment
        binding.tvHomeRestname.text = restList[position].rest_name
        // image는 나중에 고려
        binding.homelayout.setOnClickListener {
            val intent = Intent(binding.root.context, ShowRestActivity::class.java)
            intent.putExtra("rest_name", restList[position].rest_name)
            binding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = restList.size
}