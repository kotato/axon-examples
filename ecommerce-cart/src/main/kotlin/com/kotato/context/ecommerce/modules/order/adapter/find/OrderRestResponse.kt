package com.kotato.context.ecommerce.modules.order.adapter.find

import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import java.time.ZonedDateTime

data class OrderRestResponse(val id: String,
                             val createdOn: ZonedDateTime,
                             val userId: String,
                             val cartId: String,
                             val paymentId: String,
                             val status: String,
                             val cartItems: SerializedCartItems)