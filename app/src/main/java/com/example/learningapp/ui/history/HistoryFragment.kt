package com.example.learningapp.ui.history

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.learningapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {

    private lateinit var historyContainer: LinearLayout
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_history, container, false)
        historyContainer = v.findViewById(R.id.historyContainer)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadHistory()
    }

    private fun loadHistory() {
        val shared = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = shared.getString("USERNAME", "")?.trim() ?: ""

        Log.d("HistoryFragment", "Đang tải lịch sử cho user: [$username]")

        if (username.isEmpty()) {
            Toast.makeText(requireContext(), "Chưa có username", Toast.LENGTH_SHORT).show()
            addLine("⚠️ Chưa đăng nhập nên không thể xem lịch sử", gray = true)
            return
        }

        db.collection("results")
            .whereEqualTo("username", username)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snap ->
                if (snap.isEmpty) {
                    addLine("📭 Không có lịch sử nào.", gray = true)
                    return@addOnSuccessListener
                }

                for (doc in snap) {
                    val category  = doc.getString("category") ?: "Không rõ chủ đề"
                    val score     = doc.getLong("score") ?: 0
                    val total     = doc.getLong("total") ?: 0
                    val timestamp = doc.getLong("timestamp") ?: 0

                    val dateStr = try {
                        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(timestamp))
                    } catch (e: Exception) {
                        "Không rõ thời gian"
                    }

                    addLine("📝 $category\nĐiểm: $score/$total • 🕓 $dateStr")
                }
            }
            .addOnFailureListener { e ->
                Log.e("HistoryFragment", "Lỗi tải dữ liệu lịch sử", e)
                Toast.makeText(requireContext(), "Lỗi tải lịch sử: ${e.message}", Toast.LENGTH_LONG).show()
                addLine("❌ Lỗi tải dữ liệu lịch sử", gray = true)
            }
    }

    private fun addLine(content: String, gray: Boolean = false) {
        val tv = TextView(requireContext()).apply {
            text = content
            textSize = 18f
            setPadding(0, 16, 0, 16)
            setTextColor(if (gray) Color.GRAY else Color.BLACK)
        }
        historyContainer.addView(tv)
    }
}
