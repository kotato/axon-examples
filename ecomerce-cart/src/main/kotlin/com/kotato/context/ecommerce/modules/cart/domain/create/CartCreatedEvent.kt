package com.kotato.context.ecommerce.modules.cart.domain.create

import com.kotato.shared.domainevent.DomainEvent

data class CartCreatedEvent(private val aggregateId: String, val userId: String) : DomainEvent {
    override fun aggregateId(): String = this.aggregateId
}