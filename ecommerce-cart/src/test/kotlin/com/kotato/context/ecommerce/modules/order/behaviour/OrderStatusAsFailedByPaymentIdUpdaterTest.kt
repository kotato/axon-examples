package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.order.domain.Order
import com.kotato.context.ecommerce.modules.order.domain.OrderNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus
import com.kotato.context.ecommerce.modules.order.domain.update.status.failed.OrderStatusAsFailedByPaymentIdUpdater
import com.kotato.context.ecommerce.modules.order.domain.update.status.failed.UpdateOrderStatusOnPaymentFailedEventHandler
import com.kotato.context.ecommerce.modules.order.domain.view.OrderResponse
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewByPaymentIdNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id.FindOrderByPaymentIdQuery
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id.FindOrderByPaymentIdQueryAsker
import com.kotato.context.ecommerce.modules.order.infrastructure.AxonOrderRepository
import com.kotato.context.ecommerce.modules.order.stub.OrderCreatedEventStub
import com.kotato.context.ecommerce.modules.order.stub.OrderFailedEventStub
import com.kotato.context.ecommerce.modules.order.stub.OrderResponseStub
import com.kotato.context.ecommerce.modules.payment.stub.PaymentFailedEventStub
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.ask
import com.kotato.shared.MockitoHelper.Companion.mockObject
import com.kotato.shared.expectDomainEvent
import com.kotato.shared.loadAggregate
import com.kotato.shared.whenLambda
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.whenever
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class OrderStatusAsFailedByPaymentIdUpdaterTest {

    private val fixture = AggregateTestFixture(Order::class.java)
    private val repository = AxonOrderRepository(fixture.repository)
    private val queryBus: QueryBus = mockObject()
    private val service = OrderStatusAsFailedByPaymentIdUpdater(repository, FindOrderByPaymentIdQueryAsker(queryBus))
    private val handler = UpdateOrderStatusOnPaymentFailedEventHandler(service)

    @Test
    fun `it should update an order as failed`() {
        val givenEvent = OrderCreatedEventStub.random()
        val event = PaymentFailedEventStub.random(aggregateId = givenEvent.paymentId)
        val expected = OrderFailedEventStub.random(aggregateId = givenEvent.aggregateId)
        val response = OrderResponseStub.random(id = givenEvent.aggregateId,
                                                paymentId = givenEvent.paymentId)

        shouldSearchOrder(event.aggregateId, response)

        fixture.given(givenEvent)
                .whenLambda { handler.on(event) }
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        assertSimilar(fixture.loadAggregate(givenEvent.aggregateId).orderStatus, OrderStatus.FAILED)
    }

    @Test
    fun `it should throw OrderNotFoundException when trying to update order as failed`() {
        val event = PaymentFailedEventStub.random()
        val response = OrderResponseStub.random(paymentId = event.aggregateId)

        shouldSearchOrder(event.aggregateId, response)

        fixture.givenNoPriorActivity()
                .whenLambda({ handler.on(event) }, OrderNotFoundException::class)
    }

    @Test
    fun `it should throw OrderViewByPaymentIdNotFoundException when trying to update order as failed`() {
        val event = PaymentFailedEventStub.random()

        shouldThrowOrderViewByPaymentIdNotFoundException(event.aggregateId)

        fixture.givenNoPriorActivity()
                .whenLambda({ handler.on(event) }, OrderViewByPaymentIdNotFoundException::class)
    }

    private fun shouldSearchOrder(id: String, response: OrderResponse?) {
        doReturn(response).`when`(queryBus).ask<OrderResponse>(FindOrderByPaymentIdQuery(id))
    }

    private fun shouldThrowOrderViewByPaymentIdNotFoundException(id: String) {
        doThrow(OrderViewByPaymentIdNotFoundException::class)
                .whenever(queryBus)
                .ask<OrderResponse>(FindOrderByPaymentIdQuery(id))
    }
}
