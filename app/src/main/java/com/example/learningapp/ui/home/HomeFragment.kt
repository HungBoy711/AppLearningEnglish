package com.example.learningapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.learningapp.R
import com.example.learningapp.chaptertopics.TopicActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var heading1: CardView
    private lateinit var heading2: CardView
    private lateinit var heading3: CardView
    private lateinit var heading4: CardView
    private lateinit var heading5: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        heading1 = view.findViewById(R.id.heading1)
        heading2 = view.findViewById(R.id.heading2)
        heading3 = view.findViewById(R.id.heading3)

        heading1.setOnClickListener(this);
        heading2.setOnClickListener(this);
        heading3.setOnClickListener(this);

        return view
    }

    override fun onClick(view: View?) {
        val intent = Intent(context, TopicActivity::class.java)
        when (view?.id) {
            R.id.heading1 -> {
                intent.putExtra("chapterName", "heading1")
                startActivity(intent)
            }
            R.id.heading2 -> {
                intent.putExtra("chapterName", "heading2")
                startActivity(intent)
            }
            R.id.heading3 -> {
                intent.putExtra("chapterName", "heading3")
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
