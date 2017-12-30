package com.kotato.context.ecommerce.modules.cart.domain.create

import com.kotato.shared.domainevent.DomainEvent
import java.time.ZonedDateTime

data class CartCreatedEvent(private val aggregateId: String,
                            private val occurredOn: ZonedDateTime,
                            val userId: String) : DomainEvent {
    override fun aggregateId(): String = this.aggregateId
    override fun occurredOn(): ZonedDateTime = this.occurredOn
}