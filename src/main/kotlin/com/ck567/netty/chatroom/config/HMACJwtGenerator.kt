package com.ck567.netty.chatroom.config

import com.ck567.netty.chatroom.util.loggerFrom
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

class HMACJwtGenerator(
    private val issuer: String,
    private val expiredMinutes: Int,
    private val accessTokenSecret: String
): JwtValidator {

    /**
     * 支持多种audience
     */
    fun generate(audience: String, subject: String, secret: String, authorities: Set<String> = emptySet()): String {

        val builder = Jwts.builder()
            .setIssuer(issuer)
            .setAudience(audience)
            .setSubject(subject)
            .setExpiration(Date(System.currentTimeMillis() + expiredMinutes * 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, secret)
        if (authorities.isNotEmpty()){
            builder.claim("authorities", authorities.joinToString(","))
        }
        return builder.compact()
    }

    override fun validate(token: String): Claims {

        return Jwts.parser().setSigningKey(accessTokenSecret)
            .parseClaimsJws(token.replace("Bearer ", "")
                .also { loggerFrom(this).debug("accessToken=\n$it")  }
            ).body
    }
}