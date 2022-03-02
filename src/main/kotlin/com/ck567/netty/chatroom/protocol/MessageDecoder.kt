package com.ck567.netty.chatroom.protocol

import com.ck567.netty.chatroom.util.OperateType
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import io.netty.handler.codec.http.websocketx.WebSocketFrame
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.protobuf.ProtoBuf
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
@ChannelHandler.Sharable
class MessageDecoder : MessageToMessageDecoder<WebSocketFrame>() {


    @Throws(Exception::class)
    override fun decode(ctx: ChannelHandlerContext, msg: WebSocketFrame, out: MutableList<Any>) {
        val buf = msg.content()
        // 读取操作数
        val type = buf.readShort()
        println("获取到消息类型：$type")
        // 读取数据体
        val readableBytes = buf.readableBytes()
        val data = ByteArray(readableBytes)
        buf.readBytes(data)
        // 编解码器的分发
        val serialization = OperateType.getType(type)
        val ob = ProtoBuf.decodeFromByteArray(serialization as DeserializationStrategy<Any>,data)
        out.add(ob)
    }
}