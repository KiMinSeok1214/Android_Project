package com.example.project_last


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import com.example.project_last.databinding.HashtagModifyListBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.util.ArrayList

class ModifyHashtag : BaseActivity() {
    private val binding by lazy { ActivityModifyHashtagBinding.inflate(layoutInflater) }
    private lateinit var adapter: HashtagModifyAdapter
    private var add_mode:Boolean = false
    private lateinit var hashtagList: ArrayList<Item>
    private lateinit var slidePanel:SlidingUpPanelLayout
    var selectpos:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        slidePanel = binding.hashtagMainFrame // SlidingUpPanel

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        // db로 부터 데이터를 가져온다.
        hashtagList = db.getAllHashtag()

        // 데이터를 recycler view에 뿌린다.
        adapter = HashtagModifyAdapter(hashtagList)
        binding.modifyhashtagrecyclerView.layoutManager = LinearLayoutManager(this)
        binding.modifyhashtagrecyclerView.adapter = adapter

        delete()
        addmode()
        addmodetoggle()
        renamemode()
        rename()
    }

    fun showpanel() {
        val state = slidePanel.panelState

        if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }
    }

    private fun addmodetoggle() {
        binding.hashAdd.setOnClickListener {
            add_mode = !add_mode
            if (add_mode) {
                binding.renamehashLayout.visibility = View.INVISIBLE
                binding.addhashLayout.visibility = View.VISIBLE
                binding.hashAdd.setText("취소")
            }
            else {
                binding.renamehashLayout.visibility = View.INVISIBLE
                binding.addhashLayout.visibility = View.INVISIBLE
                binding.hashAdd.setText("추가")
            }
        }
    }

    private fun addmode() {
        binding.btnHashSave.setOnClickListener {
            try {
                val newHashtag = binding.etAddhash.text.toString().trim()
                if (newHashtag.isNotEmpty() && !db.checkHashtagExist(newHashtag)) {
                    hashtagList.add(Item(hashtag = newHashtag))
                    db.insertHashtag(newHashtag)
                    binding.etAddhash.setText("")
                    add_mode = false
                    binding.addhashLayout.visibility = View.INVISIBLE
                    binding.hashAdd.setText("추가")
                    adapter.notifyDataSetChanged()
                }
                else {
                    Toast.makeText(this, "존재하는 해시태그입니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun delete() {
        // recycler view 와 db에서 모두 제거
        binding.tvDelete.setOnClickListener {
            db.deleteHashtag(hashtagList[selectpos!!].hashtag)
            hashtagList.removeAt(selectpos!!)
            selectpos = null
            slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            binding.renamehashLayout.visibility = View.INVISIBLE
            binding.hashAdd.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
        }
    }

    private fun renamemode() {
        binding.tvEdit.setOnClickListener {
            binding.etRenamehash.setText(hashtagList[selectpos!!].hashtag)
            binding.renamehashLayout.visibility = View.VISIBLE
            binding.hashAdd.visibility = View.INVISIBLE
            binding.addhashLayout.visibility = View.INVISIBLE
            slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
        binding.btnBack.setOnClickListener {
            binding.renamehashLayout.visibility = View.INVISIBLE
            binding.hashAdd.visibility = View.VISIBLE
            binding.addhashLayout.visibility = View.INVISIBLE
            selectpos = null
        }
    }

    private fun rename() {
        binding.btnHashRename.setOnClickListener {
            try {
                val newHashtag = binding.etRenamehash.text.toString().trim()
                if (newHashtag.isNotEmpty() && !db.checkHashtagExist(newHashtag)) {
                    db.renameHashtag(hashtagList[selectpos!!].hashtag, newHashtag)
                    hashtagList[selectpos!!].hashtag = newHashtag
                    binding.etRenamehash.setText("")
                    selectpos = null
                    add_mode = false
                    binding.hashAdd.setText("추가")
                    binding.etAddhash.setText("")
                    binding.renamehashLayout.visibility = View.INVISIBLE
                    binding.addhashLayout.visibility = View.INVISIBLE
                    binding.hashAdd.visibility = View.VISIBLE
                    adapter.notifyDataSetChanged()
                }
                else {
                    Toast.makeText(this, "존재하는 해시태그입니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show()
                    binding.etRenamehash.setText(hashtagList[selectpos!!].hashtag)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    inner class HashtagModifyViewHolder(val binding: HashtagModifyListBinding): RecyclerView.ViewHolder(binding.root) {}
    inner class HashtagModifyAdapter(private val hashtagList: ArrayList<Item>) : RecyclerView.Adapter<HashtagModifyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagModifyViewHolder {
            val binding = HashtagModifyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HashtagModifyViewHolder(binding)
        }


        override fun onBindViewHolder(holder: HashtagModifyViewHolder, position: Int) {
            val binding = holder.binding
            binding.tvHashtagname.text = "#" + hashtagList[position].hashtag

            binding.ivMore.setOnClickListener {
                selectpos = position
                showpanel()
            }
        }
        override fun getItemCount(): Int = hashtagList.size
    }
}