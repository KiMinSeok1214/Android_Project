package com.example.project_last

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_last.databinding.ActivityAddDiaryBinding

import java.util.Calendar
import java.util.Random


class AddDiaryActivity : BaseActivity() {
    var star_late:Float = 0.0F
    val binding by lazy { ActivityAddDiaryBinding.inflate(layoutInflater) }
    val menuList = ArrayList<Item>()
    lateinit var adapter: MenuAdapter
    val defaultMenu = Item(
        rest_name = "",
        menu_name = "메뉴명을 입력해주세요.",
        menu_star = 0.0,
        menu_comment = "메뉴에 대한 코멘트를 작성해주세요.",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 저장 버튼을 클릭하면 view에 있는 text들을 저장하고 db에 넣는다.

        initActivity()

        binding.ivSave.setOnClickListener {
            val item = Item(
                rest_name = binding.etRestName.text.toString().trim(),
                rest_comment = binding.etRestComment.text.toString().trim(),
                date = binding.etDate.text.toString().trim()
                )
            db.insertItem(item)
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.tvDate.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val year = mcurrentTime.get(Calendar.YEAR)
            val month = mcurrentTime.get(Calendar.MONTH)
            val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    binding.tvDate.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth))
                }
            }, year, month, day);
            datePicker.show()
        }
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            star_late = rating
        }
        binding.tvAddMenu.setOnClickListener {
            menuList.add(0, defaultMenu)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initActivity() {
        // 처음 추가했을 경우
        val mcurrentTime = Calendar.getInstance()
        val year = mcurrentTime.get(Calendar.YEAR)
        val month = mcurrentTime.get(Calendar.MONTH)
        val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)
        binding.tvDate.text = "$year / ${month + 1} / $day"

        // menu list Recycler view adapter 연결
        menuList.add(defaultMenu)
        adapter = MenuAdapter(menuList)
        binding.menuList.layoutManager = LinearLayoutManager(this)
        binding.menuList.adapter = adapter

        binding.tvExtraInfo.setOnClickListener {
            if (binding.extraLayout.visibility == View.GONE) {
                binding.extraLayout.visibility = View.VISIBLE
            }
            else {
                binding.extraLayout.visibility = View.GONE
            }
        }
    }
}