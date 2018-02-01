package com.kotato.context.ecommerce.modules.order.domain.update.status.failed

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.PaymentFailedEvent
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class UpdateOrderStatusOnPaymentFailedEventHandler(private val updater: OrderStatusAsFailedByPaymentIdUpdater) {

    @EventHandler
    open fun on(event: PaymentFailedEvent) {
        updater(PaymentId.fromString(event.aggregateId))
    }
}