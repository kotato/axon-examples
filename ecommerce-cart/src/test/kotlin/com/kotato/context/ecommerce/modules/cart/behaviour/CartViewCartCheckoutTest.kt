package com.kotato.context.ecommerce.modules.cart.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.domain.view.checkout.CartViewCheckout
import com.kotato.context.ecommerce.modules.cart.domain.view.checkout.CheckoutCartOnCartCheckedOutEventHandler
import com.kotato.context.ecommerce.modules.cart.stub.CartCheckedOutEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CartViewStub
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test

class CartViewCartCheckoutTest {

    private val repository: CartViewRepository = mock()
    private val service = CartViewCheckout(repository)
    private val handler = CheckoutCartOnCartCheckedOutEventHandler(service)

    @Test
    fun `it should checkout on cart view`() {
        val view = CartViewStub.random()
        val event = CartCheckedOutEventStub.random(aggregateId = view.id.asString())
        val expected = view.copy(checkout = true)

        shouldSearchCartView(view.id, view)
        shouldSaveCartView(expected)

        handler.on(event)

        verify(repository).save(expected)
    }
    
    private fun shouldSaveCartView(cart: CartView) {
        doNothing().whenever(repository).save(cart)
    }

    private fun shouldSearchCartView(id: CartId, cart: CartView?) {
        doReturn(cart).whenever(repository).search(id)
    }

}