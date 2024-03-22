package com.example.project_last

import android.os.Bundle
import android.util.Log
import com.example.project_last.databinding.ActivityShowCategoryBinding

class ShowCategoryActivity:BaseActivity() {
    val binding by lazy{ ActivityShowCategoryBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertCago()
        deleteCago()
        getAllCago()
    }

    private fun showTxt(text: String) {
        with(binding) {
            tvResult.append(text + "\n")
        }
    }

    private fun clearEditTexts() {
        with(binding) {
            edCategory1.setText("")
            edCategory2.setText("")
            edCategory3.setText("")
            edCategory4.setText("")
        }
    }
    private fun insertCago(){
        binding.btnAddcago.setOnClickListener{
            try {
                val list = ArrayList<String>()
                list.add(binding.edCategory1.text.toString().trim())
                list.add(binding.edCategory2.text.toString().trim())
                list.add(binding.edCategory3.text.toString().trim())
                list.add(binding.edCategory4.text.toString().trim())
                db.insertCategory(list  )
                binding.tvResult.setText("")
                showTxt("Data inserted -> ${binding.edCategory1.text.toString().trim()} ")
                showTxt("${binding.edCategory2.text.toString().trim()} ")
                showTxt("${binding.edCategory3.text.toString().trim()} ")
                showTxt("${binding.edCategory4.text.toString().trim()}")
                clearEditTexts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteCago() {
        binding.btnDeletecago.setOnClickListener {
            try {
                val deletecategorylist = ArrayList<String>()
                deletecategorylist.add(binding.edCategory1.text.toString().trim())
                deletecategorylist.add(binding.edCategory2.text.toString().trim())
                deletecategorylist.add(binding.edCategory3.text.toString().trim())
                deletecategorylist.add(binding.edCategory4.text.toString().trim())
                db.deleteCategory(deletecategorylist)
                binding.tvResult.setText("")
                showTxt("Data delete -> ${binding.edCategory1.text.toString().trim()}")
                showTxt("${binding.edCategory2.text.toString().trim()}")
                showTxt("${binding.edCategory3.text.toString().trim()}")
                showTxt("${binding.edCategory4.text.toString().trim()}")
                clearEditTexts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getAllCago() {
        binding.btnAllcago.setOnClickListener {
            try {
                Log.d("ki", "check")
                val selectResult = db.getCategory()
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