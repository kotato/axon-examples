package com.kotato.context.ecommerce.modules.cart.acceptance

import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.shared.ContextStarterTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class CreateCartControllerTest : ContextStarterTest() {

    @Test
    fun `potato`() {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(CreateCartRestRequestStub.random()))
                .`when`()
                .post("/ecommerce/cart")
                .then()
                .statusCode(HttpStatus.CREATED.value())
    }

}