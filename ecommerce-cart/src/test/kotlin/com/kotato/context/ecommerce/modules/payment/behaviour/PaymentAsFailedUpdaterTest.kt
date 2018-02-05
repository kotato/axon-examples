package com.kotato.context.ecommerce.modules.payment.behaviour

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus
import com.kotato.context.ecommerce.modules.payment.domain.Payment
import com.kotato.context.ecommerce.modules.payment.domain.PaymentNotFound
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus
import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.PaymentAsFailedUpdater
import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.PaymentFailedEvent
import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.UpdatePaymentAsFailedCommand
import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.UpdatePaymentAsFailedCommandHandler
import com.kotato.context.ecommerce.modules.payment.infrastructure.AxonPaymentRepository
import com.kotato.context.ecommerce.modules.payment.stub.PaymentCreatedEventStub
import com.kotato.context.ecommerce.modules.payment.stub.UpdatePaymentAsFailedCommandStub
import com.kotato.shared.expectDomainEvent
import com.kotato.shared.loadAggregate
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class PaymentAsFailedUpdaterTest {

    private val fixture = AggregateTestFixture(Payment::class.java)
    private val repository = AxonPaymentRepository(fixture.repository)
    private val service = PaymentAsFailedUpdater(repository)
    private val handler = UpdatePaymentAsFailedCommandHandler(service)

    @BeforeEach
    fun setUp() {
        fixture.registerAnnotatedCommandHandler(handler)
    }

    @Test
    fun `it should update payment as failed`() {
        val givenEvent = PaymentCreatedEventStub.random()
        val command = UpdatePaymentAsFailedCommand(givenEvent.aggregateId)
        val expected = PaymentFailedEvent(givenEvent.aggregateId,
                                          ZonedDateTime.now())

        fixture.given(givenEvent)
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        assertSimilar(fixture.loadAggregate(givenEvent.aggregateId).status, PaymentStatus.FAILED)
    }

    @Test
    fun `it should throw exception when trying to update payment as failed`() {
        val command = UpdatePaymentAsFailedCommandStub.random()

        fixture.givenNoPriorActivity()
                .`when`(command)
                .expectException(PaymentNotFound::class.java)
    }

}