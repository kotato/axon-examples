package com.kotato.context.ecommerce.modules.cart.acceptance

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.Amount
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.ReadModelTransactionWrapper
import com.kotato.shared.money.Money
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.test.assertNotNull

class AddCartItemControllerTest : ContextStarterTest() {

    @Inject private lateinit var readModelTransaction: ReadModelTransactionWrapper
    @Inject private lateinit var repository: CartViewRepository

    @Test
    fun `it should add item to cart`() {
        val cartId = CartIdStub.random()
        val createCartRestRequest = CreateCartRestRequestStub.random(cartId = cartId.id)
        cartFlow.createCart(createCartRestRequest)

        val restRequest = AddCartItemRestRequestStub.random()
        cartFlow.addItem(restRequest, cartId)

        readModelTransaction { repository.search(cartId) }
                .let {
                    assertNotNull(it)
                    val cartItem = CartItem(ItemId.fromString(restRequest.itemId!!.toString()),
                                            Money.of(restRequest.price!!, restRequest.currency!!))
                    assertSimilar(it,
                                  CartView(id = CartId.fromString(createCartRestRequest.id!!.toString()),
                                           createdOn = ZonedDateTime.now(),
                                           userId = UserId.fromString(createCartRestRequest.userId!!.toString()),
                                           cartItems = mapOf(cartItem to Amount(restRequest.quantity!!))))
                }
    }

    @Test
    fun `it should add a cart item on already existing item on cart`() {
        val cartId = CartIdStub.random()
        val createCartRestRequest = CreateCartRestRequestStub.random(cartId = cartId.id)
        cartFlow.createCart(createCartRestRequest)

        val restRequest = AddCartItemRestRequestStub.random()
        (0..1).forEach { cartFlow.addItem(restRequest, cartId) }

        readModelTransaction.invoke { repository.search(cartId) }
                .let {
                    assertNotNull(it)
                    val cartItem = CartItem(ItemId.fromString(restRequest.itemId!!.toString()),
                                            Money.of(restRequest.price!!, restRequest.currency!!))
                    assertSimilar(it,
                                  CartView(id = CartId.fromString(createCartRestRequest.id!!.toString()),
                                           createdOn = ZonedDateTime.now(),
                                           userId = UserId.fromString(createCartRestRequest.userId!!.toString()),
                                           cartItems = mapOf(cartItem to Amount(restRequest.quantity!! * 2))))
                }
    }

    @Test
    fun `it should add a cart item on already existing item on cart with different price`() {
        val cartId = CartIdStub.random()
        val createCartRestRequest = CreateCartRestRequestStub.random(cartId = cartId.id)
        cartFlow.createCart(createCartRestRequest)

        val restRequest = AddCartItemRestRequestStub.random()
        cartFlow.addItem(restRequest, cartId)

        val restRequest2 = AddCartItemRestRequestStub.random()
        cartFlow.addItem(restRequest2, cartId)

        readModelTransaction.invoke { repository.search(cartId) }
                .let {
                    assertNotNull(it)
                    val cartItem = CartItem(ItemId.fromString(restRequest.itemId!!.toString()),
                                            Money.of(restRequest.price!!, restRequest.currency!!))
                    val cartItem2 = CartItem(ItemId.fromString(restRequest2.itemId!!.toString()),
                                             Money.of(restRequest2.price!!, restRequest2.currency!!))
                    assertSimilar(it,
                                  CartView(id = CartId.fromString(createCartRestRequest.id!!.toString()),
                                           createdOn = ZonedDateTime.now(),
                                           userId = UserId.fromString(createCartRestRequest.userId!!.toString()),
                                           cartItems = mapOf(cartItem to Amount(restRequest.quantity!!),
                                                             cartItem2 to Amount(restRequest2.quantity!!))))
                }
    }

    @Test
    fun `it should throw exception when trying to add a cart item`() {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(AddCartItemRestRequestStub.random()))
                .`when`()
                .patch("/ecommerce/cart/${CartIdStub.random()}")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }

}