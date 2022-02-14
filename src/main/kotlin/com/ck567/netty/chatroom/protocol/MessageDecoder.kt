package com.ck567.netty.chatroom.protocol

import com.ck567.netty.chatroom.message.Message
import com.ck567.netty.chatroom.util.OptionType
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import io.netty.handler.codec.http.websocketx.WebSocketFrame
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class MessageDecoder : MessageToMessageDecoder<WebSocketFrame>() {

    @Autowired
    lateinit var codecFactory: MessageCodecFactory

    @Throws(Exception::class)
    override fun decode(ctx: ChannelHandlerContext, msg: WebSocketFrame, out: MutableList<Any>) {
        val buf = msg.content()
        // 读取操作数
        val type = buf.readShort()
        // 读取数据体
        val readableBytes = buf.readableBytes()
        val data = ByteArray(readableBytes)
        buf.readBytes(data)
        val optionType: OptionType = OptionType.getType(type)
        // 消息类型解码
        val message: Message = codecFactory.decodeMessage(optionType, data)
        out.add(message)
    }
}
