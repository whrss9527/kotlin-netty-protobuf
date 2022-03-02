package com.ck567.netty.chatroom.protocol

import com.ck567.netty.chatroom.message.Message
import com.ck567.netty.chatroom.util.OperateType
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame
import io.netty.handler.codec.http.websocketx.WebSocketFrame
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.protobuf.ProtoBuf
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
@ChannelHandler.Sharable
class MessageEncoder : MessageToMessageEncoder<Message>() {
    @Throws(Exception::class)
    override fun encode(ctx: ChannelHandlerContext, msg: Message, out: MutableList<Any>) {
        val outBuf = ctx.alloc().buffer()
        val type: Short = msg.type
        val serialization = OperateType.getSerializer(type)
        val data: ByteArray = ProtoBuf.encodeToByteArray(serialization as SerializationStrategy<Any>,msg.msg)
        // 写入操作数
        outBuf.writeShort(type.toInt())
        // 写入数据体
        outBuf.writeBytes(data)
        val frame: WebSocketFrame = BinaryWebSocketFrame(outBuf)
        out.add(frame)
    }
}