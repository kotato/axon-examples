package com.kotato.context.ecommerce.modules.payment.domain.view.update.status.failed

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.PaymentFailedEvent
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class UpdatePaymentViewStatusOnPaymentFailedEventHandler(
        private val updater: PaymentViewStatusAsFailedUpdater) {

    @EventHandler
    open fun on(event: PaymentFailedEvent) {
        updater(PaymentId.fromString(event.aggregateId))
    }
}