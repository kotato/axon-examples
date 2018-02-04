package com.kotato.context.ecommerce.modules.cart.adapter.find

import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import java.time.ZonedDateTime

data class CartRestResponse(val id: String,
                            val createdOn: ZonedDateTime,
                            val userId: String,
                            val cartItems: SerializedCartItems,
                            val checkout: Boolean)