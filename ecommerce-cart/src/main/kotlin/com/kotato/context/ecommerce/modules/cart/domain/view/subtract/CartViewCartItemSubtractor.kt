package com.kotato.context.ecommerce.modules.cart.domain.view.subtract

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.domain.subtract
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.shared.transaction.ReadModelTransaction
import javax.inject.Named

@Named
open class CartViewCartItemSubtractor(private val repository: CartViewRepository) {

    @ReadModelTransaction
    open operator fun invoke(cartId: CartId, cartItem: CartItem, quantity: Int) {
        repository.search(cartId)
                .also { guardCartViewExists(cartId, it) }!!
                .let { it.copy(cartItems = it.cartItems.subtract(cartItem, quantity)) }
                .let(repository::save)

    }

    private fun guardCartViewExists(id: CartId, cart: CartView?) {
        cart ?: throw CartViewNotFoundException(id.asString())
    }

}