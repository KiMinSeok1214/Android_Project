package com.example.project_last

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.project_last.databinding.ActivityAddDiaryBinding
import java.util.Random

class AddDiaryActivity : BaseActivity() {

    val binding by lazy { ActivityAddDiaryBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 저장 버튼을 클릭하면 view에 있는 text들을 저장하고 db에 넣는다.
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
    }
}