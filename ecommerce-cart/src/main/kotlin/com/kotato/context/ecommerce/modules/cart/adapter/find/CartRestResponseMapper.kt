package com.kotato.context.ecommerce.modules.cart.adapter.find

import com.kotato.context.ecommerce.modules.cart.domain.view.CartResponse

fun CartResponse.toRestResponse() =
        CartRestResponse(cartId, createdOn, userId, cartItems, checkout)