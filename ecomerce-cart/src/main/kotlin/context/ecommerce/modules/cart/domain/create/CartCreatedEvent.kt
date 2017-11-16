package context.ecommerce.modules.cart.domain.create

import shared.domainevent.DomainEvent

data class CartCreatedEvent(private val aggregateId: String) : DomainEvent {
    override fun aggregateId(): String = this.aggregateId
}