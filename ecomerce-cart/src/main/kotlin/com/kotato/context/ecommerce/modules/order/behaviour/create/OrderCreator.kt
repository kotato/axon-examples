package com.kotato.context.ecommerce.modules.order.behaviour.create

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQueryAsker
import com.kotato.context.ecommerce.modules.order.behaviour.OrderId
import com.kotato.context.ecommerce.modules.order.behaviour.OrderRepository
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import javax.inject.Named

@Named
open class OrderCreator(private val repository: OrderRepository,
                        private val asker: FindCartQueryAsker) {

    operator fun invoke(orderId: OrderId, cartId: CartId, paymentId: PaymentId) {
        repository.new(orderId, cartId, paymentId, asker.ask(cartId).cartItems.toCartItems())
    }
}