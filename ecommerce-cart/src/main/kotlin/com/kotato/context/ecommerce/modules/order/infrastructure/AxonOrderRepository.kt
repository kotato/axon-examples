package com.kotato.context.ecommerce.modules.order.infrastructure

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.order.domain.Order
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.OrderRepository
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.axonframework.commandhandling.model.AggregateNotFoundException
import org.springframework.stereotype.Repository
import org.axonframework.commandhandling.model.Repository as AggregateRepository

@Repository
open class AxonOrderRepository(private val persistenceRepository: AggregateRepository<Order>) : OrderRepository {
    override fun search(orderId: OrderId) =
        try {
            persistenceRepository.load(orderId.asString()).invoke { it }
        } catch (exception: AggregateNotFoundException) {
            null
        }

    override fun new(orderId: OrderId, cartId: CartId, paymentId: PaymentId, userId: UserId, cartItems: CartItems) {
        persistenceRepository.newInstance {
            Order.create(orderId, cartId, paymentId, userId, cartItems)
            Order()
        }
    }

}