package com.kotato.context.ecommerce.modules.cart.domain.view.checkout

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.shared.transaction.ReadModelTransaction
import javax.inject.Named

@Named
open class CartViewCheckout(private val repository: CartViewRepository) {

    @ReadModelTransaction
    open operator fun invoke(cartId: CartId) {
        repository.search(cartId)
                .also { guardCartViewExists(cartId, it) }!!
                .copy(checkout = true)
                .let(repository::save)

    }

    private fun guardCartViewExists(id: CartId, cart: CartView?) {
        cart ?: throw CartViewNotFoundException(id.asString())
    }

}