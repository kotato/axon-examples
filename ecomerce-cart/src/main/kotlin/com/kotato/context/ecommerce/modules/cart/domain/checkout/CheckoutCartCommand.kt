package com.kotato.context.ecommerce.modules.cart.domain.checkout

import com.kotato.cqrs.domain.command.Command

data class CheckoutCartCommand(val cartId: String,
                               val orderId: String) : Command
