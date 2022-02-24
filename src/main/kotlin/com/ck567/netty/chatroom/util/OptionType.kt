package com.ck567.netty.chatroom.util

import com.ck567.netty.chatroom.message.HeartBeatRequestMessage
import com.ck567.netty.chatroom.message.LoginRequestMessage

enum class OptionType(
    val type: Short
) {
    LoginReq(1),
    LoginRes(2),
    LoginReq2(3),
    HeartBeatReq(1793),
    ;

    companion object {

        fun getType(type:Short): Any {
            return when(type) {
                LoginReq.type -> {
                    LoginRequestMessage.serializer()
                }
                LoginRes.type -> LoginRequestMessage.serializer()
                LoginReq2.type -> LoginRequestMessage.serializer()
                HeartBeatReq.type -> HeartBeatRequestMessage.serializer()
                else -> {
                    HeartBeatRequestMessage.serializer()
                }
            }
        }
    }
}
