package com.example.project_last


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_last.databinding.ActivityShowAllBinding

class ShowAllActivity : BaseActivity() {
    val binding by lazy { ActivityShowAllBinding.inflate(layoutInflater) }
    lateinit var adapter: ItemAdapter
    var select_mode:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // db로 부터 데이터를 가져온다.
        var itemList = db.getAllData()
        // 데이터를 recycler view에 뿌린다.
        val listAdapter = ItemAdapter(itemList)
        adapter = listAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // 터치 하면 그 하부로 들어감
        listAdapter.setItemClickListener(object: ItemAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                // 클릭 시 가게 이름을 ShowRestActivity에 던짐
                // button visible 체크하자!
                val intent = Intent(v.context, ShowRestActivity::class.java)
                intent.putExtra("rest_name", itemList[position].rest_name)
                startActivity(intent)
            }
        })
        // 선택 버튼을 눌렀을 때
        selectMode(itemList)
        delete(itemList)
        sortItem(itemList)
        loveItem(itemList)
    }

    private fun loveItem(itemList: ArrayList<Item>) {
        binding.btnLove.setOnClickListener {
            // 선택된 음식점들의 isfavor을 1로 업데이트
            var restList = ArrayList<String>()

            for (item in itemList) {
                if (item.selected)
                    restList.add(item.rest_name)
            }
            // restname을 전달
            db.setrestLove(restList)
        }
    }

    private fun sortItem(itemList: ArrayList<Item>) {
        binding.btnSort.setOnClickListener {
            if (binding.btnSort.text == "별점순") {
                itemList.sortByDescending { item -> item.rest_star }
                binding.btnSort.text = "날짜순"
            }
            else {
                itemList.sortBy { item -> item.date }
                binding.btnSort.text = "별점순"
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun delete(itemList: ArrayList<Item>) {
        // recycler view 와 db에서 모두 제거
        binding.btnDelete.setOnClickListener {
            var restList = ArrayList<String>()
            for ((idx, item) in itemList.withIndex().reversed()) {
                if (item.selected)
                {
                    itemList.removeAt(idx)
                    restList.add(item.rest_name)
                }
            }
            // db에 제거할 restname 전달
            db.deleteItem(restList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun selectMode(itemList: ArrayList<Item>) {
        binding.ivSelect.setOnClickListener {
            select_mode = !select_mode

            if (select_mode)
            {
                adapter.mode = "select"
                binding.btnDelete.visibility = View.VISIBLE
                binding.btnLove.visibility = View.VISIBLE
            }
            else {
                adapter.mode = "normal"
                binding.btnDelete.visibility = View.INVISIBLE
                for (item in itemList)
                    item.selected = false
                adapter.notifyDataSetChanged()
            }
        }
    }
}