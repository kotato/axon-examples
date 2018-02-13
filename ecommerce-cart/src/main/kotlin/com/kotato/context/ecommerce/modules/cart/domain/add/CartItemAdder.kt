package com.kotato.context.ecommerce.modules.cart.domain.add

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.CartRepository
import javax.inject.Named

@Named
open class CartItemAdder(private val repository: CartRepository) {

    operator fun invoke(id: CartId, cartItem: CartItem, quantity: Int) {
        TODO()
    }

}