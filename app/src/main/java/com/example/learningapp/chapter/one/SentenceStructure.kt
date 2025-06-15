package com.example.learningapp.chapter.one

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.R
import com.example.learningapp.ResultsActivity
import com.google.firebase.firestore.FirebaseFirestore

data class QuizQuestion2(
    val question: String = "",
    val answer: String = "",
    val options: Map<String, String> = emptyMap(),
    val order: Long = 0
)

class SentenceStructure : AppCompatActivity() {

    private lateinit var questionView: TextView
    private lateinit var radioGroupOptions: RadioGroup
    private lateinit var nextButton: Button

    private lateinit var db: FirebaseFirestore
    private var questionList: List<QuizQuestion2> = emptyList()
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var username: String = "Người dùng"

    private val CATEGORY = "SentenceStructure"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_structure)

        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        username = sharedPref.getString("USERNAME", "Người dùng") ?: username

        questionView      = findViewById(R.id.text_question)
        radioGroupOptions = findViewById(R.id.radio_group_options)
        nextButton        = findViewById(R.id.btn_next)

        db = FirebaseFirestore.getInstance()

        loadQuestionsFromFirestore()

        nextButton.setOnClickListener { onNextClicked() }
    }

    // ---------------------- Firestore ----------------------
    private fun loadQuestionsFromFirestore() {
        db.collection("quizzes")
            .whereEqualTo("category", CATEGORY)
            .get()
            .addOnSuccessListener { snap ->
                questionList = snap.documents
                    .mapNotNull { it.toObject(QuizQuestion2::class.java) }
                    .sortedBy { it.order }

                if (questionList.isEmpty()) {
                    questionView.text = "Không có câu hỏi nào"
                } else {
                    currentQuestionIndex = 0
                    correctAnswersCount = 0
                    displayQuestion()
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Lỗi: ${e.message}", e)
                questionView.text = "Lỗi khi tải dữ liệu"
            }
    }

    // ---------------------- UI ----------------------
    private fun displayQuestion() {
        val q = questionList[currentQuestionIndex]
        questionView.text = "Câu ${currentQuestionIndex + 1}: ${q.question}"

        radioGroupOptions.removeAllViews()
        q.options.toSortedMap()      // sắp xếp theo a, b, c, d cho đẹp
            .forEach { (_, text) ->
                val rb = RadioButton(this).apply {
                    this.text = text
                    this.id   = View.generateViewId()
                }
                radioGroupOptions.addView(rb)
            }
        radioGroupOptions.clearCheck()
    }

    private fun onNextClicked() {
        val selectedId = radioGroupOptions.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, "Vui lòng chọn một đáp án", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedText  = findViewById<RadioButton>(selectedId).text.toString()
        val correctAnswer = questionList[currentQuestionIndex].answer
        if (selectedText == correctAnswer) correctAnswersCount++

        if (++currentQuestionIndex < questionList.size) {
            displayQuestion()
        } else {
            saveResultToFirestore()
        }
    }

    // ---------------------- Lưu kết quả ----------------------
    private fun saveResultToFirestore() {
        val result = hashMapOf(
            "username" to username,
            "score"    to correctAnswersCount,
            "total"    to questionList.size,
            "timestamp" to System.currentTimeMillis(),
            "category" to CATEGORY
        )

        db.collection("results")
            .add(result)
            .addOnSuccessListener {
                Log.d("Firestore", "Kết quả đã được lưu")
                val i = Intent(this, ResultsActivity::class.java).apply {
                    putExtra("SCORE", correctAnswersCount)
                    putExtra("TOTAL", questionList.size)
                    putExtra("USERNAME", username)
                }
                startActivity(i)
                finish()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Lỗi lưu kết quả: ${e.message}", e)
                Toast.makeText(this, "Lỗi khi lưu kết quả", Toast.LENGTH_SHORT).show()
            }
    }
}
