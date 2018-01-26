package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.order.behaviour.create.OrderCreatedEvent
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.eventhandling.EventHandler
import org.axonframework.spring.stereotype.Aggregate
import java.time.ZonedDateTime

@Aggregate
class Order {

    @AggregateIdentifier
    lateinit var orderId: OrderId
        private set
    lateinit var cartId: CartId
        private set
    lateinit var paymentId: PaymentId
        private set
    lateinit var userId: UserId
        private set
    lateinit var cartItems: CartItems
        private set
    lateinit var orderStatus: OrderStatus
        private set

    @EventHandler
    fun on(event: OrderCreatedEvent) {
        orderId = OrderId.fromString(event.aggregateId)
        cartId = CartId.fromString(event.cartId)
        paymentId = PaymentId.fromString(event.paymentId)
        userId = UserId.fromString(event.userId)
        cartItems = event.cartItems.toCartItems()
        orderStatus = OrderStatus.IN_PROGRESS
    }

    companion object {
        fun create(orderId: OrderId, cartId: CartId, paymentId: PaymentId, userId: UserId, cartItems: CartItems) {
            apply(OrderCreatedEvent(orderId.asString(),
                                    ZonedDateTime.now(),
                                    cartId.asString(),
                                    paymentId.asString(),
                                    userId.asString(),
                                    cartItems.toSerializedCartItems()))
        }
    }

}