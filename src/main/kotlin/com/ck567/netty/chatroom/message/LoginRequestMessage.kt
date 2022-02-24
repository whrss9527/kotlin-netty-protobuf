package com.ck567.netty.chatroom.message

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@Serializable
data class LoginRequestMessage(
    @ProtoNumber(1)
    val userName: String,
    @ProtoNumber(2)
    val password: String
)
