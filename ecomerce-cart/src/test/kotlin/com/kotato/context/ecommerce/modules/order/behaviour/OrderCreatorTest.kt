package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.view.CartResponse
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQuery
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQueryAsker
import com.kotato.context.ecommerce.modules.cart.stub.CartCheckedOutEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CartResponseStub
import com.kotato.context.ecommerce.modules.order.behaviour.create.CreateOrderOnCartCheckedOutEventHandler
import com.kotato.context.ecommerce.modules.order.behaviour.create.OrderCreatedEvent
import com.kotato.context.ecommerce.modules.order.behaviour.create.OrderCreator
import com.kotato.context.ecommerce.modules.order.infrastructure.AxonOrderRepository
import com.kotato.context.ecommerce.modules.order.stub.OrderCreatedEventStub
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.ask
import com.kotato.shared.getPublishedEvents
import com.kotato.shared.whenLambda
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.Test
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
        val expected = OrderCreatedEventStub.random(aggregateId = event.orderId,
                                                    cartId = event.aggregateId)
        val response = CartResponseStub.random(cartId = expected.cartId,
                                               cartItems = expected.cartItems)

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
                    assertSimilar(actual.cartItems, expected.cartItems)
                }
    }

    private fun shouldFindCart(id: String,
                               response: CartResponse?) {
        doReturn(response).whenever(queryBus).ask<CartResponse>(FindCartQuery(id))
    }

}