package com.ck567.netty.chatroom.util

enum class OptionType(
    val type: Short
) {
    HeartBeatsReq(1),
    LoginReq(2),
    LoginRes(3)
    ;


    companion object {
        const val short1: Short = 1
        const val short2: Short = 2
        const val short3: Short = 3
        fun getType(type:Short): OptionType{
            return when(type) {
                short1 -> {
                    HeartBeatsReq
                }
                short2 -> LoginReq
                short3 -> LoginRes
                else -> HeartBeatsReq
            }
        }
    }
}