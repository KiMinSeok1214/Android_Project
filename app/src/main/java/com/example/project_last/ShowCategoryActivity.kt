package com.example.project_last

import android.os.Bundle
import android.util.Log
import com.example.project_last.databinding.ActivityTestCategoryBinding

class TestCategoryActivity:BaseActivity() {
    val binding by lazy{ ActivityTestCategoryBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertCago()
        deleteCago()
        getAllCago()
        renameCategory()
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
            edRcategory1.setText("")
            edRcategory2.setText("")
            edRcategory3.setText("")
            edRcategory4.setText("")
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

    private fun renameCategory() {
        binding.btnRename.setOnClickListener{
            try {
                val oldlist = ArrayList<String>()
                oldlist.add(binding.edCategory1.text.toString().trim())
                oldlist.add(binding.edCategory2.text.toString().trim())
                oldlist.add(binding.edCategory3.text.toString().trim())
                oldlist.add(binding.edCategory4.text.toString().trim())
                val newlist = ArrayList<String>()
                newlist.add(binding.edRcategory1.text.toString().trim())
                newlist.add(binding.edRcategory2.text.toString().trim())
                newlist.add(binding.edRcategory3.text.toString().trim())
                newlist.add(binding.edRcategory4.text.toString().trim())

                db.renameCategory(oldlist, newlist)
                binding.tvResult.setText("")
                showTxt("RENAME\n ${binding.edCategory1.text.toString().trim()} -> ${binding.edRcategory1.text.toString().trim()}\n")
                showTxt("${binding.edCategory2.text.toString().trim()} -> ${binding.edRcategory2.text.toString().trim()}\n")
                showTxt("${binding.edCategory3.text.toString().trim()} -> ${binding.edRcategory3.text.toString().trim()}\n")
                showTxt("${binding.edCategory4.text.toString().trim()} -> ${binding.edRcategory4.text.toString().trim()}")
                clearEditTexts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}