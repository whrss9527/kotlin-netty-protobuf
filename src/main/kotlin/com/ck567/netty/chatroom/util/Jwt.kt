package com.ck567.netty.chatroom.util

import com.ck567.netty.chatroom.config.HMACJwtGenerator
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class Jwt {
    @Autowired
    lateinit var hmacJwtValidator: HMACJwtGenerator

    @PostConstruct
    fun init() {
        Jwt.hmacJwtValidator = hmacJwtValidator
    }

    companion object {
        var hmacJwtValidator: HMACJwtGenerator? = null

        fun verify(jwtToken: String, userId: String): Boolean {
            logger.debug("jwtToken =$jwtToken")
            val claims: Claims = try {
                hmacJwtValidator!!.validate(jwtToken)
            } catch (ex: ExpiredJwtException) {
                logger.error("token过期")
                return false
            } catch (ex: JwtException) {
                // MalformedJwtException, SignatureException, ..
                logger.error("token校验失败")
                return false
            }
            val playerId = claims.subject.split("_")[1]
            if (playerId != userId) {
                logger.error("用户不一致")
                return false
            }
            return true
        }


        private val logger: Logger = LoggerFactory.getLogger(Jwt::class.java)

    }

}