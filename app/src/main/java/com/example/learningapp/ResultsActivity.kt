package com.example.learningapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 0)
        val username = intent.getStringExtra("USERNAME") ?: "Người dùng"

        val textResult = findViewById<TextView>(R.id.text_result)
        val btnBack = findViewById<Button>(R.id.btn_back)

        textResult.text = "$username, Đạt $score / $total Câu!"

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // hoặc HomeActivity nếu có
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
