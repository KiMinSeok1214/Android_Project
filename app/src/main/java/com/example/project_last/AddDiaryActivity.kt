package com.example.project_last

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import com.example.project_last.databinding.ActivityAddDiaryBinding
import java.util.Calendar
import java.util.Random


class AddDiaryActivity : BaseActivity() {
    var star_late:Float = 0.0F
    val binding by lazy { ActivityAddDiaryBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 저장 버튼을 클릭하면 view에 있는 text들을 저장하고 db에 넣는다.

        initActivity()
        binding.ivSave.setOnClickListener {
            val item = Item(
                0,
                binding.etRestName.text.toString(),
                0,
                0,
                Random().nextInt(5).toString(),
                Random().nextInt(5).toDouble(),
                binding.etRestComment.text.toString(),
                "image_uri",
                "address",
                "url",
                0,
                "치킨",
                2.5,
                "menu_uri",
                "soso",
                15000,
                "hasgtag",
                "",
                "",
                "",
                ""
            )
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            db.insertItem(item)
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
    }

    private fun initActivity() {
        val mcurrentTime = Calendar.getInstance()
        val year = mcurrentTime.get(Calendar.YEAR)
        val month = mcurrentTime.get(Calendar.MONTH)
        val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)
        binding.tvDate.text = "$year / ${month + 1} / $day"
    }
}