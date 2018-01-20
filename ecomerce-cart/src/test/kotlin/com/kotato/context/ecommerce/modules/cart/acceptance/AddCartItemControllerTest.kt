package com.kotato.context.ecommerce.modules.cart.acceptance

import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.shared.ContextStarterTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

class AddCartItemControllerTest : ContextStarterTest() {

    @Test
    fun `it should add item to cart`() {
        val cartId = CartIdStub.random()
        createCart(cartId.id)

        val restRequest = AddCartItemRestRequestStub.random(cartId = cartId.id)
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(restRequest))
                .`when`()
                .patch("/ecommerce/cart/main")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
    }


    private fun createCart(id: UUID) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(CreateCartRestRequestStub.random(cartId = id)))
                .`when`()
                .post("/ecommerce/cart")
                .then()
                .statusCode(HttpStatus.CREATED.value())
    }
}