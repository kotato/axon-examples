package com.kotato.context.ecommerce.modules.payment.behaviour

import com.kotato.context.ecommerce.modules.payment.domain.Payment
import com.kotato.context.ecommerce.modules.payment.domain.PaymentNotFound
import com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded.PaymentAsSucceededUpdater
import com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded.PaymentSucceededEvent
import com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded.UpdatePaymentAsSucceededCommand
import com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded.UpdatePaymentAsSucceededCommandHandler
import com.kotato.context.ecommerce.modules.payment.infrastructure.AxonPaymentRepository
import com.kotato.context.ecommerce.modules.payment.stub.PaymentCreatedEventStub
import com.kotato.context.ecommerce.modules.payment.stub.UpdatePaymentAsSucceededCommandStub
import com.kotato.shared.expectDomainEvent
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class PaymentAsSucceededUpdaterTest {

    private val fixture = AggregateTestFixture(Payment::class.java)
    private val repository = AxonPaymentRepository(fixture.repository)
    private val service = PaymentAsSucceededUpdater(repository)
    private val handler = UpdatePaymentAsSucceededCommandHandler(service)

    @BeforeEach
    fun setUp() {
        fixture.registerAnnotatedCommandHandler(handler)
    }

    @Test
    fun `it should update payment as succeeded`() {
        val givenEvent = PaymentCreatedEventStub.random()
        val command = UpdatePaymentAsSucceededCommand(givenEvent.aggregateId)
        val expected = PaymentSucceededEvent(givenEvent.aggregateId,
                                             ZonedDateTime.now())

        fixture.given(givenEvent)
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

    }

    @Test
    fun `it should throw exception when trying to update payment as succeeded`() {
        val command = UpdatePaymentAsSucceededCommandStub.random()

        fixture.givenNoPriorActivity()
                .`when`(command)
                .expectException(PaymentNotFound::class.java)
    }

}