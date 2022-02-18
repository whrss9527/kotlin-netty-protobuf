package com.ck567.netty.chatroom.server.handler

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.timeout.IdleStateEvent
import io.netty.handler.timeout.IdleStateHandler
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@ChannelHandler.Sharable
@Component
class ServerIdleStateHandler :
    IdleStateHandler(READER_IDLE_TIME.toLong(), 0, 0, TimeUnit.SECONDS) {
    override fun channelIdle(ctx: ChannelHandlerContext, evt: IdleStateEvent) {
        println("{} 秒内没有读取到数据,关闭连接$READER_IDLE_TIME")
        ctx.channel().close()
    }

    companion object {
        /**
         * 空闲检测时间单位：秒
         */
        private const val READER_IDLE_TIME = 15
    }
}
