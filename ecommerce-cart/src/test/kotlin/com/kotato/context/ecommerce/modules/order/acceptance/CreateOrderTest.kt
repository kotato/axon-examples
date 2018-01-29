package com.kotato.context.ecommerce.modules.order.acceptance

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.adapter.update.CartItemRestRequest
import com.kotato.context.ecommerce.modules.cart.domain.Amount
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.money.Money
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.test.assertNotNull

class CreateOrderTest : ContextStarterTest() {

    @Inject private lateinit var repository: OrderViewRepository

    @Test
    fun `it should create an order`() {
        val cartId = CartIdStub.random()
        val createCart = CreateCartRestRequestStub.random(cartId.id)
        val addItem = AddCartItemRestRequestStub.random()
        cartFlow.createCart(createCart)
        cartFlow.addItem(addItem, cartId)
        cartFlow.checkout(cartId)

        Thread.sleep(200)

        repository.searchByCartId(cartId)
                .also { assertNotNull(it) }!!
                .let {
                    assertSimilar(it.cartId, cartId)
                    assertSimilar(it.userId, UserId(createCart.userId!!))
                    assertSimilar(it.createdOn, ZonedDateTime.now())
                    assertSimilar(it.cartItems, addItem.getCartItems())
                    assertSimilar(it.price, Money.of(addItem.price!!, addItem.currency!!) * Amount(addItem.quantity!!))
                }

    }

    private fun CartItemRestRequest.getCartItems() =
            mapOf(CartItem(ItemId(itemId!!), Money.of(price!!, currency!!)) to Amount(quantity!!))

}