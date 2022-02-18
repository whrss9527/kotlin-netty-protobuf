package com.ck567.netty.chatroom.server.handler

import com.ck567.netty.chatroom.message.entity.loginReq
import com.google.protobuf.MessageLite
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import java.lang.Exception
import java.lang.IllegalArgumentException

/***
 * Handler容器
 */
@Component
class MessageHandlerContainer :
    InitializingBean {
    private val handlers: MutableMap<Short, MessageHandler<MessageLite>> = HashMap<Short, MessageHandler<MessageLite>>()
//    @Autowired
//    lateinit var context: ApplicationContext
//    private lateinit var ctx: ApplicationContext
//    override fun setApplicationContext(applicationContext: ApplicationContext) {
//        this.ctx = applicationContext
//    }
//    @Autowired
//    lateinit var loginHandler: LoginHandler
    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        println("MessageHandlerContainer")
//        ctx.getBeansOfType(MessageHandler::class.java).values
//            .map{ handler ->
//                handlers[handler.type!!] = handler as MessageHandler<MessageLite>
//            }
        handlers[2] = LoginHandler() as MessageHandler<MessageLite>
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
