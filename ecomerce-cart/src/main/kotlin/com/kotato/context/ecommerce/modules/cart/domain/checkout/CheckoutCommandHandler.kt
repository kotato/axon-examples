package com.kotato.context.ecommerce.modules.cart.domain.checkout

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.cqrs.domain.command.CommandHandler
import javax.inject.Named

@Named
open class CheckoutCommandHandler(private val checkout: CartCheckout) {

    @CommandHandler
    fun on(command: CheckoutCommand) {
        checkout(CartId.fromString(command.cartId))
    }
}
