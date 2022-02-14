package com.ck567.netty.chatroom.server

import com.ck567.netty.chatroom.util.logger
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.springframework.stereotype.Component

/**
 * 服务端基本配置，通过一个静态单例类，保证启动时候只被加载一次
 */
@Component
class ChatServer {
    /**
     * 单例静态内部类
     * @author wuhao
     */
    object SingletonChatServer {
        val instance = ChatServer()
    }

    private val boss: EventLoopGroup
    private val worker: EventLoopGroup
    private val serverBootstrap: ServerBootstrap
//    fun start() {
//        System.err.println("netty 服务端启动完毕 .....")
//        val ch = serverBootstrap.bind(8021).sync().channel()
//        ch.closeFuture().sync()
//    }
    private var future: ChannelFuture? = null
    fun start() {
        future = serverBootstrap.bind(8021)
        logger.error("netty 服务端启动完毕 .....")
    }

    init {
        boss = NioEventLoopGroup()
        worker = NioEventLoopGroup()
        serverBootstrap = ServerBootstrap()
        serverBootstrap.channel(NioServerSocketChannel::class.java)
        serverBootstrap.group(boss, worker)
        serverBootstrap.childHandler(ChatServerInitializer())
    }
    companion object {
        val instance: ChatServer = SingletonChatServer.instance
        private val logger = logger<ChatServer>()
    }

}



