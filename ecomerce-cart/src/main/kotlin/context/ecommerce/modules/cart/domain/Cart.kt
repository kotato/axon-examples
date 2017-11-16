package context.ecommerce.modules.cart.domain

import context.ecommerce.modules.cart.domain.create.CartCreatedEvent
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Cart {

    @AggregateIdentifier
    lateinit var id: CartId
        private set

    @EventSourcingHandler
    fun on(event: CartCreatedEvent) {
        this.id = event.aggregateId().let { CartId.fromString(it) }
    }

    companion object {
        fun create(id: CartId) {
            AggregateLifecycle.apply(CartCreatedEvent(aggregateId = id.asString()))
        }
    }
}