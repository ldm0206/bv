package dev.aaa1115910.bv.entity

import android.content.Context
import dev.aaa1115910.bv.R
import dev.aaa1115910.bv.util.stringRes

enum class ThemeType(private val strRes: Int) {
    Auto(R.string.theme_type_auto),
    Dark(R.string.theme_type_dark),
    Light(R.string.theme_type_light);

    fun getDisplayName(context: Context): String = strRes.stringRes(context)
}