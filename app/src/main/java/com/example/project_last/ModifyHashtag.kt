package com.example.project_last


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_last.databinding.ActivityModifyHashtagBinding

class ModifyHashtag : BaseActivity() {
    val binding by lazy { ActivityModifyHashtagBinding.inflate(layoutInflater) }
    lateinit var adapter: HashtagModifyAdapter
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
        val listAdapter = HashtagModifyAdapter(hashtagList)
        adapter = listAdapter
        binding.modifyhashtagrecyclerView.layoutManager = LinearLayoutManager(this)
        binding.modifyhashtagrecyclerView.adapter = adapter

        // 선택 버튼을 눌렀을 때
        selectMode(hashtagList)
        delete(hashtagList)
        add()
    }

    private fun add() {
        binding.hashAdd.setOnClickListener {
            add_mode = !add_mode
            if (add_mode)
            {
                binding.addlayouthashtag.visibility = View.VISIBLE
                binding.hashAdd.setText("취소")
                binding.hashSelect.visibility = View.INVISIBLE
                binding.hashSave.setOnClickListener {
                    try {
                        if (!db.checkHashtagExist(binding.etAddhashtag.text.toString().trim())) {
                            hashtagList.add(Item(hashtag = binding.etAddhashtag.text.toString().trim()))
                            db.insertHashtag(
                                binding.etAddhashtag.text.toString().trim()
                            )
                        }
                        else {
                            Toast.makeText(this, "존재하는 해시태그입니다.", Toast.LENGTH_SHORT).show()
                        }
                        binding.etAddhashtag.setText("")
                        binding.hashAdd.setText("추가")
                        binding.hashAdd.visibility = View.VISIBLE
                        binding.addlayouthashtag.visibility = View.INVISIBLE
                        binding.hashSelect.visibility = View.VISIBLE
                        add_mode = !add_mode
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            else {
                binding.addlayouthashtag.visibility = View.INVISIBLE
                binding.hashSelect.visibility = View.VISIBLE
                binding.hashAdd.setText("추가")
                adapter.notifyDataSetChanged()
            }
        }
    }
    private fun delete(itemList: ArrayList<Item>) {
        // recycler view 와 db에서 모두 제거
        binding.hashDelete.setOnClickListener {
            var hashtagList = ArrayList<String>()
            for ((idx, item) in itemList.withIndex().reversed()) {
                if (item.selected)
                {
                    itemList.removeAt(idx)
                    db.deleteHashtag(item.hashtag)
                }
            }
            // db에 제거할 restname 전달

            adapter.notifyDataSetChanged()
        }
    }

    private fun selectMode(itemList: ArrayList<Item>) {
        binding.hashSelect.setOnClickListener {
            select_mode = !select_mode

            if (select_mode)
            {
                adapter.mode = "select"
                binding.hashDelete.visibility = View.VISIBLE
                binding.hashAdd.visibility = View.INVISIBLE
                binding.hashSelect.setText("완료")
                adapter.notifyDataSetChanged()
            }
            else {
                adapter.mode = "normal"
                binding.hashDelete.visibility = View.INVISIBLE
                binding.hashAdd.visibility = View.VISIBLE
                binding.hashSelect.setText("편집")
                for (item in itemList)
                    item.selected = false
                adapter.notifyDataSetChanged()
            }
        }
    }
}