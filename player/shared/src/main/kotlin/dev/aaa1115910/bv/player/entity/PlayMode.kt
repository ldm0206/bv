package dev.aaa1115910.bv.player.entity

import android.content.Context
import dev.aaa1115910.bv.player.shared.R

enum class PlayMode(private val strRes: Int) {
    //单集播放
    Single(R.string.play_mode_single),

    //顺序播放
    Sequential(R.string.play_mode_sequential),

    //单集循环
    SingleLoop(R.string.play_mode_single_loop),

    //列表循环
    ListLoop(R.string.play_mode_sequential_loop);

    fun getDisplayName(context: Context) = context.getString(strRes)
}