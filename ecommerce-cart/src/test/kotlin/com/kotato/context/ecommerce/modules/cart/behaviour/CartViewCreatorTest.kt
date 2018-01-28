package com.kotato.context.ecommerce.modules.cart.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.create.CartCreatedEvent
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.domain.view.create.CartViewCreator
import com.kotato.context.ecommerce.modules.cart.domain.view.create.CreateCartViewOnCartCreatedEventHandler
import com.kotato.context.ecommerce.modules.cart.stub.CartCreatedEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CartViewStub
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

class CartViewCreatorTest {

    private val repository: CartViewRepository = mock()
    private val creator = CartViewCreator(repository)
    private val handler = CreateCartViewOnCartCreatedEventHandler(creator)

    @Test
    fun `it should create cart view on cart created event raised`() {
        val event = CartCreatedEventStub.random()
        val expected = CartViewStub.random(CartId.fromString(event.aggregateId ()),
                                           event.occurredOn(),
                                           UserId.fromString(event.userId))

        doNothing().whenever(repository).save(expected)

        handler.on(event)

        verify(repository).save(expected)
    }

}