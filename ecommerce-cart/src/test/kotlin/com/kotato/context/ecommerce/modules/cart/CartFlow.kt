package com.kotato.context.ecommerce.modules.cart

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotato.context.ecommerce.modules.cart.adapter.create.CreateCartRestRequest
import com.kotato.context.ecommerce.modules.cart.adapter.update.CartItemRestRequest
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import io.restassured.RestAssured
import org.springframework.http.HttpStatus

class CartFlow(private val objectMapper: ObjectMapper) {

    fun createCart(restRequest: CreateCartRestRequest) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(restRequest))
                .`when`()
                .post("/ecommerce/cart")
                .then()
                .statusCode(HttpStatus.CREATED.value())
    }

    fun addItem(restRequest: CartItemRestRequest, cartId: CartId) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(restRequest))
                .`when`()
                .patch("/ecommerce/cart/${cartId.asString()}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
    }

    fun subtractItem(restRequest: CartItemRestRequest, cartId: CartId) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(restRequest))
                .`when`()
                .patch("/ecommerce/cart/${cartId.asString()}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
    }

    fun checkout(id: CartId) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .`when`()
                .post("/ecommerce/cart/$id/checkout")
                .then()
                .statusCode(HttpStatus.CREATED.value())
    }
}