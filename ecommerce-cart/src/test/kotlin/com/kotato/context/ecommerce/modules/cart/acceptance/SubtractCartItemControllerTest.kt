package com.kotato.context.ecommerce.modules.cart.acceptance

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.adapter.update.CartItemRestRequest
import com.kotato.context.ecommerce.modules.cart.domain.Amount
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.SubtractCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.ReadModelTransactionWrapper
import com.kotato.shared.money.Money
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import javax.inject.Inject
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SubtractCartItemControllerTest : ContextStarterTest() {

    @Inject private lateinit var readModelTransaction: ReadModelTransactionWrapper
    @Inject private lateinit var repository: CartViewRepository

    @Test
    fun `it should subtract item to cart`() {
        val cartId = CreateCartRestRequestStub.random().also { cartFlow.createCart(it) }.id!!
        val addItem = AddCartItemRestRequestStub.random()
        val subItem = SubtractCartItemRestRequestStub.random(itemId = addItem.itemId!!,
                                                             price = addItem.price!!,
                                                             currency = addItem.currency!!,
                                                             quantity = (addItem.quantity!! - 1) * -1)
        patch(addItem, CartId(cartId))
                .statusCode(HttpStatus.NO_CONTENT.value())
        patch(subItem, CartId(cartId))
                .statusCode(HttpStatus.NO_CONTENT.value())
        readModelTransaction { CartId.fromString(cartId.toString()).let(repository::search) }
                .let {
                    assertNotNull(it)
                    assertTrue { it!!.cartItems.size == 1 }
                    assertSimilar(it!!.cartItems.entries.first().toPair(),
                                  CartItem(ItemId.fromString(addItem.itemId!!.toString()),
                                           Money.of(addItem.price!!, addItem.currency!!)) to Amount(1))
                }
    }

    @Test
    fun `it should subtract item to cart with same quantity as actual has`() {
        val cartId = CreateCartRestRequestStub.random().also { cartFlow.createCart(it) }.id!!
        val addItem = AddCartItemRestRequestStub.random()
        val subItem = SubtractCartItemRestRequestStub.random(itemId = addItem.itemId!!,
                                                             price = addItem.price!!,
                                                             currency = addItem.currency!!,
                                                             quantity = (addItem.quantity!!) * -1)
        patch(addItem, CartId(cartId))
                .statusCode(HttpStatus.NO_CONTENT.value())
        patch(subItem, CartId(cartId))
                .statusCode(HttpStatus.NO_CONTENT.value())
        readModelTransaction.invoke { CartId.fromString(cartId.toString()).let(repository::search) }
                .let {
                    assertNotNull(it)
                    assertTrue { it!!.cartItems.isEmpty() }
                }
    }

    @Test
    fun `it should subtract item to cart with more quantity than actual has`() {
        val cartId = CreateCartRestRequestStub.random().also { cartFlow.createCart(it) }.id!!
        val addItem = AddCartItemRestRequestStub.random()
        val subItem = SubtractCartItemRestRequestStub.random(itemId = addItem.itemId!!,
                                                             price = addItem.price!!,
                                                             currency = addItem.currency!!,
                                                             quantity = (addItem.quantity!!) * -1)
        patch(addItem, CartId(cartId))
                .statusCode(HttpStatus.NO_CONTENT.value())
        patch(subItem, CartId(cartId))
                .statusCode(HttpStatus.NO_CONTENT.value())
        readModelTransaction.invoke { CartId.fromString(cartId.toString()).let(repository::search) }
                .let {
                    assertNotNull(it)
                    assertTrue { it!!.cartItems.isEmpty() }
                }
    }

    @Test
    fun `it should throw exception when trying to subtract a cart item because cart does not exists`() {
        patch(SubtractCartItemRestRequestStub.random(), CartIdStub.random())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }

    @Test
    fun `it should throw exception when trying to subtract a cart item because no item in cart`() {
        CreateCartRestRequestStub.random()
                .also { cartFlow.createCart(it) }
                .let {
                    patch(SubtractCartItemRestRequestStub.random(), CartId(it.id!!))
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                }
    }

    private fun patch(restRequest: CartItemRestRequest, cartId: CartId) = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(objectMapper.writeValueAsString(restRequest))
            .`when`()
            .patch("/ecommerce/cart/${cartId.asString()}")
            .then()

}