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
import com.sothree.slidinguppanel.SlidingUpPanelLayout

import java.util.Calendar
import java.util.Random


class AddDiaryActivity : BaseActivity() {
    var star_late:Float = 0.0F
    val binding by lazy { ActivityAddDiaryBinding.inflate(layoutInflater) }
    val menuList = ArrayList<Item>()
    lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 저장 버튼을 클릭하면 view에 있는 text들을 저장하고 db에 넣는다.

        val slidePanel = binding.mainFrame                      // SlidingUpPanel
        initActivity()

        binding.fab.setOnClickListener {
            // 추가된 Menu 개수만큼 ItemList를 생성해서 db에 저장해야 함
            // 음식점 이름, 작성일, 음식점 별점, 음식점 코멘트는 고정됨

            // image uri, 카테고리 아직 고려 x
            val rest_name = binding.etRestName.text.toString().trim()
            val date = binding.tvDate.text.trim()
            val rest_star = binding.ratingBar.rating
            val rest_comment = binding.etRestComment.text.toString().trim()
            val menu_name = binding.etMenuName.text.toString().trim()
            val menu_star = binding.menuRatingbar.rating
            val menu_comment = binding.etMenuComment.text.toString().trim()
            val price = if (binding.etPrice.text.toString() != "") {
                binding.etPrice.text.toString().toInt()
            } else { 0 }

            menuList.add(Item(
                rest_name = rest_name,
                date = date.toString(),
                rest_star = rest_star.toDouble(),
                rest_comment = rest_comment,
                menu_name = menu_name,
                menu_star = menu_star.toDouble(),
                menu_comment = menu_comment,
                price = price
            ))
            ClearAddMenu()
            adapter.notifyDataSetChanged()
            slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        binding.ivDiarysave.setOnClickListener {
            if (!binding.etRestName.text.isEmpty()) {
                for (menu in menuList)
                    db.insertItem(menu)
            }
            else {
                Toast.makeText(this, "음식점 이름을 입력하세요.", Toast.LENGTH_SHORT)
                    .show()
            }
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
            val state = slidePanel.panelState

            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
            slidePanel.isTouchEnabled = false
        }
    }

    private fun ClearAddMenu() {
        binding.etMenuName.setText("")
        binding.menuRatingbar.rating = 0.0f
        binding.etMenuComment.setText("")
        binding.etPrice.setText("")
    }

    private fun initActivity() {
        // 처음 추가했을 경우
        val mcurrentTime = Calendar.getInstance()
        val year = mcurrentTime.get(Calendar.YEAR)
        val month = mcurrentTime.get(Calendar.MONTH)
        val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)
        binding.tvDate.text = "$year / ${month + 1} / $day"

        // menu list Recycler view adapter 연결
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