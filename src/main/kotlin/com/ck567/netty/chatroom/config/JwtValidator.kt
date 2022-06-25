package com.ck567.netty.chatroom.config

import io.jsonwebtoken.Claims

/**
 * @throws ExpiredJwtException
 * @throws JwtException others: invalid token
 */
interface JwtValidator {
    fun validate(token: String): Claims
}