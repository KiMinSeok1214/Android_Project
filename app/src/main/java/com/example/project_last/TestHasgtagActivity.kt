package com.example.project_last

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project_last.databinding.ActivityTestHasgtagBinding

class TestHasgtagActivity : BaseActivity() {

    private val binding: ActivityTestHasgtagBinding by lazy {
        ActivityTestHasgtagBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertDb()
        updateDb()
        deleteHashtag()
        getAllDb()
    }

    private fun showTxt(text: String) {
        with(binding) {
            tvResult.append(text + "\n")
        }
    }

    private fun clearEditTexts() {
        with(binding) {
            etHashtag.setText("")
            etRehashtag.setText("")
            etDeletehashtag.setText("")
        }
    }

    private fun insertDb() {
        binding.btnInsert.setOnClickListener {
            try {
                db.insertHashtag(
                    binding.etHashtag.text.toString().trim(),
                )
                binding.tvResult.setText("")
                showTxt("Data inserted -> ${binding.etHashtag.text.toString()}")
                clearEditTexts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateDb() {
        binding.btnRename.setOnClickListener {
            try {
                val before = binding.etHashtag.text.toString().trim()
                val after = binding.etRehashtag.text.toString().trim()
                db.renameHashtag(
                    before,
                    after
                )
                clearEditTexts()
                showTxt("RENAME $before -> $after")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteHashtag() {
        binding.btnDeletehastag.setOnClickListener {
            try {
                val deletehashtag = binding.etDeletehashtag.text.toString().trim()
                db.deleteHashtag(deletehashtag)
                clearEditTexts()
                binding.tvResult.setText("")
                showTxt("Hashtag -> $deletehashtag deleted")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getAllDb() {
        binding.btnAllhashtag.setOnClickListener {
            try {
                Log.d("ki", "check")
                val selectResult = db.getHashtag()
                binding.tvResult.setText("")
                clearEditTexts()
                var result:String = ""
                for (r in selectResult) {
                    result += r + "\n"
                }
                Log.d("ki", "${selectResult.size}")

                showTxt(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}