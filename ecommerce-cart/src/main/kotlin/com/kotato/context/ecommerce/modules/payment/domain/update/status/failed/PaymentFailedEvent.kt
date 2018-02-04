package com.kotato.context.ecommerce.modules.payment.domain.update.status.failed

import com.kotato.shared.domainevent.DomainEvent
import java.time.ZonedDateTime

data class PaymentFailedEvent(val aggregateId: String,
                              val occurredOn: ZonedDateTime) : DomainEvent {
    override fun aggregateId(): String = aggregateId
    override fun occurredOn(): ZonedDateTime = occurredOn
}