package com.ck567.netty.chatroom.server.handler

import com.ck567.netty.chatroom.message.LoginRequestMessage
import com.ck567.netty.chatroom.message.Message
import com.ck567.netty.chatroom.server.service.ServiceFactory
import com.ck567.netty.chatroom.server.session.SessionFactory
import com.ck567.netty.chatroom.util.logger
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

@ChannelHandler.Sharable
class LoginRequestHandler : SimpleChannelInboundHandler<LoginRequestMessage>() {

    override fun channelRead0(ctx: ChannelHandlerContext, msg: LoginRequestMessage) {
        logger.info("==================")
        logger.info("登陆操作：${msg}")
        val login = ServiceFactory.getUserService().login(msg.userName, msg.password)
//        if (login) {
            SessionFactory.getSession().bind(ctx.channel(), msg.userName)
            val short: Short = 1
            ctx.writeAndFlush(Message(1,LoginRequestMessage("111","222")))
            logger.info("登陆成功！")
//        } else {
//            ctx.writeAndFlush("账号或密码不正确")
//            logger.info("登陆失败！")
//        }
    }
    companion object {
        private val logger = logger<LoginRequestHandler>()
    }
}