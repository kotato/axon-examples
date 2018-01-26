package com.kotato.context.ecommerce.modules.cart.domain.checkout

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartDoesNotExistsException
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.create.CartRepository
import com.kotato.context.ecommerce.modules.order.behaviour.OrderId
import javax.inject.Named

@Named
class CartCheckout(private val repository: CartRepository) {
    operator fun invoke(id: CartId,
                        orderId: OrderId) {
        id.let(repository::search)
                .also { guardCartExists(id, it) }!!
                .checkout(orderId)
    }


    private fun guardCartExists(id: CartId, cart: Cart?) {
        cart ?: throw CartDoesNotExistsException(id.asString())
    }

}