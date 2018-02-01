package com.kotato.context.ecommerce.modules.order.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems

fun OrderView.toResponse() =
        OrderResponse(id.asString(),
                      createdOn,
                      userId.asString(),
                      cartId.asString(),
                      paymentId.asString(),
                      status.name,
                      cartItems.toSerializedCartItems())