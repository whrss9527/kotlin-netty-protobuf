package com.ck567.netty.chatroom.server.handler

import com.ck567.netty.chatroom.server.session.SessionFactory
import com.ck567.netty.chatroom.util.Jwt
import com.ck567.netty.chatroom.util.logger
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.FullHttpRequest

@ChannelHandler.Sharable
class ChannelAuthHandler: SimpleChannelInboundHandler<FullHttpRequest>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: FullHttpRequest?) {
        logger.debug("==========ChannelAuthHandler")
        val uri = msg!!.uri()
        val temp: List<String> = uri.split("/")

        val userId = temp[2]
        val token = temp[3]
        logger.debug("userId:$userId")
        logger.debug("token:$token")
        if(Jwt.verify(token, userId)){
            logger.debug("==========token校验通过")
            // 绑定appid_userId 对应
            val appId = temp[1]
            SessionFactory.getSession().bind(ctx!!.channel(), appId+"_"+userId)
            msg.uri = "/"
            // 让消息继续往流水线下游走，如果没有这句则会报错
            ctx!!.fireChannelRead(msg!!.retain())
            // 在本channel上移除这个handler消息处理，即只处理一次，鉴权通过与否
            ctx!!.pipeline().remove(ChannelAuthHandler::class.java)
        }else{
            ctx!!.close()
            logger.error("==========token校验失败")
        }
    }

    companion object {
        private val logger  = logger<ChannelAuthHandler>()
    }
}