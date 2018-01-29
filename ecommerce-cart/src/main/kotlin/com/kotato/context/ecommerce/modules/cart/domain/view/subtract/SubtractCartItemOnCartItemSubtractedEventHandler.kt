package com.kotato.context.ecommerce.modules.cart.domain.view.subtract

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.subtract.CartItemSubtractedEvent
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.shared.money.Money
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class SubtractCartItemOnCartItemSubtractedEventHandler(private val subtractor: CartViewCartItemSubtractor) {

    @EventHandler
    fun on(event: CartItemSubtractedEvent) {
        subtractor(CartId.fromString(event.aggregateId()), event.toCartItem(), event.quantity)
    }

    private fun CartItemSubtractedEvent.toCartItem() =
            CartItem(itemId = ItemId.fromString(itemId),
                     price = Money.of(price, currency))
}