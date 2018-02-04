package com.kotato.context.ecommerce.modules.order.adapter.find

import com.kotato.context.ecommerce.modules.order.domain.view.OrderResponse

fun OrderResponse.toRestResponse() =
        OrderRestResponse(id, createdOn, userId, cartId, paymentId, status, cartItems)