package com.kotato.context.ecommerce.modules.order.behaviour.create

import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import com.kotato.shared.domainevent.DomainEvent
import java.time.ZonedDateTime


data class OrderCreatedEvent(val aggregateId: String,
                             val occurredOn: ZonedDateTime,
                             val cartId: String,
                             val paymentId: String,
                             val cartItems: SerializedCartItems) : DomainEvent {

    override fun aggregateId() = this.aggregateId
    override fun occurredOn() = this.occurredOn

}