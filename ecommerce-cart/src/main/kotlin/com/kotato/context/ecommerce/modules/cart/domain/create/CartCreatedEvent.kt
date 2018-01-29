package com.kotato.context.ecommerce.modules.cart.domain.create

import com.kotato.shared.domainevent.DomainEvent
import java.time.ZonedDateTime

data class CartCreatedEvent(val aggregateId: String,
                            val occurredOn: ZonedDateTime,
                            val userId: String) : DomainEvent {
    override fun aggregateId(): String = aggregateId
    override fun occurredOn(): ZonedDateTime = occurredOn
}