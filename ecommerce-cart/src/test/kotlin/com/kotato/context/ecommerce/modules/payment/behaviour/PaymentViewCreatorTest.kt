package com.kotato.context.ecommerce.modules.payment.behaviour

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentView
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewRepository
import com.kotato.context.ecommerce.modules.payment.domain.view.create.CreatePaymentViewOnPaymentCreatedEventHandler
import com.kotato.context.ecommerce.modules.payment.domain.view.create.PaymentViewCreator
import com.kotato.context.ecommerce.modules.payment.stub.PaymentCreatedEventStub
import com.kotato.shared.money.Money
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

class PaymentViewCreatorTest {

    private val repository: PaymentViewRepository = mock()
    private val creator = PaymentViewCreator(repository)
    private val handler = CreatePaymentViewOnPaymentCreatedEventHandler(creator)

    @Test
    fun `it should create order view on order created event raised`() {
        val event = PaymentCreatedEventStub.random()
        val expected = PaymentView(PaymentId.fromString(event.aggregateId),
                                   event.occurredOn,
                                   Money.of(event.price, event.currency),
                                   PaymentStatus.PENDING)

        shouldSaveEntity(expected)

        handler.on(event)

        verify(repository).save(expected)
    }

    private fun shouldSaveEntity(expected: PaymentView) {
        doNothing().whenever(repository).save(expected)
    }

}