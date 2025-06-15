package com.example.learningapp.ui.courses

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.R


class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        val title = intent.getStringExtra("COURSE_TITLE") ?: ""
        val desc = intent.getStringExtra("COURSE_DESC") ?: ""
        val videoUrl = intent.getStringExtra("COURSE_VIDEO") ?: ""
        val benefits = intent.getSerializableExtra("COURSE_BENEFIT") as? HashMap<*, *>

        findViewById<TextView>(R.id.textTitle).text = title
        findViewById<TextView>(R.id.textDesc).text = desc

        val formatted = benefits?.entries?.joinToString("\n") {
            "• ${it.value}"
        } ?: "Không có thông tin lợi ích"
        findViewById<TextView>(R.id.textBenefits).text = formatted

        val videoId = Uri.parse(videoUrl).getQueryParameter("v")
        val webView = findViewById<WebView>(R.id.videoWeb)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.youtube.com/embed/$videoId")
    }
}
