package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.context.ecommerce.modules.cart.domain.add.CartItemAddedEvent
import com.kotato.context.ecommerce.modules.cart.domain.create.CartCreatedEvent
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import org.javamoney.moneta.Money
import java.time.ZonedDateTime

@Aggregate
class Cart {

    @AggregateIdentifier
    lateinit var id: CartId
        private set
    lateinit var userId: UserId
        private set
    var cartItems: CartItems = listOf()
        private set

    @EventSourcingHandler
    fun on(event: CartCreatedEvent) {
        this.id = event.aggregateId().let { CartId.fromString(it) }
        this.userId = event.userId.let { UserId.fromString(it) }
    }

    @EventSourcingHandler
    fun on(event: CartItemAddedEvent) {
        CartItem(itemId = ItemId.fromString(event.itemId),
                 quantity = event.quantity,
                 price = Money.of(event.price, event.currency))
                .let { this.cartItems = this.cartItems.add(it) }
    }
    
    fun addItem(cartItem: CartItem) {
        AggregateLifecycle.apply(CartItemAddedEvent(aggregateId = this.id.asString(),
                                                    occurredOn = ZonedDateTime.now(),
                                                    itemId = cartItem.itemId.asString(),
                                                    quantity = cartItem.quantity,
                                                    price = cartItem.price.numberStripped,
                                                    currency = cartItem.price.currency.currencyCode))
    }

    companion object {
        fun create(id: CartId, userId: UserId) {
            AggregateLifecycle.apply(CartCreatedEvent(aggregateId = id.asString(),
                                                      occurredOn = ZonedDateTime.now(),
                                                      userId = userId.asString()))
        }
    }
}