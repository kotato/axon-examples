package com.kotato.context.ecommerce.modules.cart.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import java.time.ZonedDateTime

data class CartResponse(val cartId: String,
                        val createdOn: ZonedDateTime,
                        val userId: String,
                        val cartItems: SerializedCartItems,
                        val checkout: Boolean)