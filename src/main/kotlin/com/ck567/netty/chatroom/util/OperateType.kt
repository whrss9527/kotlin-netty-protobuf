package com.ck567.netty.chatroom.util

import com.ck567.netty.chatroom.message.HeartBeatRequestMessage
import com.ck567.netty.chatroom.message.HeartBeatResponseMessage
import com.ck567.netty.chatroom.message.LoginRequestMessage

enum class OperateType(
    val type: Short
) {
    // 搞几个枚举放在这里
    LoginReq(1),
    LoginRes(2),
    LoginReq2(3),
    HeartBeatReq(1793),
    HeartBeatRes(1795),
    ;

    companion object {

        fun getSerializer(type:Short): Any {
            // 拿对应的序列化对象
            return when(type) {
                LoginReq.type -> {
                    LoginRequestMessage.serializer()
                }
                LoginRes.type -> LoginRequestMessage.serializer()
                LoginReq2.type -> LoginRequestMessage.serializer()
                HeartBeatReq.type -> HeartBeatRequestMessage.serializer()
                HeartBeatRes.type -> HeartBeatResponseMessage.serializer()
                else -> {
                    HeartBeatRequestMessage.serializer()
                }
            }
        }
    }
}
