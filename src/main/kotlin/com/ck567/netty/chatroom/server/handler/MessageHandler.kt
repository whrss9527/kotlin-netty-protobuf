package com.ck567.netty.chatroom.server.handler

import com.google.protobuf.MessageLite
import io.netty.channel.Channel

/**
 * @description: 消息处理器
 */
interface MessageHandler<T : MessageLite> {
    /**
     * @param message 消息
     * @param channel 管道
     * @description: 执行处理消息策略
     */
    fun execute(channel: Channel, message: T)

    /**
     * @return 处理器类型
     * @description: 获取处理器类型
     */
    val type: Short
}
