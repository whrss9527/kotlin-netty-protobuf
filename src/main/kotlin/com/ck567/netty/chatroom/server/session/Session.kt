package com.ck567.netty.chatroom.server.session

import io.netty.channel.Channel

interface Session {
    /**
     * 绑定会话
     * @param channel 哪个 channel 要绑定会话
     * @param username 会话绑定用户
     */
    fun bind(channel: Channel, username: String)

    /**
     * 解绑会话
     * @param channel 哪个 channel 要解绑会话
     */
    fun unbind(channel: Channel)

    /**
     * 获取属性
     * @param channel 哪个 channel
     * @param name 属性名
     * @return 属性值
     */
    fun getAttribute(channel: Channel, name: String): Any

    /**
     * 设置属性
     * @param channel 哪个 channel
     * @param name 属性名
     * @param value 属性值
     */
    fun setAttribute(channel: Channel, name: String, value: Any)

    /**
     * 根据用户名获取 channel
     * @param username 用户名
     * @return channel
     */
    fun getChannel(username: String): Channel?
}