package com.kotato.context.ecommerce.modules.cart.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems

fun CartView.toResponse() =
        CartResponse(id.asString(),
                     createdOn,
                     userId.asString(),
                     cartItems.toSerializedCartItems(),
                     checkout)