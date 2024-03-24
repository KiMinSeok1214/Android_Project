package com.example.project_last

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.project_last.databinding.ActivityShowRestBinding

class ShowRestActivity : BaseActivity() {
    val binding by lazy { ActivityShowRestBinding.inflate(layoutInflater) }
    var diaryList = ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rest_name = intent.getStringExtra("rest_name")
        binding.tvRestname.text = rest_name

        rest_name?.let {
            diaryList = db.getRestData(rest_name)
        }
        Log.d("ki", "${diaryList[0].rest_star}")
    }
}