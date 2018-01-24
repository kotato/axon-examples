package com.kotato.context.ecommerce.modules.order.domain

import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Order {

    @AggregateIdentifier
    lateinit var orderId: OrderId
        private set
    lateinit var cartId: PaymentId
        private set
    lateinit var paymentId: PaymentId
        private set
    lateinit var cartItems: CartItems
        private set
    lateinit var orderStatus: OrderStatus
        private set

}