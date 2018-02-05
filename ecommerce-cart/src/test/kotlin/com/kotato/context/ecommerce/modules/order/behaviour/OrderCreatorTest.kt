package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.calculatePrice
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.cart.domain.view.CartResponse
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQuery
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQueryAsker
import com.kotato.context.ecommerce.modules.cart.stub.CartCheckedOutEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CartResponseStub
import com.kotato.context.ecommerce.modules.order.domain.Order
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus
import com.kotato.context.ecommerce.modules.order.domain.create.CreateOrderOnCartCheckedOutEventHandler
import com.kotato.context.ecommerce.modules.order.domain.create.OrderCreatedEvent
import com.kotato.context.ecommerce.modules.order.domain.create.OrderCreator
import com.kotato.context.ecommerce.modules.order.infrastructure.AxonOrderRepository
import com.kotato.context.ecommerce.modules.order.stub.OrderCreatedEventStub
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.ask
import com.kotato.shared.getPublishedEvents
import com.kotato.shared.loadAggregate
import com.kotato.shared.whenLambda
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class OrderCreatorTest {

    private val fixture = AggregateTestFixture(Order::class.java)
    private val repository = AxonOrderRepository(fixture.repository)
    private val queryBus: QueryBus = mock()
    private val service = OrderCreator(repository, FindCartQueryAsker(queryBus))
    private val handler = CreateOrderOnCartCheckedOutEventHandler(service)

    @Test
    fun `it should create an order`() {
        val event = CartCheckedOutEventStub.random()
        val response = CartResponseStub.random(cartId = event.aggregateId)
        val price = response.cartItems.toCartItems().calculatePrice()
        val expected = OrderCreatedEventStub.random(aggregateId = event.orderId,
                                                    cartId = event.aggregateId,
                                                    userId = response.userId,
                                                    price = price.amount,
                                                    currency = price.currency)

        shouldFindCart(event.aggregateId, response)

        fixture.givenNoPriorActivity()
                .whenLambda({ handler.on(event) })
                .expectSuccessfulHandlerExecution()
                .getPublishedEvents()
                .let {
                    assertTrue { it.size == 1 }
                    val actual = it.first() as OrderCreatedEvent
                    assertSimilar(actual.aggregateId(), expected.aggregateId)
                    assertSimilar(actual.cartId, expected.cartId)
                    assertSimilar(actual.price, expected.price)
                    assertSimilar(actual.currency, expected.currency)
                }

        fixture.loadAggregate(event.orderId).let {
            assertSimilar(it.orderId, OrderId.fromString(event.orderId))
            assertSimilar(it.cartId, CartId.fromString(event.aggregateId))
            assertSimilar(it.orderStatus, OrderStatus.IN_PROGRESS)
            assertSimilar(it.price, price)
            assertSimilar(it.userId, UserId.fromString(expected.userId))
            assertNotNull(it.paymentId)
        }
    }

    private fun shouldFindCart(id: String,
                               response: CartResponse?) {
        doReturn(response).whenever(queryBus).ask<CartResponse>(FindCartQuery(id))
    }

}