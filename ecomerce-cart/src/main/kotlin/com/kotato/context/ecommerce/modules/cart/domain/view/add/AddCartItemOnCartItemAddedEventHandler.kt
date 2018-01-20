package com.kotato.context.ecommerce.modules.cart.domain.view.add

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.add.CartItemAddedEvent
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import org.axonframework.eventhandling.EventHandler
import org.javamoney.moneta.Money
import javax.inject.Named

@Named
open class AddCartItemOnCartItemAddedEventHandler(private val adder: CartViewCartItemAdder) {

    @EventHandler
    fun on(event: CartItemAddedEvent) {
        adder(CartId.fromString(event.aggregateId()), event.toCartItem(), event.quantity)
    }

    private fun CartItemAddedEvent.toCartItem() =
            CartItem(itemId = ItemId.fromString(this.itemId),
                     price = Money.of(this.price, this.currency))
}