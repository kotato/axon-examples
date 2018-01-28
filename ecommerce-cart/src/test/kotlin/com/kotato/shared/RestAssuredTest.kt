package com.kotato.shared

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.config.DecoderConfig
import io.restassured.config.EncoderConfig
import io.restassured.config.LogConfig
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RedirectConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.config.SSLConfig
import io.restassured.filter.log.LogDetail
import io.restassured.parsing.Parser

class RestAssuredTest {
    companion object Config {
        fun config(serverPort: Int, objectMapper: ObjectMapper) {
            RestAssured.port = serverPort
            RestAssured.defaultParser = Parser.JSON
            RestAssured.config = RestAssuredConfig.config()
                    .redirect(RedirectConfig.redirectConfig().followRedirects(false))
                    .encoderConfig(
                            EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
                    .decoderConfig(DecoderConfig.decoderConfig().noContentDecoders())
                    .objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory { cls, charset -> objectMapper })
                    .sslConfig(SSLConfig().relaxedHTTPSValidation())
                    .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
        }
    }
}
