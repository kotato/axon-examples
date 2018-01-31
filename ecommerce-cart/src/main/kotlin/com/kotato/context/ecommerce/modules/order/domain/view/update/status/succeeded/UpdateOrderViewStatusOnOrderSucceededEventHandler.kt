package com.kotato.context.ecommerce.modules.order.domain.view.update.status.succeeded

import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.update.status.succeeded.OrderSucceededEvent
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class UpdateOrderViewStatusOnOrderSucceededEventHandler(
        private val updater: OrderViewStatusAsSucceededUpdater) {

    @EventHandler
    open fun on(event: OrderSucceededEvent) {
        updater(OrderId.fromString(event.aggregateId))
    }
}