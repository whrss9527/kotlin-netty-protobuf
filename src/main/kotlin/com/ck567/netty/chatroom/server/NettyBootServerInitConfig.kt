package com.ck567.netty.chatroom.server

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

//@Component
//class NettyBootServerInitConfig : ApplicationListener<ContextRefreshedEvent> {
//
//    override fun onApplicationEvent(event: ContextRefreshedEvent) {
//        if (event.applicationContext.parent == null) {
//            ChatServer.instance.start()
//        }
//    }
//
//
//}