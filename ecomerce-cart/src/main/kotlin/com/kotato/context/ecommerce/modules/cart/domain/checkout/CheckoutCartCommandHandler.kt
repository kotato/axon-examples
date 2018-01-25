package com.kotato.context.ecommerce.modules.cart.domain.checkout

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.cqrs.domain.command.CommandHandler
import javax.inject.Named

@Named
open class CheckoutCartCommandHandler(private val checkout: CartCheckout) {

    @CommandHandler
    fun on(command: CheckoutCartCommand) {
        checkout(CartId.fromString(command.cartId),
                 OrderId.fromString(command.orderId))
    }
}
