package com.ck567.netty.chatroom.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts


class HMACJwtGenerator(
    private val accessTokenSecret: String
) : JwtValidator {

    override fun validate(token: String): Claims {

        return Jwts.parser().setSigningKey(accessTokenSecret)
            .parseClaimsJws(token.replace("Bearer ", "")).body
    }
}