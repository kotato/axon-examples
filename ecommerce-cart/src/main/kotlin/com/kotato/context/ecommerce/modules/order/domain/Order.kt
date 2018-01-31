package com.kotato.context.ecommerce.modules.order.domain

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.order.domain.create.OrderCreatedEvent
import com.kotato.context.ecommerce.modules.order.domain.update.status.failed.OrderFailedEvent
import com.kotato.context.ecommerce.modules.order.domain.update.status.succeeded.OrderSucceededEvent
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.eventsourcing.EventSourcingHandler
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

    @EventSourcingHandler
    fun on(event: OrderCreatedEvent) {
        orderId = OrderId.fromString(event.aggregateId)
        cartId = CartId.fromString(event.cartId)
        paymentId = PaymentId.fromString(event.paymentId)
        userId = UserId.fromString(event.userId)
        cartItems = event.cartItems.toCartItems()
        orderStatus = OrderStatus.IN_PROGRESS
    }

    @EventSourcingHandler
    fun on(event: OrderFailedEvent) {
        orderStatus = OrderStatus.SUCCEEDED
    }

    @EventSourcingHandler
    fun on(event: OrderSucceededEvent) {
        orderStatus = OrderStatus.FAILED
    }

    fun updateAsFailed() {
        apply(OrderFailedEvent(orderId.asString(), ZonedDateTime.now()))
    }

    fun updateAsSucceeded() {
        apply(OrderSucceededEvent(orderId.asString(), ZonedDateTime.now()))
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