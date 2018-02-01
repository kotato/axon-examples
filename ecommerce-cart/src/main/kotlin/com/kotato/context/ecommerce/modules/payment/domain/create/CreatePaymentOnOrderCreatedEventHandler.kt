package com.kotato.context.ecommerce.modules.payment.domain.create

import com.kotato.context.ecommerce.modules.order.domain.create.OrderCreatedEvent
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.shared.money.Money
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class CreatePaymentOnOrderCreatedEventHandler(private val creator: PaymentCreator) {

    @EventHandler
    open fun on(event: OrderCreatedEvent) {
        creator(PaymentId.fromString(event.paymentId), Money.of(event.price, event.currency))
    }
}