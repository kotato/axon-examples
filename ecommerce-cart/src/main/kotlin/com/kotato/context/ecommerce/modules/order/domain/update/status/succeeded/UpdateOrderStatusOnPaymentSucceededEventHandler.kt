package com.kotato.context.ecommerce.modules.order.domain.update.status.succeeded

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded.PaymentSucceededEvent
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class UpdateOrderStatusOnPaymentSucceededEventHandler(private val updater: OrderStatusAsSucceededByPaymentIdUpdater) {

    @EventHandler
    open fun on(event: PaymentSucceededEvent) {
        updater(PaymentId.fromString(event.aggregateId))
    }
}