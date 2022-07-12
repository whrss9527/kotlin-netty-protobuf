package com.ck567.netty.chatroom.server.message_bus


import com.ck567.netty.chatroom.server.service.LoginService
import com.ck567.netty.chatroom.util.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.util.*

@Component
class Consumer {

    @Autowired
    lateinit var service: LoginService

    @KafkaListener(topics = ["\${kafka.topics}"], groupId = "\${kafka.group-id}"+"#{T(java.util.UUID).randomUUID().toString()}")
    fun noticeGoOffline(msg: NoticeDTO, ack: Acknowledgment) {
        logger.debug("接收到消息：$msg")
        service.noticeGoOffline(msg)
        ack.acknowledge()
    }

    companion object {
        private val logger = logger<Consumer>()
    }
}

data class NoticeDTO(
    val userId: String
)
