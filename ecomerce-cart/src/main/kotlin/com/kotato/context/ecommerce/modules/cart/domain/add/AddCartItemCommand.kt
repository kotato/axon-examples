package com.kotato.context.ecommerce.modules.cart.domain.add

import com.kotato.cqrs.domain.command.Command
import java.math.BigDecimal

data class AddCartItemCommand(val cartId: String,
                              val itemId: String,
                              val quantity: Int,
                              val price: BigDecimal,
                              val currency: String): Command