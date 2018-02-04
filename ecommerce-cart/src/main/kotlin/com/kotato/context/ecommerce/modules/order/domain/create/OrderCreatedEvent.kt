package com.kotato.context.ecommerce.modules.order.domain.create

import com.kotato.shared.domainevent.DomainEvent
import java.math.BigDecimal
import java.time.ZonedDateTime


data class OrderCreatedEvent(val aggregateId: String,
                             val occurredOn: ZonedDateTime,
                             val cartId: String,
                             val paymentId: String,
                             val userId: String,
                             val price: BigDecimal,
                             val currency: String) : DomainEvent {

    override fun aggregateId() = aggregateId
    override fun occurredOn() = occurredOn

}