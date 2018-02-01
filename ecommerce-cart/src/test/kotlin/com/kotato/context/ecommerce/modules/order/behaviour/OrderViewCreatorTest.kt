package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.cart.domain.view.CartResponse
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQuery
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQueryAsker
import com.kotato.context.ecommerce.modules.cart.stub.CartResponseStub
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus
import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.order.domain.view.create.CreateOrderViewOnOrderCreatedEventHandler
import com.kotato.context.ecommerce.modules.order.domain.view.create.OrderViewCreator
import com.kotato.context.ecommerce.modules.order.stub.OrderCreatedEventStub
import com.kotato.context.ecommerce.modules.order.stub.OrderViewStub
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.ask
import com.kotato.shared.MockitoHelper.Companion.mockObject
import com.kotato.shared.money.Money
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class OrderViewCreatorTest {

    private val repository: OrderViewRepository = mockObject()
    private val queryBus: QueryBus = mockObject()
    private val asker = FindCartQueryAsker(queryBus)
    private val creator = OrderViewCreator(repository, asker)
    private val handler = CreateOrderViewOnOrderCreatedEventHandler(creator)

    @Test
    fun `it should create order view on order created event raised`() {
        val event = OrderCreatedEventStub.random()
        val response = CartResponseStub.random(cartId = event.cartId)
        val expected = OrderViewStub.random(id = OrderId.fromString(event.aggregateId),
                                            occurredOn = event.occurredOn,
                                            userId = UserId.fromString(event.userId),
                                            cartId = CartId.fromString(event.cartId),
                                            paymentId = PaymentId.fromString(event.paymentId),
                                            price = Money.of(event.price, event.currency),
                                            status = OrderStatus.IN_PROGRESS,
                                            cartItems = response.cartItems.toCartItems())

        shouldSearchCart(event.cartId, response)
        shouldSaveView(expected)

        handler.on(event)

        verify(repository).save(expected)
    }

    @Test
    fun `it should throw exception when trying to save order view`() {
        val event = OrderCreatedEventStub.random()

        shouldThrowCartViewNotFoundException(event.cartId)

        assertFailsWith<CartViewNotFoundException> {
            handler.on(event)
        }

    }

    private fun shouldSaveView(expected: OrderView) {
        doNothing().whenever(repository).save(expected)
    }

    private fun shouldSearchCart(id: String, response: CartResponse) {
        doReturn(response)
                .whenever(queryBus)
                .ask<CartResponse>(FindCartQuery(id))
    }

    private fun shouldThrowCartViewNotFoundException(id: String) {
        doThrow(CartViewNotFoundException::class)
                .whenever(queryBus)
                .ask<CartResponse>(FindCartQuery(id))
    }

}