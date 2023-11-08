package com.woojun.knowledge_query.util

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val space: Space, private val type: BookType) : RecyclerView.ItemDecoration() {

    init {
        space.top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space.top.toFloat(), Resources.getSystem().displayMetrics).toInt()
        space.left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space.left.toFloat(), Resources.getSystem().displayMetrics).toInt()
        space.right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space.right.toFloat(), Resources.getSystem().displayMetrics).toInt()
        space.bottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space.bottom.toFloat(), Resources.getSystem().displayMetrics).toInt()
        space.firstTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space.firstTop.toFloat(), Resources.getSystem().displayMetrics).toInt()
        space.firstLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space.firstLeft.toFloat(), Resources.getSystem().displayMetrics).toInt()
        space.lastRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space.lastRight.toFloat(), Resources.getSystem().displayMetrics).toInt()
        space.lastBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space.lastBottom.toFloat(), Resources.getSystem().displayMetrics).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (type == BookType.My) {
                when (parent.getChildAdapterPosition(view)) {
                    0 -> {
                        top = space.firstTop
                        left = space.left
                        right = space.right
                        bottom = space.bottom
                    }
                    parent.adapter!!.itemCount - 1 -> {
                        top = space.top
                        left = space.left
                        right = space.right
                        bottom = space.lastBottom
                    }
                    else -> {
                        top = space.top
                        left = space.left
                        right = space.right
                        bottom = space.bottom
                    }
                }
            } else if (type == BookType.Category) {
                when (parent.getChildAdapterPosition(view)) {
                    0 -> {
                        top = space.top
                        left = space.firstLeft
                        right = space.right
                        bottom = space.bottom
                    }
                    parent.adapter!!.itemCount - 1 -> {
                        top = space.top
                        left =  space.left
                        right = space.lastRight
                        bottom = space.bottom
                    }
                    else -> {
                        top = space.top
                        left =  space.left
                        right = space.right
                        bottom = space.bottom
                    }
                }
            } else {
                if ((parent.getChildAdapterPosition(view)+1) % 2 != 0) {
                    top = space.top
                    left =  space.firstLeft
                    right = space.right
                    bottom = space.bottom
                } else {
                    top = space.top
                    left =  space.left
                    right = space.lastRight
                    bottom = space.bottom
                }
            }
        }
    }
}
