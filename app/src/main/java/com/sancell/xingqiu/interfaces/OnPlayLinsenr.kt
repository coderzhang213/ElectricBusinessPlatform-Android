package com.sancell.xingqiu.interfaces

import com.sancell.xingqiu.enump.PLAY_STATE

interface OnPlayLinsenr {
    fun onBaseStart()
    fun onBaseStop()

    /**
     * 获取当前播放状态
     */
    fun onBasePlayStates(): PLAY_STATE
    fun onLiveEndPlay()
    fun onSetPlayStatus(isPlay: Boolean)
}