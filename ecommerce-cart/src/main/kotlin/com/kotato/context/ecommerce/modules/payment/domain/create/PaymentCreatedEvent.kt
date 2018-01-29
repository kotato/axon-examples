package com.kotato.context.ecommerce.modules.payment.domain.create

import com.kotato.shared.domainevent.DomainEvent
import java.math.BigDecimal
import java.time.ZonedDateTime

data class PaymentCreatedEvent(val aggregateId: String,
                               val occurredOn: ZonedDateTime,
                               val price: BigDecimal,
                               val currency: String) : DomainEvent {

    override fun aggregateId() = aggregateId
    override fun occurredOn() = occurredOn

}