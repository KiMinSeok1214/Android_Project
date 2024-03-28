package com.example.project_last

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_last.databinding.ActivityCombineHashtagBinding
import com.example.project_last.databinding.HashtagCombineListBinding
import java.util.ArrayList

class CombineHashtag : BaseActivity() {
    private val binding by lazy { ActivityCombineHashtagBinding.inflate(layoutInflater) }
    private lateinit var adapter: CombineHashtag.HashtagCombineAdapter
    private lateinit var hashtagList: ArrayList<Item>
    var mode = 0
    var selectpos1:Int? = null
    var selectpos2:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // db로 부터 데이터를 가져온다.
        hashtagList = db.getAllHashtag()
        Log.d("parklog", "${hashtagList[0]}")
        // 데이터를 recycler view에 뿌린다.
        val listAdapter = HashtagCombineAdapter(hashtagList)
        adapter = listAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    inner class HashtagCombineViewHolder(val binding: HashtagCombineListBinding): RecyclerView.ViewHolder(binding.root) {}
    inner class HashtagCombineAdapter(private val hashtagList: ArrayList<Item>): RecyclerView.Adapter<HashtagCombineViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CombineHashtag.HashtagCombineViewHolder {
            val binding = HashtagCombineListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HashtagCombineViewHolder(binding)
        }

        override fun onBindViewHolder(holder: HashtagCombineViewHolder, position: Int) {
            val binding2 = holder.binding

            binding2.tvHashtagname.text = "#" + hashtagList[position].hashtag

            binding2.tvHashtagname.setOnClickListener {
                if (mode == 0) {
                    selectpos1 = position
                    binding.txPos1.setText(hashtagList[position].hashtag)
                    mode++
                    adapter.notifyDataSetChanged()
                    Log.d("parklog", "${hashtagList[position].hashtag}")
                }
                else if (mode == 1) {
                    if (selectpos1 != position) {
                        selectpos2 = position
                        binding.txPos2.setText(hashtagList[position].hashtag)
                        mode++
                        adapter.notifyDataSetChanged()
                        Log.d("parklog", "${hashtagList[position].hashtag}")
                    }
                }
            }
            if (mode == 2) {
                binding.hashReset.setOnClickListener {
                    selectpos1 = null
                    selectpos2 = null
                    mode = 0
                    binding.txPos1.setText("")
                    binding.txPos2.setText("")
                    Log.d("parklog", "reset")
                    adapter.notifyDataSetChanged()
                }

                binding.hashCombine.setOnClickListener {
                    Log.d("Combine", "${hashtagList[selectpos1!!].hashtag.trim()} and ${hashtagList[selectpos2!!].hashtag.trim()}")
                    db.combineH1ToH2(hashtagList[selectpos1!!].hashtag.trim(), hashtagList[selectpos2!!].hashtag.trim())
                    hashtagList.removeAt(selectpos1!!)
                    selectpos1 = null
                    selectpos2 = null
                    mode = 0
                    binding.txPos1.setText("")
                    binding.txPos2.setText("")
                    adapter.notifyDataSetChanged()
                }
            }
            if (selectpos1 == position) {
                binding2.tvHashtagname.setBackgroundResource(R.drawable.box_hashtagpos1)
            }
            else if (selectpos2 == position)
                binding2.tvHashtagname.setBackgroundResource(R.drawable.box_hashtagpos2)
            else
                binding2.tvHashtagname.setBackgroundResource(R.drawable.box_hashtag)
        }

        override fun getItemCount(): Int = hashtagList.size
    }
}