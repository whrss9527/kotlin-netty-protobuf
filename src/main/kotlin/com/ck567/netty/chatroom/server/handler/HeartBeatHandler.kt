package com.ck567.netty.chatroom.server.handler

import com.ck567.netty.chatroom.message.HeartBeatRequestMessage
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler


@ChannelHandler.Sharable
class HeartBeatHandler : SimpleChannelInboundHandler<HeartBeatRequestMessage>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: HeartBeatRequestMessage) {
        println("收到心跳消息")
    }

}