package com.anwesh.uiprojects.bibarcrossview

/**
 * Created by anweshmishra on 09/10/20.
 */

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

val colors : Array<Int> = arrayOf(
        "#F44336",
        "#4CAF50",
        "#03A9F4",
        "#FFC107",
        "#009688"
).map({Color.parseColor(it)}).toTypedArray()
val parts : Int = 2
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val barSizeFactor : Float = 12.6f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawBiBarCross(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val gap : Float = Math.min(w, h) / barSizeFactor
    save()
    translate(w / 2, h / 2)
    for (j in 0..1) {
        val xBar : Float = (w - gap) * j
        val yStart : Float = h * (1 - sf2) * j
        val yEnd : Float = h * sf1 * (1 - j) + h * j
        val lineYStart = h * j
        val lineYEnd = h * sf1 * (1 - j) + h * (1 - sf1) * j
        save()
        drawLine(gap, lineYStart, gap + (w - 2 * gap) * sf1, lineYEnd, paint)
        drawRect(RectF(xBar, yStart, xBar + gap, yEnd), paint)
        restore()
    }
    restore()
}

fun Canvas.drawBBCNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawBiBarCross(scale, w, h, paint)
}

class BiBarCrossView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {


        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
}