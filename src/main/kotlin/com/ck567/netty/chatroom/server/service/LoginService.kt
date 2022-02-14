package com.ck567.netty.chatroom.server.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class LoginService {
    private val allUserMap: MutableMap<String, String> = ConcurrentHashMap<String, String>()

    init {
        allUserMap["zhangsan"] = "123"
        allUserMap["lisi"] = "123"
        allUserMap["wangwu"] = "123"
        allUserMap["zhaoliu"] = "123"
        allUserMap["qianqi"]= "123"
    }

    fun login(username: String, password: String): Boolean {
        val pass = allUserMap[username] ?: return false
        return pass == password
    }
}