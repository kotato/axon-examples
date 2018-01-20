package com.kotato.context.ecommerce.modules.cart.behaviour

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.cart.domain.Amount
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.domain.view.subtract.CartViewCartItemSubtractor
import com.kotato.context.ecommerce.modules.cart.domain.view.subtract.SubtractCartItemOnCartItemSubtractedEventHandler
import com.kotato.context.ecommerce.modules.cart.stub.CartItemSubtractedEventStub
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

class CartViewCartItemSubtractorTest {

    private val repository: CartViewRepository = mock()
    private val service = CartViewCartItemSubtractor(repository)
    private val handler = SubtractCartItemOnCartItemSubtractedEventHandler(service)

    @Test
    fun `it should subtract item on cart view`() {
        val actualAmount = Faker().number().numberBetween(3, 10)
        val amountToSubtract = Faker().number().numberBetween(2, actualAmount - 1)
        val expectedAmount = actualAmount - amountToSubtract
        val event = CartItemSubtractedEventStub.random(quantity = amountToSubtract)
        val cartItem = CartItem(ItemId.fromString(event.itemId),
                                Money.of(event.price, event.currency))
        val view = CartViewStub.random(id = CartId.fromString(event.aggregateId()),
                                       cartItems = mapOf(cartItem to Amount(actualAmount)))
        val expected = view.copy(cartItems = mapOf(cartItem to Amount(expectedAmount)))

        shouldSearchCartView(view.id, view)
        shouldSaveCartView(expected)

        handler.on(event)

        verify(repository).save(expected)
    }

    @Test
    fun `it should subtract item on cart view with same amount as actual has`() {
        val amount = Faker().number().numberBetween(1, 10)
        val event = CartItemSubtractedEventStub.random(quantity = amount)
        val cartItem = CartItem(ItemId.fromString(event.itemId),
                                Money.of(event.price, event.currency))
        val view = CartViewStub.random(id = CartId.fromString(event.aggregateId()),
                                       cartItems = mapOf(cartItem to Amount(amount)))
        val expected = view.copy(cartItems = emptyMap())

        shouldSearchCartView(view.id, view)
        shouldSaveCartView(expected)

        handler.on(event)

        verify(repository).save(expected)
    }

    @Test
    fun `it should throw exception when adding item on cart view`() {
        assertFailsWith<CartViewNotFoundException> {
            CartItemSubtractedEventStub.random()
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