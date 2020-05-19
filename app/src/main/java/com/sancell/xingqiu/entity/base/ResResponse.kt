package com.sancell.xingqiu.entity.base

data class ResResponse<out T>(val retCode: Int, val retMsg: String, val retData: T)