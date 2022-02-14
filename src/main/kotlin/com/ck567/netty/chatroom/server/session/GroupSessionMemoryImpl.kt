package com.ck567.netty.chatroom.server.session

import io.netty.channel.Channel
import java.util.concurrent.ConcurrentHashMap

class GroupSessionMemoryImpl : GroupSession {
    private val groupMap: ConcurrentHashMap<String, Group> = ConcurrentHashMap()

    override fun createGroup(name: String, members: HashSet<String>?): Group? {
        val group = Group(name,members)
        // putIfAbsent 如果传入key对应的value已经存在，就返回存在的value，不进行替换。如果不存在，就添加key和value，返回null
        return groupMap.putIfAbsent(name,group)
    }

    override fun joinMember(name: String, member: String): Group? {
        // 如果 key 对应的 value 不存在，则返回该 null，如果存在，则返回通过 remappingFunction 重新计算后的值。
        return groupMap.computeIfPresent(name) { _: String?, value: Group ->
            value.members?.add(member)
            value
        }
    }

    override fun removeMember(name: String, member: String?): Group? {
        return groupMap.computeIfPresent(name) { _: String?, value: Group ->
            value.members?.remove(member)
            value
        }
    }

    override fun removeGroup(name: String): Group? {
        return groupMap.remove(name)
    }

    override fun getMembers(name: String): HashSet<String>? {
        return groupMap.getOrDefault(name, Group.EMPTY_GROUP).members
    }

    override fun getMembersChannel(name: String): List<Channel>? {
        return getMembers(name)?.mapNotNull { SessionFactory.getSession().getChannel(it) }
    }

}