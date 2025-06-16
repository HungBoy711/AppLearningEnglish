package com.example.learningapp.chaptertopics

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.learningapp.R
import com.example.learningapp.chapter.one.SentenceStructure
import com.example.learningapp.chapter.one.Tenses
import com.example.learningapp.chapter.two.Greetings
import com.example.learningapp.chapter.two.Interviews

class TopicActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var gridView: ExpandableHeightGridView
    private var chapterName: String? = null
    private lateinit var adapter: TopicAdapter
    private var arr: Array<String>? = null
    private lateinit var chapter_Image: ImageView

    private val chapter1 = arrayOf("SentenceStruc...", "Tenses")
    private val chapter2 = arrayOf("Greetings", "Interviews")
    private val chapter3 = arrayOf("Question 3", "Sub Heading 3", "Question 3", "Question 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        toolbar = findViewById(R.id.toolbar)
        gridView = findViewById(R.id.topics_name)
        gridView.isExpanded = true

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        chapterName = intent.getStringExtra("chapterName")
        chapter_Image = findViewById(R.id.chapter_image)

        compareAndOpen()
    }

    private fun compareAndOpen() {
        arr = when (chapterName) {
            "heading1" -> {
                chapter_Image.setImageResource(R.drawable.grammar)
                supportActionBar?.title = "Grammar"
                chapter1
            }
            "heading2" -> {
                chapter_Image.setImageResource(R.drawable.conversation)
                supportActionBar?.title = "Conversation"
                chapter2
            }
            "heading3" -> {
                chapter_Image.setImageResource(R.drawable.verb)
                supportActionBar?.title = "Verb"
                chapter3
            }
            else -> null
        }

        arr?.let {
            adapter = TopicAdapter(this, it)
            gridView.adapter = adapter

            gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
                openActivity(it[i])
            }
        } ?: run {
            Toast.makeText(this, "No topics found for this chapter", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openActivity(topic: String) {
        when (topic) {
            "SentenceStruc..." -> {
                val intent = Intent(this, SentenceStructure::class.java)
                startActivity(intent)
            }
            "Tenses" -> {
                val intent = Intent(this, Tenses::class.java)
                startActivity(intent)
            }
            "Greetings" -> {
                val intent = Intent(this, Greetings::class.java)
                startActivity(intent)
            }
            "Interviews" -> {
                val intent = Intent(this, Interviews::class.java)
                startActivity(intent)
            }
            else -> {
                Toast.makeText(this, "No activity found for topic: $topic", Toast.LENGTH_SHORT).show()
            }
        }
    }
}