package com.kotato.context.ecommerce.modules.order.domain.view.create

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.create.OrderCreatedEvent
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.shared.money.Money
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class CreateOrderViewOnOrderCreatedEventHandler(private val creator: OrderViewCreator) {

    @EventHandler
    open fun on(event: OrderCreatedEvent) {
        creator(OrderId.fromString(event.aggregateId),
                event.occurredOn,
                CartId.fromString(event.cartId),
                PaymentId.fromString(event.paymentId),
                UserId.fromString(event.userId),
                Money.of(event.price, event.currency))
    }
}