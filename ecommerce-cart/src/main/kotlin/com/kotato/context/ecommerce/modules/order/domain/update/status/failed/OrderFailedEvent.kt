package com.kotato.context.ecommerce.modules.order.domain.update.status.failed

import com.kotato.shared.domainevent.DomainEvent
import java.time.ZonedDateTime

data class OrderFailedEvent(val aggregateId: String,
                             val occurredOn: ZonedDateTime) : DomainEvent {

    override fun aggregateId() = this.aggregateId
    override fun occurredOn() = this.occurredOn

}