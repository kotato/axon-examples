package com.kotato.context.ecommerce.modules.cart.acceptance

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.shared.ContextStarterTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import javax.inject.Inject
import kotlin.test.assertTrue

class CheckoutCartControllerTest : ContextStarterTest() {

    @Inject private lateinit var repository: CartViewRepository

    @Test
    fun `it should checkout cart with items`() {
        val cartId = CartIdStub.random()
        cartFlow.createCart(CreateCartRestRequestStub.random(cartId.id))
        cartFlow.addItem(AddCartItemRestRequestStub.random(), cartId)
        checkout(cartId)
                .statusCode(HttpStatus.CREATED.value())

        assertTrue { repository.search(cartId)!!.checkout }
    }

    @Test
    fun `it should try to checkout cart with no items`() {
        val cartId = CartIdStub.random()
        cartFlow.createCart(CreateCartRestRequestStub.random(cartId.id))
        checkout(cartId)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }

    @Test
    fun `it should try to checkout non existent cart`() {
        checkout(CartIdStub.random())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }

    private fun checkout(id: CartId) =
            RestAssured.given()
                    .header("Content-Type", "application/json")
                    .`when`()
                    .post("/ecommerce/cart/$id/checkout")
                    .then()

}