package com.kotato.context.ecommerce.modules.cart.domain.add

import java.math.BigDecimal

data class AddCartItemCommand(val id: String,
                              val itemId: String,
                              val quantity: Int,
                              val price: BigDecimal,
                              val currency: String)