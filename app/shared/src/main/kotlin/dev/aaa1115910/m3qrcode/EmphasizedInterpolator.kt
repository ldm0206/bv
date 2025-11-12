package dev.aaa1115910.m3qrcode

import android.graphics.Path;
import android.view.animation.PathInterpolator;

object EmphasizedInterpolator {
    private val interpolator: PathInterpolator

    init {
        val path = Path().apply {
            moveTo(0.0f, 0.0f)
            cubicTo(0.05f, 0.0f, 0.133333f, 0.06f, 0.166666f, 0.4f)
            cubicTo(0.208333f, 0.82f, 0.25f, 1.0f, 1.0f, 1.0f)
        }
        interpolator = PathInterpolator(path)
    }

    fun getInterpolation(input: Float): Float = interpolator.getInterpolation(input)
}
