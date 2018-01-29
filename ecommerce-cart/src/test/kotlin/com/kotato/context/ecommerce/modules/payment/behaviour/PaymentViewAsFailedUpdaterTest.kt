package com.kotato.context.ecommerce.modules.payment.behaviour

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentView
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewNotFound
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewRepository
import com.kotato.context.ecommerce.modules.payment.domain.view.update.status.failed.PaymentViewStatusAsFailedUpdater
import com.kotato.context.ecommerce.modules.payment.domain.view.update.status.failed.UpdatePaymentViewStatusOnPaymentFailedEventHandler
import com.kotato.context.ecommerce.modules.payment.stub.PaymentFailedEventStub
import com.kotato.context.ecommerce.modules.payment.stub.PaymentViewStub
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class PaymentViewAsFailedUpdaterTest {

    private val repository: PaymentViewRepository = mock()
    private val creator = PaymentViewStatusAsFailedUpdater(repository)
    private val handler = UpdatePaymentViewStatusOnPaymentFailedEventHandler(creator)

    @Test
    fun `it should update payment as failed`() {
        val view = PaymentViewStub.random()
        val event = PaymentFailedEventStub.random(aggregateId = view.id.asString())
        val expected = view.copy(status = PaymentStatus.FAILED)

        shouldSearchView(view.id, view)
        shouldSaveView(expected)

        handler.on(event)

        verify(repository).save(eq(expected))
    }

    @Test
    fun `it should throw exception when trying to update payment as failed`() {
        val event = PaymentFailedEventStub.random()
        shouldSearchView(PaymentId.fromString(event.aggregateId), null)
        assertFailsWith<PaymentViewNotFound> {
            handler.on(event)
        }
    }

    private fun shouldSearchView(id: PaymentId, view: PaymentView?) {
        doReturn(view).whenever(repository).search(id)
    }

    private fun shouldSaveView(view: PaymentView) {
        doNothing().whenever(repository).save(view)
    }

}