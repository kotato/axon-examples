package com.kotato.context.ecommerce.modules.cart.domain.checkout

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartDoesNotExistsException
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.create.CartRepository

class CartCheckout(private val repository: CartRepository) {
    operator fun invoke(id: CartId) {
        id.let(repository::search)
                .also { guardCartExists(id, it) }!!
                .checkout()
    }


    private fun guardCartExists(id: CartId, cart: Cart?) {
        if (null == cart) throw CartDoesNotExistsException(id.asString())
    }

}