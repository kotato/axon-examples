package com.kotato.context.ecommerce.modules.payment.domain.view.update.status.succeeded

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded.PaymentSucceededEvent
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class UpdatePaymentViewStatusOnPaymentSucceededEventHandler(
        private val updater: PaymentViewStatusAsSucceededUpdater) {

    @EventHandler
    open fun on(event: PaymentSucceededEvent) {
        updater(PaymentId.fromString(event.aggregateId))
    }
}