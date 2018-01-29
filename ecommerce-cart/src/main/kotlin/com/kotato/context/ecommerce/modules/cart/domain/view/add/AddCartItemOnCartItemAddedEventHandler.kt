package com.kotato.context.ecommerce.modules.cart.domain.view.add

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.add.CartItemAddedEvent
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.shared.money.Money
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class AddCartItemOnCartItemAddedEventHandler(private val adder: CartViewCartItemAdder) {

    @EventHandler
    fun on(event: CartItemAddedEvent) {
        adder(CartId.fromString(event.aggregateId()), event.toCartItem(), event.quantity)
    }

    private fun CartItemAddedEvent.toCartItem() =
            CartItem(itemId = ItemId.fromString(itemId),
                     price = Money.of(price, currency))
}