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
