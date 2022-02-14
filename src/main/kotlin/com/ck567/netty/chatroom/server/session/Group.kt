package com.ck567.netty.chatroom.server.session

class Group {
    // 聊天室名称
    var name: String? = null

    // 聊天室成员
    var members: HashSet<String>? = null

    companion object{
        val EMPTY_GROUP: Group = Group("empty", HashSet())
    }


    constructor(name: String?, members: HashSet<String>?) {
        this.name = name
        this.members = members
    }
}