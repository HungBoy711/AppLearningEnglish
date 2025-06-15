package com.example.learningapp.ui.courses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.learningapp.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

// ──────────────── data class NGAY TRONG FILE ───────────────
data class Course(
    val title: String = "",
    val description: String = "",
    val videoUrl: String = "",
    val Beneficial: Map<String, String> = emptyMap()
)

// ──────────────── FRAGMENT ───────────────
class CoursesFragment : Fragment() {

    private lateinit var container: LinearLayout
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_courses, container, false)
        this.container = v.findViewById(R.id.container)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCourses()
    }

    private fun loadCourses() {
        db.collection("courses")
            .get()
            .addOnSuccessListener { snap ->
                for (doc in snap) {
                    val course = doc.toObject(Course::class.java)
                    addCourseView(course)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Lỗi tải khóa học", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addCourseView(course: Course) {
        val view = layoutInflater.inflate(R.layout.item_courses, container, false)
        view.findViewById<TextView>(R.id.textTitle).text = course.title
        view.findViewById<TextView>(R.id.textDesc).text = course.description

        view.setOnClickListener {
            val intent = Intent(requireContext(), CourseDetailActivity::class.java).apply {
                putExtra("COURSE_TITLE", course.title)
                putExtra("COURSE_DESC", course.description)
                putExtra("COURSE_VIDEO", course.videoUrl)
                putExtra("COURSE_BENEFIT", HashMap(course.Beneficial))
            }
            startActivity(intent)
        }

        container.addView(view)
    }
}
