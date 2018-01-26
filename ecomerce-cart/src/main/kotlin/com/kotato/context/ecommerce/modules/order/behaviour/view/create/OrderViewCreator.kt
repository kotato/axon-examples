package com.kotato.context.ecommerce.modules.order.behaviour.view.create

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.order.behaviour.OrderId
import com.kotato.context.ecommerce.modules.order.behaviour.OrderStatus
import com.kotato.context.ecommerce.modules.order.behaviour.view.OrderView
import com.kotato.context.ecommerce.modules.order.behaviour.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import java.time.ZonedDateTime
import javax.inject.Named

@Named
open class OrderViewCreator(private val repository: OrderViewRepository) {


    open operator fun invoke(orderId: OrderId,
                             createdOn: ZonedDateTime,
                             cartId: CartId,
                             paymentId: PaymentId,
                             userId: UserId,
                             cartItems: CartItems) {
        OrderView(orderId,
                  createdOn,
                  userId,
                  cartId,
                  paymentId,
                  OrderStatus.IN_PROGRESS,
                  cartItems)
                .let(repository::save)

    }

}