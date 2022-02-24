package com.ck567.netty.chatroom.message

import com.google.protobuf.MessageLite
import java.io.Serializable

class Message(
    val type: Short,
    val msg: Any?
) : Serializable {
//    /**
//     * 消息类型
//     */
//     val type: Short? = null
//
//    /**
//     * 消息
//     */
//     val msg: Any? = null
}
