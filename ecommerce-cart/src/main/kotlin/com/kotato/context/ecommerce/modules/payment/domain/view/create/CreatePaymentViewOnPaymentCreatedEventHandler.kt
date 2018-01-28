package com.kotato.context.ecommerce.modules.payment.domain.view.create

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.create.PaymentCreatedEvent
import com.kotato.shared.money.Money
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class CreatePaymentViewOnPaymentCreatedEventHandler(private val creator: PaymentViewCreator) {

    @EventHandler
    open fun on(event: PaymentCreatedEvent) {
        creator(PaymentId.fromString(event.aggregateId),
                event.occurredOn,
                Money.of(event.price, event.currency))
    }
}