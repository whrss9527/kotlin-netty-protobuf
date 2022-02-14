package com.ck567.netty.chatroom.message

import com.google.protobuf.MessageLite
import java.io.Serializable

data class Message(
    /** 消息类型 */
    val type: Short,
    /** 消息 */
    val msg: MessageLite?
) : Serializable {
    constructor(): this(1,null)
    companion object {
        private const val serialVersionUID = -5415899635613935282L
    }
}