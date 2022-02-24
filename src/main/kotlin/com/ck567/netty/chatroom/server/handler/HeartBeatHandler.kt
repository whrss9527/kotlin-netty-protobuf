package com.ck567.netty.chatroom.server.handler

import com.ck567.netty.chatroom.message.HeartBeatRequestMessage
import com.ck567.netty.chatroom.message.LoginRequestMessage
import com.ck567.netty.chatroom.message.Message
import com.ck567.netty.chatroom.util.OptionType
import io.netty.channel.Channel
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler


@ChannelHandler.Sharable
class HeartBeatHandler : SimpleChannelInboundHandler<HeartBeatRequestMessage>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: HeartBeatRequestMessage) {
        println("收到心跳消息")
    }

}