package com.ck567.netty.chatroom.server

//import com.ck567.netty.chatroom.protocol.ProcotolFrameDecoder
//import com.ck567.netty.chatroom.message.MessageDispatcher
import com.ck567.netty.chatroom.protocol.MessageDecoder
import com.ck567.netty.chatroom.protocol.MessageEncoder
import com.ck567.netty.chatroom.server.handler.*
import com.ck567.netty.chatroom.util.logger
import io.netty.channel.*
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent
import io.netty.handler.timeout.IdleStateHandler
import java.lang.Exception
import io.netty.handler.stream.ChunkedWriteHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@ChannelHandler.Sharable
@Component
class ChatServerInitializer :  ChannelInitializer<Channel>() {

    @Autowired
    lateinit var messageEncoder: MessageEncoder
    @Autowired
    lateinit var messageDecoder: MessageDecoder
//    @Autowired
//    lateinit var messageDispatcher: MessageDispatcher

//    private val channelStateHandler: ChannelStateHandler? = null
    @Autowired
    lateinit var serverIdleStateHandler: ServerIdleStateHandler

    val loginReqHandler = LoginRequestMessageHandler()
    val heartBeat = HeartBeatHandler()


    override fun initChannel(channel: Channel) {
        channel.pipeline() //                .addLast(serverIdleStateHandler)
            .addLast(HttpServerCodec())
            .addLast(ChunkedWriteHandler())
            .addLast(HttpObjectAggregator(1024 * 64))
            .addLast(WebSocketServerCompressionHandler())
            .addLast(WebSocketServerProtocolHandler("/", null, true))
            .addLast(messageEncoder)
            .addLast(messageDecoder)
            .addLast(loginReqHandler)
            .addLast(heartBeat)
    }

    companion object {
        private val logger = logger<ChatServerInitializer>()
    }
}