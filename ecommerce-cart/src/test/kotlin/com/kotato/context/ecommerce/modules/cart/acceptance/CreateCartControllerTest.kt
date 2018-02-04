package com.kotato.context.ecommerce.modules.cart.acceptance

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.ReadModelTransactionWrapper
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.test.assertNotNull

class CreateCartControllerTest : ContextStarterTest() {

    @Inject private lateinit var readModelTransaction: ReadModelTransactionWrapper
    @Inject private lateinit var repository: CartViewRepository

    @Test
    fun `it should create cart`() {
        val restRequest = CreateCartRestRequestStub.random()
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(restRequest))
                .`when`()
                .post("/ecommerce/cart")
                .then()
                .statusCode(HttpStatus.CREATED.value())

        readModelTransaction { repository.search(CartId.fromString(restRequest.id!!.toString())) }
                .let {
                    assertNotNull(it)
                    assertSimilar(it, CartView(id = CartId.fromString(restRequest.id!!.toString()),
                                               createdOn = ZonedDateTime.now(),
                                               userId = UserId.fromString(restRequest.userId!!.toString()),
                                               cartItems = emptyMap()))
                }
    }

}