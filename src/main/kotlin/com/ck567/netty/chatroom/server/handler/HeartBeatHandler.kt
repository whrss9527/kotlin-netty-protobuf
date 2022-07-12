package com.ck567.netty.chatroom.server.handler

import com.ck567.netty.chatroom.message.HeartBeatRequestMessage
import com.ck567.netty.chatroom.message.HeartBeatResponseMessage
import com.ck567.netty.chatroom.message.Message
import com.ck567.netty.chatroom.util.OperateType
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler


@ChannelHandler.Sharable
class HeartBeatHandler : SimpleChannelInboundHandler<HeartBeatRequestMessage>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: HeartBeatRequestMessage) {
        println("收到心跳消息")
        ctx.writeAndFlush(Message(OperateType.HeartBeatRes.type, HeartBeatResponseMessage()))
    }

}