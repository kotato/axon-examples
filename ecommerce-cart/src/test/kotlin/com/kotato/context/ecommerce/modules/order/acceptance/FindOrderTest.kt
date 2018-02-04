package com.kotato.context.ecommerce.modules.order.acceptance

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.assertZonedDateTime
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import javax.inject.Inject
import kotlin.test.assertNotNull

class FindOrderTest : ContextStarterTest() {

    @Inject private lateinit var repository: OrderViewRepository

    @Test
    fun `it should find an order`() {
        val cartId = CartIdStub.random()
        val createCart = CreateCartRestRequestStub.random(cartId.id)
        val addItem = AddCartItemRestRequestStub.random()
        cartFlow.createCart(createCart)
        cartFlow.addItem(addItem, cartId)
        cartFlow.checkout(cartId)

        Thread.sleep(200)

        val order = repository.searchByCartId(cartId).also { assertNotNull(it) }!!


        RestAssured.given()
                .header("Content-Type", "application/json")
                .`when`()
                .get("/ecommerce/order/${order.id.asString()}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(order.id.asString()))
                .body("user_id", equalTo(order.userId.asString()))
                .body("cart_id", equalTo(order.cartId.asString()))
                .body("payment_id", equalTo(order.paymentId.asString()))
                .body("status", equalTo(order.status.name))
                .assertZonedDateTime("created_on", order.createdOn)
                .assertCartItems(order.cartItems.toSerializedCartItems())
    }

    private fun ValidatableResponse.assertCartItems(expected: SerializedCartItems) {
        val actual = this.extract().path<Map<Triple<*, *, *>, Int>>("cart_items")
        assertSimilar(actual, expected.mapKeys { it.key.toString() })
    }

}