package com.ck567.netty.chatroom.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.ck567"])
class OpsAppConfig {

    @Value("\${jwt.access-token-secret}")
    private val accessTokenSecret = "P1X8X@qL3i!5XBFQ"

    @Bean("hmacJwtGenerator")
    fun hmacJwtValidator(): HMACJwtGenerator {

        return HMACJwtGenerator(
            accessTokenSecret = accessTokenSecret
        )
    }


}