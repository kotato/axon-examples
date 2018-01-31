package com.kotato.context.ecommerce.modules.order.stub

import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CartItemsStub
import com.kotato.context.ecommerce.modules.order.domain.view.OrderResponse
import com.kotato.context.ecommerce.modules.payment.stub.PaymentIdStub
import com.kotato.context.ecommerce.modules.user.stub.UserIdStub
import java.time.ZonedDateTime

class OrderResponseStub {
    companion object {
        fun random(id: String = OrderIdStub.random().asString(),
                   createdAt: ZonedDateTime = ZonedDateTime.now(),
                   userId: String = UserIdStub.random().asString(),
                   cartId: String = CartIdStub.random().asString(),
                   paymentId: String = PaymentIdStub.random().asString(),
                   status: String = OrderStatusStub.random().name,
                   cartItems: SerializedCartItems = CartItemsStub.random().toSerializedCartItems())
                = OrderResponse(id, createdAt, userId, cartId, paymentId, status, cartItems)
    }
}