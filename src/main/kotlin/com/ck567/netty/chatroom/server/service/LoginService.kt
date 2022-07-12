package com.ck567.netty.chatroom.server.service

import com.ck567.netty.chatroom.server.message_bus.NoticeDTO
import com.ck567.netty.chatroom.server.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class LoginService {

    fun noticeGoOffline(msg: NoticeDTO) {
        val ctx = SessionFactory.getSession().getChannel(msg.userId)
        if(ctx != null){
            SessionFactory.getSession().unbind(ctx)
            // set redis
        }

    }
}