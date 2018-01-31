package com.kotato.context.ecommerce.modules.order.domain.update.status.succeeded

import com.kotato.shared.domainevent.DomainEvent
import java.time.ZonedDateTime

data class OrderSucceededEvent(val aggregateId: String,
                            val occurredOn: ZonedDateTime) : DomainEvent {

    override fun aggregateId() = this.aggregateId
    override fun occurredOn() = this.occurredOn

}