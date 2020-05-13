package com.naichuan.imessenger.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SlideBar(context: Context?, attrs: AttributeSet)
            : View(context, attrs) {

    var sectionHeight = 50.0
    var x = width / 2.0
    var y = 0.0

    companion object {
        private val SECTIONS = arrayOf(
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P","Q", "R",
            "S", "T","U", "V", "W", "X",
            "Y", "Z"
        )
    }

    override fun onDraw(canvas: Canvas?) {
        SECTIONS.forEach {
            y += sectionHeight
            val paint = Paint()
            canvas?.drawText(it, x.toFloat(), y.toFloat(), paint)
        }
    }
}