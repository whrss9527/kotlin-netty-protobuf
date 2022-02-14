package com.ck567.netty.chatroom.server.handler

import com.google.protobuf.MessageLite
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.lang.Exception
import java.lang.IllegalArgumentException

/***
 * Handler容器
 */
@Component
class MessageHandlerContainer @Autowired constructor(private val context: ApplicationContext) :
    InitializingBean {
    private val handlers: MutableMap<Short, MessageHandler<MessageLite>> = HashMap<Short, MessageHandler<MessageLite>>()

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        context.getBeansOfType(MessageHandler::class.java).values
            .forEach{ handler ->
                handlers[handler.type!!] = handler as MessageHandler<MessageLite>
            }
        println("[MessageHandlerContainer] [Handler count]: {}" + handlers.size)
    }

    /**
     * @param type 消息类型
     * @return 处理器
     * @description: 根据类型获取处理器
     */
    fun getHandler(type: Short): MessageHandler<MessageLite> {
        return handlers[type]
            ?: throw IllegalArgumentException(String.format("消息类型：%d，找不到匹配的处理器", type))
    }
}
