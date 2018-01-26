package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.order.behaviour.view.OrderView
import com.kotato.context.ecommerce.modules.order.behaviour.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.order.behaviour.view.create.CreateOrderViewOnOrderCreatedEventHandler
import com.kotato.context.ecommerce.modules.order.behaviour.view.create.OrderViewCreator
import com.kotato.context.ecommerce.modules.order.stub.OrderCreatedEventStub
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

class OrderViewCreatorTest {

    private val repository: OrderViewRepository = mock()
    private val creator = OrderViewCreator(repository)
    private val handler = CreateOrderViewOnOrderCreatedEventHandler(creator)

    @Test
    fun `it should create order view on order created event raised`() {
        val event = OrderCreatedEventStub.random()
        val expected = OrderView(OrderId.fromString(event.aggregateId),
                                 event.occurredOn,
                                 UserId.fromString(event.userId),
                                 CartId.fromString(event.cartId),
                                 PaymentId.fromString(event.paymentId),
                                 OrderStatus.IN_PROGRESS,
                                 event.cartItems.toCartItems())

        doNothing().whenever(repository).save(expected)

        handler.on(event)

        verify(repository).save(expected)
    }

}