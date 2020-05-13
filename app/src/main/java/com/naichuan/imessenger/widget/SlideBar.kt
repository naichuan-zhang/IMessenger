package com.naichuan.imessenger.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.naichuan.imessenger.R
import org.jetbrains.anko.sp

class SlideBar(context: Context?, attrs: AttributeSet)
            : View(context, attrs) {

    var sectionHeight = 0
    private var y = 0

    private var baseLine = 0f
    private val paint = Paint()

    var onSectionChangeListener: OnSectionChangeListener? = null

    init {
        paint.apply {
            color = Color.BLACK
            textSize = sp(12).toFloat()
            textAlign = Paint.Align.CENTER
        }
    }

    companion object {
        private val SECTIONS = arrayOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P","Q", "R", "S", "T","U", "V", "W", "X", "Y", "Z"
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sectionHeight = h / SECTIONS.size
        val textHeight = paint.fontMetrics.descent - paint.fontMetrics.ascent
        baseLine = sectionHeight / 2 + (textHeight / 2 - paint.fontMetrics.descent)
    }

    override fun onDraw(canvas: Canvas) {
        val x = width / 2
        var textBaseLine = baseLine

        SECTIONS.forEach {
            canvas.drawText(it, x.toFloat(), textBaseLine, paint)
            textBaseLine += sectionHeight
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                setBackgroundResource(R.drawable.slidebar_bg)
                val index = getTouchIndex(event)
                val firstLetter = SECTIONS[index]
                onSectionChangeListener?.onSectionChanged(firstLetter)
            }
            MotionEvent.ACTION_UP -> {
                setBackgroundColor(Color.TRANSPARENT)
                onSectionChangeListener?.onSlideFinished()
            }
            MotionEvent.ACTION_MOVE -> {
                setBackgroundResource(R.drawable.slidebar_bg)
                val index = getTouchIndex(event)
                val firstLetter = SECTIONS[index]
                onSectionChangeListener?.onSectionChanged(firstLetter)
            }
        }
        return true
    }

    private fun getTouchIndex(event: MotionEvent): Int {
        var index = (event.y / sectionHeight).toInt()
        if (index < 0) index = 0
        else if (index >= SECTIONS.size) index = SECTIONS.size - 1
        return index
    }

    interface OnSectionChangeListener {
        fun onSectionChanged(firstLetter: String)
        fun onSlideFinished()
    }
}