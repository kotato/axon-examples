package com.kotato.context.ecommerce.modules.cart.domain.create

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.user.domain.UserId

interface CartRepository {

    fun new(id: CartId, userId: UserId)
    fun search(id: CartId): Cart?

}