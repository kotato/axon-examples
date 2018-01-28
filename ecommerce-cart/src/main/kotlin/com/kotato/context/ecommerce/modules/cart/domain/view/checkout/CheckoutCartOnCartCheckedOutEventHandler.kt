package com.kotato.context.ecommerce.modules.cart.domain.view.checkout

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.checkout.CartCheckedOutEvent
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class CheckoutCartOnCartCheckedOutEventHandler(private val checkout: CartViewCheckout) {

    @EventHandler
    fun on(event: CartCheckedOutEvent) {
        checkout(CartId.fromString(event.aggregateId()))
    }
}