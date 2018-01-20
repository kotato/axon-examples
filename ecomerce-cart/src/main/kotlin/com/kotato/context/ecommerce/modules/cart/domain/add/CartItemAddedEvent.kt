package com.kotato.context.ecommerce.modules.cart.domain.add

import com.kotato.shared.domainevent.DomainEvent
import java.math.BigDecimal
import java.time.ZonedDateTime


data class CartItemAddedEvent(private val aggregateId: String,
                              private val occurredOn: ZonedDateTime,
                              val itemId: String,
                              val quantity: Int,
                              val price: BigDecimal,
                              val currency: String) : DomainEvent {
    override fun aggregateId(): String = this.aggregateId
    override fun occurredOn(): ZonedDateTime = this.occurredOn
}