package com.kotato.context.ecommerce.modules.payment

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotato.context.ecommerce.modules.cart.adapter.create.CreateCartRestRequest
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import io.restassured.RestAssured
import org.springframework.http.HttpStatus

class PaymentFlow(private val objectMapper: ObjectMapper) {

    fun failedCallback(id: PaymentId) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .`when`()
                .post("/ecommerce/payment/${id.asString()}/failed")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
    }

    fun succeededCallback(id: PaymentId) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .`when`()
                .post("/ecommerce/payment/${id.asString()}/succeeded")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
    }
}