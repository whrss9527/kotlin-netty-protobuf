package com.ck567.netty.chatroom.server

//import com.ck567.netty.chatroom.protocol.ProcotolFrameDecoder
import com.ck567.netty.chatroom.message.MessageDispatcher
import com.ck567.netty.chatroom.protocol.MessageDecoder
import com.ck567.netty.chatroom.protocol.MessageEncoder
import com.ck567.netty.chatroom.server.handler.*
import com.ck567.netty.chatroom.util.logger
import io.netty.channel.Channel
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
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
import javax.annotation.PostConstruct

@Component
class ChatServerInitializer :  ChannelInitializer<Channel>() {
    private val LOGGING_HANDLER = LoggingHandler(LogLevel.DEBUG)


     var decoder: MessageDecoder = MessageDecoder()
     var encoder: MessageEncoder= MessageEncoder()


     var dispatcher: MessageDispatcher = MessageDispatcher()

     var idle:ServerIdleStateHandler = ServerIdleStateHandler()



    @Throws(Exception::class)
    override fun initChannel(ch: Channel) {

        // 日志
        ch.pipeline().addLast(LOGGING_HANDLER)
        ch.pipeline().addLast(HttpServerCodec())
            .addLast(ChunkedWriteHandler())
            .addLast(HttpObjectAggregator(1024 * 64))
            .addLast(WebSocketServerCompressionHandler())
            .addLast(WebSocketServerProtocolHandler("/", null, true))
        // 消息编解码
        ch.pipeline().addLast(decoder)
        ch.pipeline().addLast(encoder)
        ch.pipeline().addLast(dispatcher)
        ch.pipeline().addLast(idle)

    }

    companion object {
        private val logger = logger<ChatServerInitializer>()
    }
}