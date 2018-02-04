package com.kotato.context.ecommerce.modules.cart.acceptance

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.adapter.update.CartItemRestRequest
import com.kotato.context.ecommerce.modules.cart.domain.Amount
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.assertZonedDateTime
import com.kotato.shared.money.Money
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

class FindCartTest : ContextStarterTest() {

    @Test
    fun `it should find a cart`() {
        val cartId = CartIdStub.random()
        val createCart = CreateCartRestRequestStub.random(cartId.id)
        val addItem = AddCartItemRestRequestStub.random()
        cartFlow.createCart(createCart)
        cartFlow.addItem(addItem, cartId)

        Thread.sleep(200)

        RestAssured.given()
                .header("Content-Type", "application/json")
                .`when`()
                .get("/ecommerce/cart/${cartId.asString()}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(cartId.asString()))
                .body("user_id", equalTo(createCart.userId!!.toString()))
                .body("checkout", equalTo(false))
                .assertZonedDateTime("created_on", ZonedDateTime.now())
                .assertCartItems(addItem.toCartItems().toSerializedCartItems())
    }

    private fun ValidatableResponse.assertCartItems(expected: SerializedCartItems) {
        val actual = this.extract().path<Map<Triple<*, *, *>, Int>>("cart_items")
        assertSimilar(actual, expected.mapKeys { it.key.toString() })
    }

    private fun CartItemRestRequest.toCartItems() =
            mapOf(CartItem(ItemId.fromString(this.itemId!!.toString()),
                           Money.of(this.price!!, this.currency!!)) to Amount(this.quantity!!))

}