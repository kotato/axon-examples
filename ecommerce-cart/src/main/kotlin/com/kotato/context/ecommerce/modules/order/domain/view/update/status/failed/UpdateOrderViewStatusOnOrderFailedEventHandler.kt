package com.kotato.context.ecommerce.modules.order.domain.view.update.status.failed

import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.update.status.failed.OrderFailedEvent
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class UpdateOrderViewStatusOnOrderFailedEventHandler(
        private val updater: OrderViewStatusAsFailedUpdater) {

    @EventHandler
    open fun on(event: OrderFailedEvent) {
        updater(OrderId.fromString(event.aggregateId))
    }
}