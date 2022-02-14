package com.ck567.netty.chatroom.server

//import com.ck567.netty.chatroom.protocol.ProcotolFrameDecoder
import com.ck567.netty.chatroom.protocol.MessageDecoder
import com.ck567.netty.chatroom.protocol.MessageEncoder
import com.ck567.netty.chatroom.server.handler.*
import com.ck567.netty.chatroom.util.logger
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent
import io.netty.handler.timeout.IdleStateHandler
import java.lang.Exception
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender
import io.netty.handler.stream.ChunkedWriteHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ChatServerInitializer : ChannelInitializer<SocketChannel>() {
    private val LOGGING_HANDLER = LoggingHandler(LogLevel.DEBUG)


    @Autowired
    lateinit var decoder: MessageDecoder
    @Autowired
    lateinit var encoder: MessageEncoder

    @Throws(Exception::class)
    override fun initChannel(ch: SocketChannel) {

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
        // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
        // 5s 内如果没有收到 channel 的数据，会触发一个 IdleState#READER_IDLE 事件
        ch.pipeline().addLast(IdleStateHandler(0, 5, 0))
        // ChannelDuplexHandler 可以同时作为入站和出站处理器
        ch.pipeline().addLast(object : ChannelDuplexHandler() {
            // 用来触发特殊事件
            @Throws(Exception::class)
            override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
                val event = evt as IdleStateEvent
                // 触发了读空闲事件
                if (event.state() == IdleState.READER_IDLE) {
                    logger.debug("已经 5s 没有读到数据了")
                    ctx.channel().close()
                }
            }
        })

    }

    companion object {
        private val logger = logger<ChatServerInitializer>()
    }
}