package com.ck567.netty.chatroom.server

import com.ck567.netty.chatroom.protocol.MessageDecoder
import com.ck567.netty.chatroom.protocol.MessageEncoder
import com.ck567.netty.chatroom.server.handler.*
import com.ck567.netty.chatroom.util.logger
import io.netty.channel.*
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import io.netty.handler.stream.ChunkedWriteHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@ChannelHandler.Sharable
@Component
class ChatServerInitializer :  ChannelInitializer<Channel>() {

    @Autowired
    lateinit var messageEncoder: MessageEncoder
    @Autowired
    lateinit var messageDecoder: MessageDecoder

    var serverIdleStateHandler = ServerIdleStateHandler()

    val heartBeat = HeartBeatHandler()


    override fun initChannel(channel: Channel) {
        channel.pipeline()
            .addLast(serverIdleStateHandler)
            .addLast(HttpServerCodec())
            .addLast(ChunkedWriteHandler())
            .addLast(HttpObjectAggregator(1024 * 64))
            .addLast(WebSocketServerCompressionHandler())
            .addLast(WebSocketServerProtocolHandler("/", null, true))
            .addLast(messageEncoder)
            .addLast(messageDecoder)
            .addLast(heartBeat)
    }

    companion object {
        private val logger = logger<ChatServerInitializer>()
    }
}