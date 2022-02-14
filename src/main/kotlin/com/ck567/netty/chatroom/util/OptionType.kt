package com.ck567.netty.chatroom.util

enum class OptionType(
    val type: Short
) {
    HeartBeatsReq(1),
    ;

    companion object {
        fun getType(type:Short): OptionType{
            return when(type) {
                1 as Short -> {
                    HeartBeatsReq
                }
                else -> HeartBeatsReq
            }
        }
    }
}