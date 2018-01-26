package com.kotato.context.ecommerce.modules.cart.behaviour

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.cart.domain.view.CartResponse
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.domain.view.find.CartFinder
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQuery
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQueryHandler
import com.kotato.context.ecommerce.modules.cart.stub.CartResponseStub
import com.kotato.context.ecommerce.modules.cart.stub.CartViewStub
import com.kotato.context.ecommerce.modules.cart.stub.FindCartQueryStub
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class CartFinderTest {

    private val repository: CartViewRepository = mock()
    private val service = CartFinder(repository)
    private val handler = FindCartQueryHandler(service)

    @Test
    fun `it should find cart`() {
        val view = CartViewStub.random()
        val query = FindCartQuery(view.id.asString())
        val expected = CartResponse(view.id.asString(),
                                    view.createdOn,
                                    view.userId.asString(),
                                    view.cartItems.toSerializedCartItems(),
                                    view.checkout)

        shouldSearchCart(view.id, view)

        assertSimilar(handler.on(query), expected)
    }

    @Test
    fun `it should throw exception when trying to find cart`() {
        val query = FindCartQueryStub.random()

        shouldSearchCart(CartId.fromString(query.id), null)

        assertFailsWith<CartViewNotFoundException> {
            handler.on(query)
        }
    }

    private fun shouldSearchCart(id: CartId, view: CartView?) {
        doReturn(view).whenever(repository).search(id)
    }
}