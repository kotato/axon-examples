package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.context.ecommerce.modules.cart.domain.add.CartItemAddedEvent
import com.kotato.context.ecommerce.modules.cart.domain.create.CartCreatedEvent
import com.kotato.context.ecommerce.modules.cart.domain.subtract.CartItemIsNotInCartException
import com.kotato.context.ecommerce.modules.cart.domain.subtract.CartItemSubtractedEvent
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.shared.money.Money
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import java.time.ZonedDateTime

@Aggregate
class Cart {

    @AggregateIdentifier
    lateinit var id: CartId
        private set
    lateinit var userId: UserId
        private set
    var cartItems: CartItems = mapOf()
        private set

    @EventSourcingHandler
    fun on(event: CartCreatedEvent) {
        this.id = event.aggregateId().let { CartId.fromString(it) }
        this.userId = event.userId.let { UserId.fromString(it) }
    }

    @EventSourcingHandler
    fun on(event: CartItemAddedEvent) {
        CartItem(itemId = ItemId.fromString(event.itemId),
                 price = Money.of(event.price, event.currency))
                .let { this.cartItems = this.cartItems.add(it, event.quantity) }
    }

    @EventSourcingHandler
    fun on(event: CartItemSubtractedEvent) {
        CartItem(itemId = ItemId.fromString(event.itemId),
                 price = Money.of(event.price, event.currency))
                .let { this.cartItems = this.cartItems.subtract(it, event.quantity) }
    }

    fun addItem(cartItem: CartItem, quantity: Int) {
        apply(CartItemAddedEvent(aggregateId = this.id.asString(),
                                 occurredOn = ZonedDateTime.now(),
                                 itemId = cartItem.itemId.asString(),
                                 quantity = quantity,
                                 price = cartItem.price.amount,
                                 currency = cartItem.price.currency))
    }

    fun subtractItem(cartItem: CartItem, quantity: Int) {
        guardItemExistsInCart(cartItem)
        apply(CartItemSubtractedEvent(aggregateId = this.id.asString(),
                                      occurredOn = ZonedDateTime.now(),
                                      itemId = cartItem.itemId.asString(),
                                      quantity = if (cartItems[cartItem]!!.amount < quantity) cartItems[cartItem]!!.amount else quantity,
                                      price = cartItem.price.amount,
                                      currency = cartItem.price.currency))
    }

    private fun guardItemExistsInCart(cartItem: CartItem) {
        if (!cartItems.keys.contains(cartItem)) throw CartItemIsNotInCartException()
    }

    companion object {
        fun create(id: CartId, userId: UserId) {
            apply(CartCreatedEvent(aggregateId = id.asString(),
                                   occurredOn = ZonedDateTime.now(),
                                   userId = userId.asString()))
        }
    }
}