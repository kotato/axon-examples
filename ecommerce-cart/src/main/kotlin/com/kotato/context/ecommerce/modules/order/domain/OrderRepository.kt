package com.kotato.context.ecommerce.modules.order.domain

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId

interface OrderRepository {

    fun search(orderId: OrderId): Order?
    fun new(orderId: OrderId,
            cartId: CartId,
            paymentId: PaymentId,
            userId: UserId,
            cartItems: CartItems)

}