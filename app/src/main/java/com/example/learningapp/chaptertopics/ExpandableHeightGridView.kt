package com.example.learningapp.chaptertopics

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView
import android.view.ViewGroup

class ExpandableHeightGridView : GridView {
    var isExpanded: Boolean = false

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isExpanded) {
            val expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST)
            super.onMeasure(widthMeasureSpec, expandSpec)
            val params: ViewGroup.LayoutParams = layoutParams
            params.height = measuredHeight
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}
