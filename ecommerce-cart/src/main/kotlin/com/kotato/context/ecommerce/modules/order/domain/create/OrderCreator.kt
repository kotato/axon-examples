package com.kotato.context.ecommerce.modules.order.domain.create

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQueryAsker
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.OrderRepository
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import javax.inject.Named

@Named
open class OrderCreator(private val repository: OrderRepository,
                        private val asker: FindCartQueryAsker) {

    operator fun invoke(orderId: OrderId, cartId: CartId, paymentId: PaymentId) {
        asker.ask(cartId).let {
            repository.new(orderId,
                           cartId,
                           paymentId,
                           UserId.fromString(it.userId),
                           it.cartItems.toCartItems())
        }
    }
}