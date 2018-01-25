package com.kotato.context.ecommerce.modules.cart.domain.checkout

import com.kotato.shared.domainevent.DomainEvent
import java.time.ZonedDateTime


data class CartCheckedOutEvent(val aggregateId: String,
                               val occurredOn: ZonedDateTime,
                               val orderId: String) : DomainEvent {
    override fun aggregateId(): String = this.aggregateId
    override fun occurredOn(): ZonedDateTime = this.occurredOn
}