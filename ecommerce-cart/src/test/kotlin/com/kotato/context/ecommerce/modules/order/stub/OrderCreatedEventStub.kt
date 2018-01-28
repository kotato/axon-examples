package com.kotato.context.ecommerce.modules.order.stub

import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CartItemsStub
import com.kotato.context.ecommerce.modules.order.domain.create.OrderCreatedEvent
import com.kotato.context.ecommerce.modules.payment.stub.PaymentIdStub
import com.kotato.context.ecommerce.modules.user.stub.UserIdStub
import java.time.ZonedDateTime

class OrderCreatedEventStub {
    companion object {
        fun random(aggregateId: String = OrderIdStub.random().asString(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now(),
                   cartId: String = CartIdStub.random().asString(),
                   paymentId: String = PaymentIdStub.random().asString(),
                   userId: String = UserIdStub.random().asString(),
                   cartItems: SerializedCartItems = CartItemsStub.random().toSerializedCartItems())
                = OrderCreatedEvent(aggregateId, occurredOn, cartId, paymentId, userId, cartItems)
    }
}