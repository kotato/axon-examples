package com.kotato.context.ecommerce.modules.cart.domain.checkout

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartRepository
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import javax.inject.Named

@Named
class CartCheckout(private val repository: CartRepository) {

    operator fun invoke(id: CartId, orderId: OrderId) {
        TODO()
    }

}