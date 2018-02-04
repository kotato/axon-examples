package com.kotato.shared

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import io.restassured.response.ValidatableResponse
import java.time.ZonedDateTime

fun ValidatableResponse.assertZonedDateTime(datePath: String, date: ZonedDateTime): ValidatableResponse {
    this.extract().path<String>(datePath)
            .let { ZonedDateTime.parse(it) }
            .let { assertSimilar(it, date) }
    return this
}