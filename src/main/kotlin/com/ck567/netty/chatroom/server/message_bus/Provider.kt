package com.ck567.netty.chatroom.server.message_bus

import com.ck567.netty.chatroom.util.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class Provider {

    @Value("\${kafka.topics}")
    private val kafkaTopics = ""

    @Resource
    lateinit var kafkaTemplate: KafkaTemplate<String, NoticeDTO>


    fun sendMessage(msg: NoticeDTO) {
        kafkaTemplate.send(kafkaTopics, msg)
        logger.debug("发送消息成功:$msg")
    }

    companion object {
        private val logger  = logger<Provider>()
    }
}