package com.example.learningapp.chaptertopics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.learningapp.R

class TopicAdapter(private val context: Context, private val topicName: Array<String>) : BaseAdapter(){


    override fun getCount(): Int {
        return topicName.size
    }

    override fun getItem(position: Int): Any {
        return topicName[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.custome_topic_item_layout, parent, false)

        val textView = view.findViewById<TextView>(R.id.topic_text)
        textView.text = topicName[position]

        return view
    }
}
