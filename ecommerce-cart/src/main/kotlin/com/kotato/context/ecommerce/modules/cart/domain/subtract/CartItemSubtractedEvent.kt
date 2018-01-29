package com.kotato.context.ecommerce.modules.cart.domain.subtract

import com.kotato.shared.domainevent.DomainEvent
import java.math.BigDecimal
import java.time.ZonedDateTime

data class CartItemSubtractedEvent(val aggregateId: String,
                                   val occurredOn: ZonedDateTime,
                                   val itemId: String,
                                   val quantity: Int,
                                   val price: BigDecimal,
                                   val currency: String) : DomainEvent {
    override fun aggregateId(): String = aggregateId
    override fun occurredOn(): ZonedDateTime = occurredOn
}