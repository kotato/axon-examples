package com.kotato.context.ecommerce.modules.cart.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.Amount
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.domain.view.add.AddCartItemOnCartItemAddedEventHandler
import com.kotato.context.ecommerce.modules.cart.domain.view.add.CartViewCartItemAdder
import com.kotato.context.ecommerce.modules.cart.stub.CartItemAddedEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CartViewStub
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.shared.money.Money
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class CartViewCartItemAdderTest {

    private val repository: CartViewRepository = mock()
    private val service = CartViewCartItemAdder(repository)
    private val handler = AddCartItemOnCartItemAddedEventHandler(service)

    @Test
    fun `it should add item on cart view`() {
        val view = CartViewStub.random(cartItems = emptyMap())
        val event = CartItemAddedEventStub.random(aggregateId = view.id.asString())
        val expected = view.copy(cartItems = mapOf(CartItem(ItemId.fromString(event.itemId),
                                                            Money.of(event.price, event.currency)) to Amount(event.quantity)))

        shouldSearchCartView(view.id, view)
        shouldSaveCartView(expected)

        handler.on(event)

        verify(repository).save(expected)
    }

    @Test
    fun `it should add item on cart view for existent item in view`() {
        val event = CartItemAddedEventStub.random()
        val cartItem = CartItem(ItemId.fromString(event.itemId),
                                Money.of(event.price, event.currency))
        val view = CartViewStub.random(id = CartId.fromString(event.aggregateId()),
                                       cartItems = mapOf(cartItem to Amount(event.quantity)))
        val expected = view.copy(cartItems = mapOf(cartItem to Amount(event.quantity * 2)))

        shouldSearchCartView(view.id, view)
        shouldSaveCartView(expected)

        handler.on(event)

        verify(repository).save(expected)
    }

    @Test
    fun `it should throw exception when adding item on cart view`() {
        assertFailsWith<CartViewNotFoundException> {
            CartItemAddedEventStub.random()
                    .also { shouldSearchCartView(CartId.fromString(it.aggregateId()), null) }
                    .let(handler::on)
        }
    }

    private fun shouldSaveCartView(cart: CartView) {
        doNothing().whenever(repository).save(cart)
    }

    private fun shouldSearchCartView(id: CartId, cart: CartView?) {
        doReturn(cart).whenever(repository).search(id)
    }

}