package com.kotato.context.ecommerce.modules.order.domain.create

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.checkout.CartCheckedOutEvent
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import org.axonframework.eventhandling.EventHandler
import java.util.UUID
import javax.inject.Named

@Named
open class CreateOrderOnCartCheckedOutEventHandler(private val creator: OrderCreator) {

    @EventHandler
    open fun on(event: CartCheckedOutEvent) {
        creator(OrderId.fromString(event.orderId),
                CartId.fromString(event.aggregateId))
    }
}