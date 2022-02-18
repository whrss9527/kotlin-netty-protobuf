package com.ck567.netty.chatroom.server.handler

import com.ck567.netty.chatroom.message.Message
import com.ck567.netty.chatroom.message.entity.HeartBeatProto
import com.ck567.netty.chatroom.util.OptionType
import io.netty.channel.Channel
import io.netty.channel.ChannelHandler


@ChannelHandler.Sharable
class HeartBeatHandler : MessageHandler<HeartBeatProto.HeartBeat> {
    override fun execute(channel: Channel, message: HeartBeatProto.HeartBeat) {
        val msg: Message = Message(OptionType.HeartBeatsReq.type,HeartBeatProto.HeartBeat.newBuilder().build())
        channel.writeAndFlush(msg)
    }

    override val type: Short
        get() = OptionType.HeartBeatsReq.type
}