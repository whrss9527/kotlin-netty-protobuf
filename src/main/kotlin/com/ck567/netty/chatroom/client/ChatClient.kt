//package com.ck567.netty.chatroom.client
//
//import com.ck567.netty.chatroom.message.*
//import com.ck567.netty.chatroom.protocol.MessageCodecSharable
//import com.ck567.netty.chatroom.protocol.ProcotolFrameDecoder
//import io.netty.bootstrap.Bootstrap
//import io.netty.channel.ChannelDuplexHandler
//import io.netty.channel.ChannelHandlerContext
//import io.netty.channel.ChannelInboundHandlerAdapter
//import io.netty.channel.ChannelInitializer
//import io.netty.channel.nio.NioEventLoopGroup
//import io.netty.channel.socket.SocketChannel
//import io.netty.channel.socket.nio.NioSocketChannel
//import io.netty.handler.logging.LogLevel
//import io.netty.handler.logging.LoggingHandler
//import io.netty.handler.timeout.IdleState
//import io.netty.handler.timeout.IdleStateEvent
//import io.netty.handler.timeout.IdleStateHandler
//import java.lang.Exception
//import java.util.*
//import java.util.concurrent.CountDownLatch
//import java.util.concurrent.atomic.AtomicBoolean
//
//object ChatClient {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val group = NioEventLoopGroup()
//        val LOGGING_HANDLER = LoggingHandler(LogLevel.DEBUG)
//        val MESSAGE_CODEC = MessageCodecSharable()
//        val WAIT_FOR_LOGIN = CountDownLatch(1)
//        val LOGIN = AtomicBoolean(false)
//        val EXIT = AtomicBoolean(false)
//        val scanner = Scanner(System.`in`)
//        try {
//            val bootstrap = Bootstrap()
//            bootstrap.channel(NioSocketChannel::class.java)
//            bootstrap.group(group)
//            bootstrap.handler(object : ChannelInitializer<SocketChannel>() {
//                @Throws(Exception::class)
//                override fun initChannel(ch: SocketChannel) {
//                    ch.pipeline().addLast(ProcotolFrameDecoder())
//                    ch.pipeline().addLast(MESSAGE_CODEC)
//                    // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
//                    // 3s 内如果没有向服务器写数据，会触发一个 IdleState#WRITER_IDLE 事件
//                    ch.pipeline().addLast(IdleStateHandler(0, 3, 0))
////                     ChannelDuplexHandler 可以同时作为入站和出站处理器
//                    // ChannelDuplexHandler 可以同时作为入站和出站处理器
////                    ch.pipeline().addLast(object : ChannelDuplexHandler() {
////                        // 用来触发特殊事件
////                        @Throws(Exception::class)
////                        override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
////                            val event = evt as IdleStateEvent
////                            // 触发了写空闲事件
////                            if (event.state() == IdleState.WRITER_IDLE) {
//////                                log.debug("3s 没有写数据了，发送一个心跳包");
////                                ctx.writeAndFlush(PingMessage())
////                            }
////                        }
////                    })
//                    ch.pipeline().addLast("client handler", object : ChannelInboundHandlerAdapter() {
//                        // 接收响应消息
//                        @Throws(Exception::class)
//                        override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
////                            println("接收到返回数据")
//                            println("msg: {}$msg")
//                            if (msg is LoginResponseMessage) {
//                                val response: LoginResponseMessage = msg as LoginResponseMessage
//                                if (response.success) {
//                                    // 如果登录成功
//                                    LOGIN.set(true)
//                                }
//                                // 唤醒 system in 线程
//                                WAIT_FOR_LOGIN.countDown()
//                            }
//                        }
//
//                        // 在连接建立后触发 active 事件
//                        @Throws(Exception::class)
//                        override fun channelActive(ctx: ChannelHandlerContext) {
//                            // 负责接收用户在控制台的输入，负责向服务器发送各种消息
//                            Thread {
//                                println("请输入用户名:")
//                                val username = scanner.nextLine()
//                                if (EXIT.get()) {
//                                    return@Thread
//                                }
//                                println("请输入密码:")
//                                val password = scanner.nextLine()
//                                if (EXIT.get()) {
//                                    return@Thread
//                                }
//                                // 构造消息对象
//                                val message = LoginRequestMessage(username, password)
//                                println(message.toString())
//                                // 发送消息
//                                ctx.writeAndFlush(message)
//                                println("等待后续操作...")
//                                try {
//                                    WAIT_FOR_LOGIN.await()
//                                } catch (e: InterruptedException) {
//                                    e.printStackTrace()
//                                }
//                                // 如果登录失败
//                                if (!LOGIN.get()) {
//                                    ctx.channel().close()
//                                    return@Thread
//                                }
//                                while (true) {
//                                    println("==================================")
//                                    println("send [username] [content]")
//                                    println("gsend [group name] [content]")
//                                    println("gcreate [group name] [m1,m2,m3...]")
//                                    println("gmembers [group name]")
//                                    println("gjoin [group name]")
//                                    println("gquit [group name]")
//                                    println("quit")
//                                    println("==================================")
//                                    var command: String? = null
//                                    command = try {
//                                        scanner.nextLine()
//                                    } catch (e: Exception) {
//                                        break
//                                    }
//                                    if (EXIT.get()) {
//                                        return@Thread
//                                    }
//                                    val s = command?.split(" ")?.toTypedArray()
//                                    when (s!![0]) {
//                                    "send" -> ctx.writeAndFlush(ChatRequestMessage(username, s[1], s[2]))
//                                    "gsend" -> ctx.writeAndFlush(GroupChatRequestMessage(username, s[1], s[2]))
//                                    "gcreate" -> {
//                                        val set: HashSet<String> =
//                                            HashSet(listOf(*s[2]
//                                                .split(",").toTypedArray()))
//                                        set.add(username) // 加入自己
//                                        ctx.writeAndFlush(GroupCreateRequestMessage(s[1], set))
//                                    }
//                                    "gmembers" -> ctx.writeAndFlush(GroupMembersRequestMessage(s[1]))
//                                    "gjoin" -> ctx.writeAndFlush(GroupJoinRequestMessage(username, s[1]))
//                                    "gquit" -> ctx.writeAndFlush(GroupQuitRequestMessage(username, s[1]))
//                                        "quit" -> {
//                                            ctx.channel().close()
//                                            return@Thread
//                                        }
//                                    }
//                                }
//                            }.start()
//                        }
//
//                        // 在连接断开时触发
//                        @Throws(Exception::class)
//                        override fun channelInactive(ctx: ChannelHandlerContext) {
//                            println("连接已经断开，按任意键退出..")
//                            EXIT.set(true)
//                        }
//
//                        // 在出现异常时触发
//                        @Throws(Exception::class)
//                        override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
//                            println("连接已经断开，按任意键退出..{}")
//                            EXIT.set(true)
//                        }
//                    })
//                }
//            })
//            val channel = bootstrap.connect("172.16.101.166", 10050).sync().channel()
//            channel.closeFuture().sync()
//        } catch (e: Exception) {
//            println("client error")
//        } finally {
//            group.shutdownGracefully()
//        }
//    }
//}
