package com.ck567.netty.chatroom.util

import com.ck567.netty.chatroom.message.HeartBeatRequestMessage
import com.ck567.netty.chatroom.message.HeartBeatResponseMessage

enum class OperateType(
    val type: Short
) {
    // 搞几个枚举放在这里
    HeartBeatReq(1793),
    HeartBeatRes(1795),
    ;

    companion object {

        fun getSerializer(type:Short): Any {
            // 拿对应的序列化对象
            return when(type) {
                HeartBeatReq.type -> HeartBeatRequestMessage.serializer()
                HeartBeatRes.type -> HeartBeatResponseMessage.serializer()
                else -> {
                    HeartBeatRequestMessage.serializer()
                }
            }
        }
    }
}
