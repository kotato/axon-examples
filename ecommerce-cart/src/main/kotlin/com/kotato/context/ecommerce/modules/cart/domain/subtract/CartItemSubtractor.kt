package com.kotato.context.ecommerce.modules.cart.domain.subtract

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.CartNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.CartRepository
import javax.inject.Named

@Named
open class CartItemSubtractor(private val repository: CartRepository) {

    operator fun invoke(id: CartId, cartItem: CartItem, quantity: Int) {
        TODO()
    }

}