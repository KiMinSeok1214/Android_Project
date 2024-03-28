package com.example.project_last


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ActivityShowAllBinding
import com.example.project_last.databinding.RestListBinding

class ShowAllActivity : BaseActivity() {
    val binding by lazy { ActivityShowAllBinding.inflate(layoutInflater) }
    companion object {
        var adapter: RestAdapter? = null
    }
    lateinit var restList:ArrayList<Restaurent>
    lateinit var originalList: ArrayList<Restaurent>
    var select_mode:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActivity()
        // db로 부터 데이터를 가져온다.
        val keyword = intent.getStringExtra("KEYWORD") ?: ""
        val preactivity = intent.getStringExtra("PREACTIVITY") ?: ""
        if (preactivity == "search")
            restList = db.getKeywordData(keyword)
        else if (preactivity == "hashtag")
            restList = db.getHASHREST(keyword)
        else if (preactivity == "home")
            restList = db.getAllRestaurent()

        // 데이터를 recycler view에 뿌린다.
        val listAdapter = RestAdapter(restList)
        adapter = listAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
        sortRest()
        changeType()
        selectMode()
        deleteRest()
    }

    private fun deleteRest() {
        binding.btnDelete.setOnClickListener {
            var removeRestList = ArrayList<String>()
            for ((idx, rest) in restList.withIndex().reversed()) {
                if (rest.selected)
                {
                    restList.removeAt(idx)
                    removeRestList.add(rest.rest_name)
                }
            }
            // db에 제거할 restname 전달
            db.deleteRest(removeRestList)
            adapter?.notifyDataSetChanged()
            MainActivity.adapter?.notifyDataSetChanged()
        }
    }

    private fun changeType() {
        binding.btnType.setOnClickListener {
            originalList = restList
            when (binding.btnType.text) {
                "배달 + 방문" -> {
                    binding.btnType.text = "배달"
                    val tmpList = ArrayList<Restaurent>()
                    for (rest in restList) {
                        if (rest.isdelivery == 1 && rest.isvisit == 0)
                            tmpList.add(rest)
                    }
                    restList = tmpList
                    adapter?.notifyDataSetChanged()
                }
                "배달" -> {
                    binding.btnType.text = "방문"
                    val tmpList = ArrayList<Restaurent>()
                    for (rest in restList)
                        if (rest.isdelivery == 0 && rest.isvisit == 1)
                            tmpList.add(rest)
                    restList = tmpList
                    adapter?.notifyDataSetChanged()
                }
                "방문" -> {
                    binding.btnType.text = "배달 + 방문"
                    restList = originalList
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun sortRest() {
        binding.btnSort.setOnClickListener {
            if (binding.btnSort.text == "별점순") {
                originalList = restList
                restList.sortByDescending { rest -> rest.recent_star }
                binding.btnSort.text = "날짜순"
            }
            else {
                restList = originalList
                binding.btnSort.text = "별점순"
            }
            adapter?.notifyDataSetChanged()
        }
    }

    private fun initActivity() {
        setSupportActionBar(binding.showAllToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("일기")
        binding.ivShowallSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }
    private fun selectMode() {
        binding.ivSelected.setOnClickListener {
            select_mode = !select_mode

            if (select_mode)
            {
                adapter?.mode = "select"
                binding.btnDelete.visibility = View.VISIBLE
            }
            else {
                adapter?.mode = "normal"
                binding.btnDelete.visibility = View.INVISIBLE
                for (item in restList)
                    item.selected = false
                adapter?.notifyDataSetChanged()
            }
        }
    }
    inner class RestAdapter(val restList: ArrayList<Restaurent>): RecyclerView.Adapter<RestAdapter.RestViewHolder>() {
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
                if (restList[position].isfavor == 1)
                    binding.ivStar.setImageResource(android.R.drawable.star_big_on)
                else
                    binding.ivStar.setImageResource(android.R.drawable.star_big_off)
            }
            binding.restLayout.setOnClickListener {
                if (select_mode == false) {
                    val intent = Intent(binding.root.context, ShowRestActivity::class.java)
                    intent.putExtra("rest_name", restList[position].rest_name)
                    binding.root.context.startActivity(intent)
                }
                else {
                    setMultipleSelection(position)
                }
            }
            binding.ivStar.setOnClickListener {
                if (restList[position].isfavor == 1) {
                    restList[position].isfavor = 0
                    binding.ivStar.setImageResource(android.R.drawable.star_big_off)
                    // db에 없데이트
                    db.loveRest(restList[position].rest_name, 0)
                }
                else {
                    restList[position].isfavor = 1
                    binding.ivStar.setImageResource(android.R.drawable.star_big_on)
                    // db에 업데이트
                    db.loveRest(restList[position].rest_name, 1)
                }
            }
            if (restList[position].selected)
                binding.layout.setBackgroundColor(Color.parseColor("#90caf9"))
            else
                binding.layout.setBackgroundColor(Color.parseColor("#ffffff"))
        }
        override fun getItemCount(): Int = restList.size

        fun setMultipleSelection(position: Int) {
            if (mode != "normal") {
                restList[position].selected = !restList[position].selected
                notifyDataSetChanged()
            }
        }
    }
}