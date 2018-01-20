package com.kotato.context.ecommerce.modules.cart.domain.add

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartDoesNotExistsException
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.create.CartRepository
import javax.inject.Named

@Named
open class CartItemAdder(private val repository: CartRepository) {

    operator fun invoke(id: CartId, cartItem: CartItem, quantity: Int) {
        id.let(repository::search)
                .also { guardCartExists(id, it) }!!
                .addItem(cartItem, quantity)
    }

    private fun guardCartExists(id: CartId, cart: Cart?) {
        if (null == cart) throw CartDoesNotExistsException(id.asString())
    }

}