package com.ck567.netty.chatroom.protocol

import com.ck567.netty.chatroom.message.Message
import com.ck567.netty.chatroom.message.entity.HeartBeatProto
import com.ck567.netty.chatroom.util.OptionType
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*

/**
 * @description: 消息编解码工厂
 */
@Component
class MessageCodecFactory {
    /**
     * @param type    操作类型
     * @param message 消息
     * @return message
     * @description: 将消息解码成Message
     * @author: YapuLv
     * @time: 2021/11/10 10:44
     */
    @Throws(Exception::class)
    fun decodeMessage(type: OptionType, message: ByteArray?): Message {
        val msg =
        when (type) {
            OptionType.HeartBeatsReq -> return Message(type.type, HeartBeatProto.HeartBeat.getDefaultInstance().getParserForType().parseFrom(message))
        }
        throw RuntimeException(String.format("[message]:%s,未找到该信息所对应的消息类型", Arrays.toString(message)))
    }
}