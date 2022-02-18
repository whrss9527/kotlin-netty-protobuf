package com.ck567.netty.chatroom.server.handler

import com.ck567.netty.chatroom.message.entity.LoginProto
import com.ck567.netty.chatroom.util.OptionType
import io.netty.channel.Channel
import io.netty.channel.ChannelHandler


@ChannelHandler.Sharable
class LoginHandler : MessageHandler<LoginProto.LoginReq> {

    override fun execute(channel: Channel, message: LoginProto.LoginReq) {
        println("login...")
    }

    override val type: Short
        get() = OptionType.LoginReq.type
}