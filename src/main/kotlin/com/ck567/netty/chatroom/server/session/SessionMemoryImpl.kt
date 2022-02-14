package com.ck567.netty.chatroom.server.session

import io.netty.channel.Channel
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionMemoryImpl : Session{
    private val usernameChannelMap: ConcurrentHashMap<String, Channel> = ConcurrentHashMap()
    private val channelUsernameMap: ConcurrentHashMap<Channel, String> = ConcurrentHashMap()
    private val channelAttributesMap: ConcurrentHashMap<Channel, Map<String, Any>> = ConcurrentHashMap()

    override fun bind(channel: Channel, username: String) {
        usernameChannelMap[username] = channel
        channelUsernameMap[channel] = username
        channelAttributesMap[channel] = ConcurrentHashMap()
    }

    override fun unbind(channel: Channel) {
        TODO("Not yet implemented")
    }

    override fun getAttribute(channel: Channel, name: String): Any {
        TODO("Not yet implemented")
    }

    override fun setAttribute(channel: Channel, name: String, value: Any) {
        TODO("Not yet implemented")
    }

    override fun getChannel(username: String): Channel? {
        return usernameChannelMap[username]
    }


}