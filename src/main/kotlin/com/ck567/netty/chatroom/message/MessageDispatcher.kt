package com.ck567.netty.chatroom.message

import com.ck567.netty.chatroom.server.handler.MessageHandler
import com.ck567.netty.chatroom.server.handler.MessageHandlerContainer
import com.google.protobuf.MessageLite
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Exception

/**
 * @description: 消息分发器
 */
@ChannelHandler.Sharable
@Component
class MessageDispatcher:
    SimpleChannelInboundHandler<Message>() {
    @Autowired
    lateinit var container: MessageHandlerContainer

    @Throws(Exception::class)
    override fun channelRead0(ctx: ChannelHandlerContext, msg: Message) {
        val handler: MessageHandler<MessageLite> = container.getHandler(msg.type)
        handler.execute(ctx.channel(), msg.msg!!)
    }

}
